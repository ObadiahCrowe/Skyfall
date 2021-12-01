package io.skyfallsdk.world.chunk;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;

public enum ChunkStatus {

    EMPTY,
    STRUCTURE_STARTS,
    STRUCTURE_REFERENCES,
    BIOMES,
    NOISE,
    SURFACE,
    CARVERS,
    LIQUID_CARVERS,
    FEATURE,
    LIGHT,
    SPAWN,
    HEIGHTMAPS,
    FULL;

    private static final Map<String, ChunkStatus> RAW_TO_STATUS = Maps.newHashMap();

    static {
        for (ChunkStatus status : ChunkStatus.values()) {
            RAW_TO_STATUS.put(status.name(), status);
        }
    }

    public static @NotNull ChunkStatus getByRaw(@NotNull String raw) {
        return RAW_TO_STATUS.getOrDefault(raw.toUpperCase(Locale.ROOT), ChunkStatus.FULL);
    }
}
