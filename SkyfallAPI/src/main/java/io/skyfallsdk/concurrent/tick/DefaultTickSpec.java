package io.skyfallsdk.concurrent.tick;

public enum DefaultTickSpec implements TickSpec {

    BLOCK(50L, false),
    REDSTONE(50L, true),
    ENTITY(50L, false),
    PLAYER(50L, false),
    TILE_ENTITY(50L, false),
    OTHER(50L, false);

    private final long tickLength;
    private final boolean sequential;

    DefaultTickSpec(long defaultTickLength, boolean sequential) {
        this.tickLength = defaultTickLength;
        this.sequential = sequential;
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
