package io.skyfallsdk.config;

import io.skyfallsdk.Server;
import io.skyfallsdk.config.type.JsonConfig;
import io.skyfallsdk.config.type.YamlConfig;

import java.nio.file.Path;

public class PerformanceConfig extends JsonConfig<PerformanceConfig> {

    private static final PerformanceConfig DEFAULT_CONFIG = new PerformanceConfig(4);

    private int nettyThreads;

    /**
     * Represents a configuration file.
     */
    public PerformanceConfig() {
        super(PerformanceConfig.class);
    }

    private PerformanceConfig(int nettyThreads) {
        super(PerformanceConfig.class);

        this.nettyThreads = nettyThreads;
    }

    public int getNettyThreads() {
        return this.nettyThreads;
    }

    @Override
    public Path getPath() {
        return Server.get().getPath().resolve("performance.json");
    }

    @Override
    public PerformanceConfig getDefaultConfig() {
        return DEFAULT_CONFIG;
    }
}
