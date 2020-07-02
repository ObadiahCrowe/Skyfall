package io.skyfallsdk;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.command.CoreCommandMap;
import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.concurrent.ThreadPool;
import io.skyfallsdk.config.LoadableConfig;
import io.skyfallsdk.config.PerformanceConfig;
import io.skyfallsdk.config.ServerConfig;
import io.skyfallsdk.net.NetServer;
import io.skyfallsdk.player.Player;
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

    SkyfallServer() {
        workingDir = Paths.get(System.getProperty("user.dir"));
        logger = Logger.getLogger(SkyfallMain.class.getName());

        Impl.IMPL.set(this);

        this.config = LoadableConfig.getByClass(ServerConfig.class).load();
        this.perfConfig = LoadableConfig.getByClass(PerformanceConfig.class).load();

        NetServer.init("localhost", 25565);

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    @Override
    public void shutdown() {
        this.config.save();
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
    public CoreCommandMap getCommandMap() {
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
    public void sendMessage(ChatComponent component) {

    }
}
