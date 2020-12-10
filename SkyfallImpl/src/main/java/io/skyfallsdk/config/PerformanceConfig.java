package io.skyfallsdk.config;

import io.skyfallsdk.Server;
import io.skyfallsdk.config.type.YamlConfig;

import java.nio.file.Path;

public class PerformanceConfig extends YamlConfig<PerformanceConfig> {

    private static final PerformanceConfig DEFAULT_CONFIG = new PerformanceConfig(30_000, 256);

    private int maxPacketSize;
    private int compressionThreshold;

    /**
     * Represents a configuration file.
     */
    public PerformanceConfig() {
        super(PerformanceConfig.class);
    }

    private PerformanceConfig(int maxPacketSize, int compressionThreshold) {
        super(PerformanceConfig.class);

        this.maxPacketSize = maxPacketSize;
        this.compressionThreshold = compressionThreshold;
    }

    public int getMaxPacketSize() {
        return this.maxPacketSize;
    }

    public int getCompressionThreshold() {
        return this.compressionThreshold;
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
