package io.skyfallsdk.concurrent.tick;

import org.jetbrains.annotations.NotNull;

public interface TickSpec<T extends TickSpec<T>> {

    @NotNull
    String getName();

    @NotNull
    TickRegistry<T> getRegistry();

    long getTickLength();

    boolean isSequential();
}
