package io.skyfallsdk.concurrent;

public class PoolSpec {

    public static final PoolSpec CHUNKS = new PoolSpec("Skyfall - Chunks", 4, false);

    public static final PoolSpec ENTITIES = new PoolSpec("Skyfall - Entities", 4, true);
    public static final PoolSpec PLAYERS = new PoolSpec("Skyfall - Players", 4, false);

    public static final PoolSpec SCHEDULER = new PoolSpec("Skyfall - Scheduler", 4, true);
    public static final PoolSpec WORLD = new PoolSpec("Skyfall - World", 2, true);

    private final String name;
    private final int maxThreads;
    private final boolean stealing;

    PoolSpec(String name, int maxThreads, boolean stealing) {
        this.name = name;
        this.maxThreads = maxThreads;
        this.stealing = stealing;
    }


}
