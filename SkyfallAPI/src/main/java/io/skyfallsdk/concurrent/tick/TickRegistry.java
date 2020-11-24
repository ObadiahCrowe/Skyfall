package io.skyfallsdk.concurrent.tick;

import io.skyfallsdk.Server;

import java.util.Optional;

public interface TickRegistry<T extends TickSpec<T>> {

    static <T extends TickSpec<T>> TickRegistry<T> getBySpec(T tickSpec) {
        return Server.get().getTickRegistry(tickSpec);
    }

    default long getTickLength() {
        return this.getSpec().getTickLength();
    }

    Optional<Tickable<T>> getById(long id);

    long getId(Tickable<T> tickable);

    long getLastExecutionTime(Tickable<T> tickable);

    long getLastExecutionTime(long id);

    T getSpec();
}
