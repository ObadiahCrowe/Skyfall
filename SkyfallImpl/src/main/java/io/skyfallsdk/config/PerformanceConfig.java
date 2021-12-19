package io.skyfallsdk.config;

import io.skyfallsdk.Server;
import io.skyfallsdk.config.type.YamlConfig;

import java.nio.file.Path;

public class PerformanceConfig extends YamlConfig<PerformanceConfig> {

    private static final PerformanceConfig DEFAULT_CONFIG = new PerformanceConfig(30_000, 256, 4000, 20);

    private int maxPacketSize;
    private int compressionThreshold;
    private int connectionThrottleThreshold;

    private int initialChunkCache;
    private boolean loadPlayerDataOnStartup;

    /**
     * Represents a configuration file.
     */
    public PerformanceConfig() {
        super(PerformanceConfig.class);
    }

    private PerformanceConfig(int maxPacketSize, int compressionThreshold, int connectionThrottleThreshold, int initialChunkCache) {
        super(PerformanceConfig.class);

        this.maxPacketSize = maxPacketSize;
        this.compressionThreshold = compressionThreshold;
        this.connectionThrottleThreshold = connectionThrottleThreshold;
        this.initialChunkCache = initialChunkCache;
        this.loadPlayerDataOnStartup = true;
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

    public int getInitialChunkCache() {
        return this.initialChunkCache;
    }

    public boolean shouldLoadPlayerDataOnStartup() {
        return this.loadPlayerDataOnStartup;
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
