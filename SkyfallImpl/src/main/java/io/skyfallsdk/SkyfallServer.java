package io.skyfallsdk;

import com.google.common.collect.Lists;
import io.sentry.Sentry;
import io.skyfallsdk.bossbar.BossBar;
import io.skyfallsdk.bossbar.SkyfallBossBar;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.command.ServerCommandMap;
import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.ThreadPool;
import io.skyfallsdk.concurrent.thread.ConsoleThread;
import io.skyfallsdk.concurrent.tick.ServerTickRegistry;
import io.skyfallsdk.concurrent.tick.TickSpec;
import io.skyfallsdk.config.LoadableConfig;
import io.skyfallsdk.config.PerformanceConfig;
import io.skyfallsdk.config.ServerConfig;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.expansion.ExpansionInfo;
import io.skyfallsdk.expansion.ServerExpansionRegistry;
import io.skyfallsdk.inventory.SkyfallInventoryFactory;
import io.skyfallsdk.net.NetServer;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.protocol.SkyfallPluginChannelRegistry;
import io.skyfallsdk.server.ServerState;
import io.skyfallsdk.spectre.ServerSpectreAPI;
import io.skyfallsdk.spectre.SpectreAPI;
import io.skyfallsdk.util.SkyfallSentryAppender;
import io.skyfallsdk.util.UtilGitVersion;
import io.skyfallsdk.util.http.ServerMojangAPI;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.loader.AbstractWorldLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SkyfallServer implements Server {

    private static Path workingDir;
    private static Logger logger;

    private static ServerState state;

    private final ServerConfig config;
    private final PerformanceConfig perfConfig;

    private final Path serverIcon;

    private final ServerExpansionRegistry expansionRegistry;
    private final ServerSpectreAPI spectreAPI;
    private final SkyfallPluginChannelRegistry pluginChannelRegistry;
    private final ServerCommandMap commandMap;
    private final NetServer server;

    private final ServerMojangAPI mojangAPI;

    private final AbstractWorldLoader<?, ?> worldLoader;
    private final SkyfallInventoryFactory inventoryFactory;

    private final ConsoleThread consoleThread;

    SkyfallServer() {
        long startTime = System.currentTimeMillis();

        workingDir = Paths.get(System.getProperty("user.dir"));
        logger = LogManager.getLogger(SkyfallServer.class);
        try {
            logger.info("Starting Skyfall version " + UtilGitVersion.getFromSkyfall().getPretty());
        } catch (NullPointerException e) {
            logger.warn("Starting unknown Skyfall version");
        }

        synchronized (this) {
            state = ServerState.INITIALISING;
        }

        logger.info("Setting Skyfall implementation..");
        Impl.IMPL.set(this);

        logger.info("Loading default configs..");
        this.config = LoadableConfig.getByClass(ServerConfig.class).load();
        this.perfConfig = LoadableConfig.getByClass(PerformanceConfig.class).load();

        if (this.config.getSentryConfig().isEnabled()) {
            logger.info("Enabling Sentry support..");
            SkyfallSentryAppender.usingSentry = true;
            Sentry.init(opt -> {
                opt.setDsn(this.config.getSentryConfig().getDsn());
                opt.setTracesSampleRate(this.config.getSentryConfig().getTraceSampleRate());

                opt.setDebug(this.config.isDebugEnabled());
            });
        } else {
            Sentry.close();
        }

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
        this.spectreAPI = new ServerSpectreAPI(this);
        this.pluginChannelRegistry = new SkyfallPluginChannelRegistry();
        this.commandMap = new ServerCommandMap();

        this.mojangAPI = new ServerMojangAPI();

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
            AbstractWorldLoader<?, ?> current = (AbstractWorldLoader<?, ?>) this.getClass().getDeclaredField("worldLoader").get(this);
            if (current == null) {
                logger.info("Instantiating WorldLoader with the " + this.config.getWorldFormat() + " format.");
                this.worldLoader = this.config.getWorldFormat().getWorldLoader().getConstructor(SkyfallServer.class, Path.class).newInstance(this, workingDir);
            } else {
                logger.info("Custom WorldLoader detected (" + current.getClass().getCanonicalName() + "), ignoring default loader.");
                this.worldLoader = current;
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            logger.fatal("Could not instantiate world loader!");
            throw new RuntimeException(e);
        }

        logger.info("Loading worlds...");
        try {
            Files.walkFileTree(this.worldLoader.getWorldDirectory(), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path levelDat = dir.resolve("level.dat");

                    if (Files.exists(levelDat)) {
                        SkyfallServer.this.worldLoader.load(dir);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            logger.warn(e);
        }

        logger.info("Initialising inventory factory...");
        this.inventoryFactory = new SkyfallInventoryFactory(this);

        this.consoleThread = new ConsoleThread(this);
        this.consoleThread.setDaemon(true);
        this.consoleThread.setUncaughtExceptionHandler((t, e) -> logger.error("Uncaught exception on thread \"" + t.getName() + "\": " + e.getMessage()));
        this.consoleThread.start();

        synchronized (this) {
            state = ServerState.RUNNING;
        }

        logger.info("Finished instantiation in " + ((double) (System.currentTimeMillis() - startTime) / 1000) + "s");

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownInternal, "SF-Shutdown"));
    }

    private void shutdownInternal() {
        synchronized (this) {
            state = ServerState.TERMINATING;
        }

        try {
            logger.info("Shuttng down Netty server..");
            this.server.shutdown();
        } catch (InterruptedException e) {
            Sentry.captureException(e);
        }

        logger.info("Saving worlds..");
        this.worldLoader.unloadAll();

        logger.info("Shutting down expansions..");
        this.expansionRegistry.unloadAllExpansions();

        logger.info("Shutting down thread pools..");
        ThreadPool.shutdownAll();

        logger.info("Saving configs..");
        this.config.save();

        this.consoleThread.interrupt();
    }

    public ServerConfig getConfig() {
        return this.config;
    }

    public PerformanceConfig getPerformanceConfig() {
        return this.perfConfig;
    }

    @Override
    public void shutdown() {
        System.exit(0);
    }

    @Override
    public @NotNull ServerState getState() {
        return state;
    }

    @Override
    public @NotNull Path getPath() {
        return workingDir;
    }

    @Override
    public @NotNull Logger getLogger() {
        return logger;
    }

    @Override
    public @NotNull Logger getLogger(Class<? extends Expansion> expansion) {
        return LogManager.getLogger(Expansion.class);
    }

    @Override
    public @NotNull ThreadPool getScheduler() {
        return ThreadPool.createForSpec(PoolSpec.SCHEDULER);
    }

    @Override
    public @NotNull ServerCommandMap getCommandMap() {
        return this.commandMap;
    }

    @Override
    public @NotNull Optional<@Nullable Player> getPlayer(String username) {
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<@Nullable Player> getPlayer(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public @NotNull List<@NotNull Player> getPlayers() {
        return Lists.newArrayList();
    }

    @Override
    public @NotNull Collection<@NotNull World> getWorlds() {
        return this.worldLoader.getLoadedWorlds();
    }

    @Override
    public @NotNull CompletableFuture<@NotNull Optional<@Nullable World>> getWorld(@NotNull String name) {
        return this.worldLoader.get(name);
    }

    @Override
    public @NotNull ChatComponent getMotd() {
        return null;
    }

    @Override
    public void setMotd(@NotNull ChatComponent motd) {

    }

    @Override
    public @Nullable Path getServerIcon() {
        return null;
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
    public @NotNull ServerExpansionRegistry getExpansionRegistry() {
        return this.expansionRegistry;
    }

    @Override
    public @NotNull SkyfallPluginChannelRegistry getPluginChannelRegistry() {
        return this.pluginChannelRegistry;
    }

    @Override
    public <T extends Expansion> @Nullable T getExpansion(@NotNull Class<T> expansionClass) {
        return this.expansionRegistry.getExpansion(expansionClass);
    }

    @Override
    public @Nullable ExpansionInfo getExpansionInfo(@NotNull Class<? extends Expansion> expansionClass) {
        return this.expansionRegistry.getExpansionInfo(expansionClass);
    }

    @Override
    public @NotNull SpectreAPI getSpectreAPI() {
        return this.spectreAPI;
    }

    @Override
    public @NotNull ServerMojangAPI getMojangApi() {
        return this.mojangAPI;
    }

    @Override
    public @NotNull List<@NotNull ProtocolVersion> getSupportedVersions() {
        return this.config.getSupportedVersions();
    }

    @Override
    public @NotNull ProtocolVersion getBaseVersion() {
        return this.config.getBaseVersion();
    }

    @Override
    public @NotNull AbstractWorldLoader getWorldLoader() {
        return this.worldLoader;
    }

    @Override
    public @NotNull <T extends TickSpec<T>> ServerTickRegistry<T> getTickRegistry(@NotNull T spec) {
        return ServerTickRegistry.getTickRegistry(spec);
    }

    @Override
    public @NotNull SkyfallInventoryFactory getInventoryFactory() {
        return this.inventoryFactory;
    }

    @Override
    public @NotNull BossBar createNewBossBar() {
        return new SkyfallBossBar();
    }

    @Override
    public void addPermission(@NotNull String permission) {

    }

    @Override
    public void removePermission(@NotNull String permission) {

    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return true;
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean op) {}

    @Override
    public void sendMessage(@NotNull ChatComponent component) {
        logger.info(component.getText());
    }

    @Override
    public void executeCommand(@NotNull String command) {
        this.commandMap.dispatch(this, command);
    }
}
