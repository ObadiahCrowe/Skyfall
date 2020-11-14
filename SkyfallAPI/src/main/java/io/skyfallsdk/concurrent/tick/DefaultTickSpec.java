package io.skyfallsdk.concurrent.tick;

public enum DefaultTickSpec implements TickSpec {

    BLOCK(50L),
    ENTITY(50L),
    PLAYER(50L),
    TILE_ENTITY(50L),
    OTHER(50L);

    private final long tickLength;

    DefaultTickSpec(long defaultTickLength) {
        this.tickLength = defaultTickLength;
    }

    @Override
    public long getTickLength() {
        return this.tickLength;
    }
}
