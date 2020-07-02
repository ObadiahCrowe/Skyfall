package io.skyfallsdk.config;

import io.skyfallsdk.config.type.YamlConfig;

import java.nio.file.Path;

public class PerformanceConfig extends YamlConfig<PerformanceConfig> {

    private int nettyThreads;

    /**
     * Represents a configuration file.
     */
    public PerformanceConfig() {
        super(PerformanceConfig.class);
    }

    public int getNettyThreads() {
        return this.nettyThreads;
    }

    @Override
    public Path getPath() {
        return null;
    }

    @Override
    public PerformanceConfig getDefaultConfig() {
        return null;
    }
}
