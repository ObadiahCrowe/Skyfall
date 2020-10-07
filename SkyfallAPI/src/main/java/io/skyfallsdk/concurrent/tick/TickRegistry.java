package io.skyfallsdk.concurrent.tick;

import java.util.Optional;

public interface TickRegistry<T extends TickSpec> {

    static <T extends TickSpec> TickRegistry<T> getBySpec(T tickSpec) {
        return null;
    }

    default long getTickLength() {
        return 50L;
    }

    Optional<Tickable<T>> getById(long id);

    long getId(Tickable<T> tickable);

    long getLastExecutionTime(long id);

    T getSpec();
}
