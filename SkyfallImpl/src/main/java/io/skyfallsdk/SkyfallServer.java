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
import io.skyfallsdk.packet.*;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.util.UtilGitVersion;
import io.skyfallsdk.world.SkyfallWorldLoader;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.WorldLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SkyfallServer implements Server {

    private static Path workingDir;
    private static Logger logger;

    private final ServerConfig config;
    private final PerformanceConfig perfConfig;

    private final ServerExpansionRegistry expansionRegistry;
    private final ServerCommandMap commandMap;
    private final NetServer server;

    private final SkyfallWorldLoader worldLoader;

    private final ConsoleThread consoleThread;

    SkyfallServer() {
        workingDir = Paths.get(System.getProperty("user.dir"));
        logger = LogManager.getLogger(SkyfallServer.class);
        logger.info("Starting Skyfall version " + UtilGitVersion.getFromSkyfall().getPretty());

        logger.info("Setting Skyfall implementation..");
        Impl.IMPL.set(this);

        logger.info("Loading default configs..");
        this.config = LoadableConfig.getByClass(ServerConfig.class).load();
        this.perfConfig = LoadableConfig.getByClass(PerformanceConfig.class).load();

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

        /*
         * Load all immediately to give them absolute control before the server initialises.
         */
        this.expansionRegistry.loadAllExpansions();

        logger.info("Starting server..");
        this.server = NetServer.init(this.config.getNetworkConfig().getAddress(), this.config.getNetworkConfig().getPort());

        this.worldLoader = new SkyfallWorldLoader(workingDir);

        this.consoleThread = new ConsoleThread(this);
        this.consoleThread.setDaemon(true);
        this.consoleThread.setUncaughtExceptionHandler((t, e) -> logger.error("Uncaught exception on thread \"" + t.getName() + "\": " + e.getMessage()));
        this.consoleThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownInternal, "SF-Shutdown"));
    }

    private void shutdownInternal() {
        try {
            logger.info("Shuttng down Netty server..");
            this.server.shutdown();

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
        return LogManager.getLogger(expansion);
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
    public World getWorld(String name) {
        return this.worldLoader.get(name).orElse(null);
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
