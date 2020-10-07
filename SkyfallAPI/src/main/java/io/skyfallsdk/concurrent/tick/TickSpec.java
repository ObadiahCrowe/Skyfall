package io.skyfallsdk.concurrent.tick;

public enum TickSpec {

    BLOCK(50L),
    ENTITY(50L),
    PLAYER(50L),
    TILE_ENTITY(50L),
    OTHER(50L);

    private long defaultTickLength;

    TickSpec(long defaultTickLength) {
        this.defaultTickLength = defaultTickLength;
    }

    public long getDefaultTickLength() {
        return this.defaultTickLength;
    }
}
