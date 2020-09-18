package io.skyfallsdk;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.command.ServerCommandMap;
import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.concurrent.ThreadPool;
import io.skyfallsdk.concurrent.thread.ConsoleThread;
import io.skyfallsdk.concurrent.thread.ServerTickThread;
import io.skyfallsdk.config.LoadableConfig;
import io.skyfallsdk.config.PerformanceConfig;
import io.skyfallsdk.config.ServerConfig;
import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.net.NetServer;
import io.skyfallsdk.permission.PermissibleAction;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class SkyfallServer implements Server {

    private static Path workingDir;
    private static Logger logger;

    private final ServerConfig config;
    private final PerformanceConfig perfConfig;

    SkyfallServer() {
        workingDir = Paths.get(System.getProperty("user.dir"));
        logger = LogManager.getLogger(SkyfallServer.class);

        logger.info("Setting Skyfall implementation..");
        Impl.IMPL.set(this);

        logger.info("Loading default configs..");
        this.config = LoadableConfig.getByClass(ServerConfig.class).load();
        this.perfConfig = LoadableConfig.getByClass(PerformanceConfig.class).load();

        logger.info("Initialising thread pools..");
        ThreadPool.initDefaultPools();

        logger.info("Starting server..");
        NetServer.init("localhost", 25565);

        new ConsoleThread(this).start();
        new ServerTickThread().start();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    @Override
    public void shutdown() {
        logger.info("Saving configs..");
        this.config.save();

        logger.info("Shutting down thread pools..");
        ThreadPool.shutdownAll();
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
    public ServerCommandMap getCommandMap() {
        return null;
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
    public void sendMessage(ChatComponent component) {

    }

    @Override
    public void executeCommand(String command) {

    }

    @Override
    public void addPermission(PermissibleAction permission) {}

    @Override
    public void removePermission(PermissibleAction permission) {}

    @Override
    public boolean hasPermission(PermissibleAction permission) {
        return true;
    }
}
