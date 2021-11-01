package io.skyfallsdk.concurrent.tick;

import io.skyfallsdk.Server;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface TickRegistry<T extends TickSpec<T>> {

    static <T extends TickSpec<T>> TickRegistry<T> getBySpec(T tickSpec) {
        return Server.get().getTickRegistry(tickSpec);
    }

    default long getTickLength() {
        return this.getSpec().getTickLength();
    }

    @NotNull
    Optional<@NotNull Tickable<T>> getById(long id);

    void register(@NotNull Tickable<T> tickable);

    long getId(@NotNull Tickable<T> tickable);

    long getLastExecutionTime(@NotNull Tickable<T> tickable);

    long getLastExecutionTime(long id);

    @NotNull
    T getSpec();
}
