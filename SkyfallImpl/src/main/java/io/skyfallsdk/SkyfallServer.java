package io.skyfallsdk;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.command.CommandMap;
import io.skyfallsdk.command.ServerCommandMap;
import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.concurrent.ThreadPool;
import io.skyfallsdk.concurrent.thread.ConsoleThread;
import io.skyfallsdk.config.PerformanceConfig;
import io.skyfallsdk.config.ServerConfig;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.expansion.ExpansionInfo;
import io.skyfallsdk.net.NetServer;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.world.World;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class SkyfallServer implements Server {

    private static Path workingDir;
    private static Logger logger;

    private final ServerConfig config;
    private final PerformanceConfig perfConfig;

    private final ServerCommandMap commandMap;

    private final ConsoleThread consoleThread;

    SkyfallServer() {
        workingDir = Paths.get(System.getProperty("user.dir"));
        logger = Logger.getLogger("Skyfall");

        logger.info("Setting Skyfall implementation..");
        Impl.IMPL.set(this);

        logger.info("Loading default configs..");
        //this.config = LoadableConfig.getByClass(ServerConfig.class).load();
        //this.perfConfig = LoadableConfig.getByClass(PerformanceConfig.class).load();
        this.config = new ServerConfig().getDefaultConfig();
        this.perfConfig = new PerformanceConfig().getDefaultConfig();

        logger.info("Setting up...");
        this.commandMap = new ServerCommandMap();

        logger.info("Initialising thread pools..");
        ThreadPool.initDefaultPools();

        logger.info("Starting server..");
        NetServer.init("localhost", 25565);

        this.consoleThread = new ConsoleThread(this);
        this.consoleThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    @Override
    public void shutdown() {
        logger.info("Saving configs..");
        this.config.save();

        logger.info("Shutting down thread pools..");
        ThreadPool.shutdownAll();

        this.consoleThread.interrupt();
    }

    public ServerConfig getConfig() {
        return this.config;
    }

    public PerformanceConfig getPerfConfig() {
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
    public Scheduler getScheduler() {
        return ThreadPool.createForSpec(PoolSpec.SCHEDULER);
    }

    @Override
    public CommandMap getCommandMap() {
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
        return null;
    }

    @Override
    public List<World> getWorlds() {
        return null;
    }

    @Override
    public World getWorld(String name) {
        return null;
    }

    @Override
    public String getMotd() {
        return null;
    }

    @Override
    public void setMotd(String motd) {

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
    public <T extends Expansion> T getExpansion(Class<T> expansionClass) {
        return null;
    }

    @Override
    public ExpansionInfo getExpansionInfo(Class<? extends Expansion> expansionClass) {
        return null;
    }

    @Override
    public List<ProtocolVersion> getSupportedVersions() {
        return this.config.getSupportedVersions();
    }

    @Override
    public ProtocolVersion getBaseVersion() {
        return null;
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
}
