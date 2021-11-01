package io.skyfallsdk.concurrent.tick;

import io.skyfallsdk.Server;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum DefaultTickSpec implements TickSpec<DefaultTickSpec> {

    BLOCK(50L, false),
    REDSTONE(50L, true),
    ENTITY(50L, false),
    PLAYER(50L, false),
    TILE_ENTITY(50L, false),
    OTHER(50L, false),
    WORLD(50L, false);

    private final String name;

    private final long tickLength;
    private final boolean sequential;

    DefaultTickSpec(long defaultTickLength, boolean sequential) {
        StringBuilder builder = new StringBuilder();
        String[] parts = this.name().split("_");
        for (String part : parts) {
            builder.append(part.substring(0, 1).toUpperCase(Locale.ROOT))
              .append(part.substring(1).toLowerCase(Locale.ROOT))
              .append("-");
        }

        this.name = builder.substring(0, builder.length() - 1);

        this.tickLength = defaultTickLength;
        this.sequential = sequential;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull TickRegistry<DefaultTickSpec> getRegistry() {
        return Server.get().getTickRegistry(this);
    }

    @Override
    public long getTickLength() {
        return this.tickLength;
    }

    @Override
    public boolean isSequential() {
        return this.sequential;
    }
}
