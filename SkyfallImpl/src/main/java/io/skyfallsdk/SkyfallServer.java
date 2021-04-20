package io.skyfallsdk;

import com.google.common.collect.Lists;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.command.ServerCommandMap;
import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.concurrent.ThreadPool;
import io.skyfallsdk.concurrent.thread.ConsoleThread;
import io.skyfallsdk.concurrent.tick.ServerTickRegistry;
import io.skyfallsdk.concurrent.tick.TickRegistry;
import io.skyfallsdk.concurrent.tick.TickSpec;
import io.skyfallsdk.config.LoadableConfig;
import io.skyfallsdk.config.PerformanceConfig;
import io.skyfallsdk.config.ServerConfig;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.expansion.ExpansionInfo;
import io.skyfallsdk.expansion.ServerExpansionRegistry;
import io.skyfallsdk.net.NetServer;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.server.ServerState;
import io.skyfallsdk.util.UtilGitVersion;
import io.skyfallsdk.util.http.MojangAPI;
import io.skyfallsdk.util.http.NetMojangAPI;
import io.skyfallsdk.world.loader.AbstractWorldLoader;
import io.skyfallsdk.world.loader.AnvilWorldLoader;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.WorldLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class SkyfallServer implements Server {

    private static Path workingDir;
    private static Logger logger;

    private static ServerState state;

    private final ServerConfig config;
    private final PerformanceConfig perfConfig;

    private final Path serverIcon;

    private final ServerExpansionRegistry expansionRegistry;
    private final ServerCommandMap commandMap;
    private final NetServer server;

    private final NetMojangAPI mojangAPI;

    private final AbstractWorldLoader worldLoader;

    private final ConsoleThread consoleThread;

    SkyfallServer() {
        workingDir = Paths.get(System.getProperty("user.dir"));
        logger = LogManager.getLogger(SkyfallServer.class);
        try {
            logger.info("Starting Skyfall version " + UtilGitVersion.getFromSkyfall().getPretty());
        } catch (NullPointerException e) {
            logger.info("Starting unknown Skyfall version");
        }

        synchronized (this) {
            state = ServerState.INITIALISING;
        }

        logger.info("Setting Skyfall implementation..");
        Impl.IMPL.set(this);

        logger.info("Loading default configs..");
        this.config = LoadableConfig.getByClass(ServerConfig.class).load();
        this.perfConfig = LoadableConfig.getByClass(PerformanceConfig.class).load();

        Path serverIcon = workingDir.resolve("server-icon.png");
        this.serverIcon = Files.exists(serverIcon) ? serverIcon : null;

        if (!this.config.isDebugEnabled()) {
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            Configuration config = context.getConfiguration();
            LoggerConfig loggerCfg = config.getLoggerConfig("io.netty");
            loggerCfg.setLevel(Level.OFF);
            context.updateLoggers();
        }

        logger.info("Implementing on " + this.config.getBaseVersion().getName() + " as base version!");
        try {
            // Calls static initialiser, therefore registering all packets at startup.
            Class.forName("io.skyfallsdk.packet.PacketRegistry");
        } catch (ClassNotFoundException ignored) {}

        logger.info("Initialising thread pools..");
        ThreadPool.initDefaultPools();

        logger.info("Setting up expansion support...");
        this.expansionRegistry = new ServerExpansionRegistry(this);
        this.commandMap = new ServerCommandMap();

        this.mojangAPI = new NetMojangAPI();

        /*
         * Load all immediately to give them absolute control before the server initialises.
         */
        this.expansionRegistry.loadAllExpansions();

        logger.info("Starting server..");
        this.server = NetServer.init(this.config.getNetworkConfig().getAddress(), this.config.getNetworkConfig().getPort());

        try {
            // Whilst this may come across as hacky, the idea is to allow expansions to reflectively set their WorldLoader if they so desire.
            // Normally this would be exposed via the API, but we do not wish to have WorldLoader's changed at runtime unless the developer
            // really knows what they're doing.
            AbstractWorldLoader current = (AbstractWorldLoader) this.getClass().getDeclaredField("worldLoader").get(this);
            if (current == null) {
                logger.info("Instantiating WorldLoader with the " + this.config.getWorldFormat() + " format.");
                this.worldLoader = this.config.getWorldFormat().getWorldLoader().getConstructor(SkyfallServer.class, Path.class).newInstance(this, workingDir);
            } else {
                logger.info("Custom WorldLoader detected (" + current.getClass() + "), ignoring default loader.");
                this.worldLoader = current;
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            logger.fatal("Could not instantiate world loader!");
            throw new RuntimeException(e);
        }

        this.consoleThread = new ConsoleThread(this);
        this.consoleThread.setDaemon(true);
        this.consoleThread.setUncaughtExceptionHandler((t, e) -> logger.error("Uncaught exception on thread \"" + t.getName() + "\": " + e.getMessage()));
        this.consoleThread.start();

        synchronized (this) {
            state = ServerState.RUNNING;
        }

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownInternal, "SF-Shutdown"));
    }

    private void shutdownInternal() {
        synchronized (this) {
            state = ServerState.TERMINATING;
        }

        try {
            logger.info("Shuttng down Netty server..");
            this.server.shutdown();

            logger.info("Saving worlds..");
            this.worldLoader.unloadAll();

            logger.info("Shutting down thread pools..");
            ThreadPool.shutdownAll();

            logger.info("Saving configs..");
            this.config.save();

            this.consoleThread.interrupt();
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

    @Override
    public void shutdown() {
        System.exit(0);
    }

    @Override
    public ServerState getState() {
        return state;
    }

    public ServerConfig getConfig() {
        return this.config;
    }

    public PerformanceConfig getPerformanceConfig() {
        return this.perfConfig;
    }

    @Override
    public Path getPath() {
        return workingDir;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public Logger getLogger(Expansion expansion) {
        return LogManager.getLogger(Expansion.class);
    }

    @Override
    public Scheduler getScheduler() {
        return ThreadPool.createForSpec(PoolSpec.SCHEDULER);
    }

    @Override
    public ServerCommandMap getCommandMap() {
        return this.commandMap;
    }

    @Override
    public Player getPlayer(String username) {
        return null;
    }

    @Override
    public Player getPlayer(UUID uuid) {
        return null;
    }

    @Override
    public List<Player> getPlayers() {
        return Lists.newArrayList();
    }

    @Override
    public Collection<World> getWorlds() {
        return this.worldLoader.getLoadedWorlds();
    }

    @Override
    public CompletableFuture<Optional<World>> getWorld(String name) {
        return this.worldLoader.get(name);
    }

    @Override
    public String getMotd() {
        return this.config.getNetworkConfig().getMotd();
    }

    @Override
    public void setMotd(String motd) {
        this.config.getNetworkConfig().setMotd(motd);
    }

    @Override
    public Path getServerIcon() {
        return this.serverIcon;
    }

    @Override
    public boolean isOnlineMode() {
        return this.config.isOnlineMode();
    }

    @Override
    public void setOnlineMode(boolean onlineMode) {
        this.config.setOnlineMode(onlineMode);
    }

    @Override
    public int getMaxPlayers() {
        return this.config.getMaxPlayers();
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        this.config.setMaxPlayers(maxPlayers);
    }

    @Override
    public ServerExpansionRegistry getExpansionRegistry() {
        return this.expansionRegistry;
    }

    @Override
    public <T extends Expansion> T getExpansion(Class<T> expansionClass) {
        return this.getExpansionRegistry().getExpansion(expansionClass);
    }

    @Override
    public ExpansionInfo getExpansionInfo(Class<? extends Expansion> expansionClass) {
        return this.getExpansionRegistry().getExpansionInfo(expansionClass);
    }

    @Override
    public MojangAPI getMojangApi() {
        return this.mojangAPI;
    }

    @Override
    public List<ProtocolVersion> getSupportedVersions() {
        return this.config.getSupportedVersions();
    }

    @Override
    public ProtocolVersion getBaseVersion() {
        return this.config.getBaseVersion();
    }

    @Override
    public WorldLoader getWorldLoader() {
        return this.worldLoader;
    }

    @Override
    public <T extends TickSpec<T>> TickRegistry<T> getTickRegistry(T spec) {
        return ServerTickRegistry.getTickRegistry(spec);
    }

    @Override
    public void sendMessage(ChatComponent component) {
        logger.info(component.getText());
    }

    @Override
    public void executeCommand(String command) {
        this.commandMap.dispatch(this, command);
    }

    @Override
    public void addPermission(String permission) {}

    @Override
    public void removePermission(String permission) {}

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean op) {}
}
