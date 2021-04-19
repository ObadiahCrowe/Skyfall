package io.skyfallsdk.config;

import io.skyfallsdk.Server;
import io.skyfallsdk.config.type.YamlConfig;

import java.nio.file.Path;

public class PerformanceConfig extends YamlConfig<PerformanceConfig> {

    private static final PerformanceConfig DEFAULT_CONFIG = new PerformanceConfig(30_000, 256, 4000);

    private int maxPacketSize;
    private int compressionThreshold;
    private int connectionThrottleThreshold;

    /**
     * Represents a configuration file.
     */
    public PerformanceConfig() {
        super(PerformanceConfig.class);
    }

    private PerformanceConfig(int maxPacketSize, int compressionThreshold, int connectionThrottleThreshold) {
        super(PerformanceConfig.class);

        this.maxPacketSize = maxPacketSize;
        this.compressionThreshold = compressionThreshold;
        this.connectionThrottleThreshold = connectionThrottleThreshold;
    }

    public int getMaxPacketSize() {
        return this.maxPacketSize;
    }

    public int getCompressionThreshold() {
        return this.compressionThreshold;
    }

    public int getConnectionThrottleThreshold() {
        return this.connectionThrottleThreshold;
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
