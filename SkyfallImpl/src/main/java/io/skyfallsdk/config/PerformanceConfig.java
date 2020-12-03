package io.skyfallsdk.config;

import io.skyfallsdk.Server;
import io.skyfallsdk.config.type.YamlConfig;

import java.nio.file.Path;

public class PerformanceConfig extends YamlConfig<PerformanceConfig> {

    private static final PerformanceConfig DEFAULT_CONFIG = new PerformanceConfig(30_000);

    private int maxPacketSize;

    /**
     * Represents a configuration file.
     */
    public PerformanceConfig() {
        super(PerformanceConfig.class);
    }

    private PerformanceConfig(int maxPacketSize) {
        super(PerformanceConfig.class);

        this.maxPacketSize = maxPacketSize;
    }

    public int getMaxPacketSize() {
        return this.maxPacketSize;
    }

    @Override
    public Path getPath() {
        return Server.get().getPath().resolve("performance.yml");
    }

    @Override
    public PerformanceConfig getDefaultConfig() {
        return DEFAULT_CONFIG;
    }
}
