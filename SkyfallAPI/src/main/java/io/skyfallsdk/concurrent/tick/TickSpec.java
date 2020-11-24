package io.skyfallsdk.concurrent.tick;

public interface TickSpec<T extends TickSpec<T>> {

    TickRegistry<T> getRegistry();

    long getTickLength();

    boolean isSequential();
}
