package io.skyfallsdk;

import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.concurrent.ThreadPool;
import io.skyfallsdk.config.LoadableConfig;
import io.skyfallsdk.config.ServerConfig;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class SkyfallServer implements Server {

    private static Path workingDir;
    private static Logger logger;

    private final ServerConfig config;

    SkyfallServer() {
        workingDir = Paths.get(System.getProperty("user.dir"));
        logger = Logger.getLogger(SkyfallMain.class.getName());

        Impl.IMPL.set(this);

        this.config = LoadableConfig.getByClass(ServerConfig.class).load();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    @Override
    public void shutdown() {
        this.config.save();
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
}
