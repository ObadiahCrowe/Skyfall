package io.skyfallsdk.concurrent;

import io.skyfallsdk.Server;

import java.io.PrintStream;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.ThreadFactory;

public class PoolSpec implements ThreadFactory, ForkJoinPool.ForkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler {

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

    public String getName() {
        return this.name;
    }

    public int getMaxThreads() {
        return this.maxThreads;
    }

    public boolean isStealing() {
        return this.stealing;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace(new PrintStream(System.out) {
            @Override
            public void println(Object o) {
                Server.get().getLogger().severe(String.valueOf(o));
            }
        });
    }

    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        ForkJoinWorkerThread thread = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
        thread.setName(this.name + " - " + thread.getPoolIndex());
        thread.setUncaughtExceptionHandler(this);

        return thread;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, this.name);
        thread.setUncaughtExceptionHandler(this);

        return thread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return this.name.equalsIgnoreCase(((PoolSpec) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.maxThreads, this.stealing);
    }

    @Override
    public String toString() {
        return "PoolSpec{" +
          "name='" + name + '\'' +
          ", maxThreads=" + maxThreads +
          ", stealing=" + stealing +
          '}';
    }
}
