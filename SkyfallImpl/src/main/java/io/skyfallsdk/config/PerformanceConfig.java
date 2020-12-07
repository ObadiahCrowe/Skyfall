package io.skyfallsdk.config;

import io.skyfallsdk.Server;
import io.skyfallsdk.config.type.YamlConfig;

import java.nio.file.Path;

public class PerformanceConfig extends YamlConfig<PerformanceConfig> {

    private static final PerformanceConfig DEFAULT_CONFIG = new PerformanceConfig(30_000, 100);

    private int maxPacketSize;
    private int mojangApiCacheSize;

    /**
     * Represents a configuration file.
     */
    public PerformanceConfig() {
        super(PerformanceConfig.class);
    }

    private PerformanceConfig(int maxPacketSize, int mojangApiCacheSize) {
        super(PerformanceConfig.class);

        this.maxPacketSize = maxPacketSize;
        this.mojangApiCacheSize = mojangApiCacheSize;
    }

    public int getMaxPacketSize() {
        return this.maxPacketSize;
    }

    public int getMojangApiCacheSize() {
        return this.mojangApiCacheSize;
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
