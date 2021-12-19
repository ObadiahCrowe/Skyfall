package io.skyfallsdk.concurrent.tick;

import io.skyfallsdk.Server;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TickRegistry<T extends TickSpec<T>> {

    static <T extends TickSpec<T>> TickRegistry<T> getBySpec(T tickSpec) {
        return Server.get().getTickRegistry(tickSpec);
    }

    static @NotNull Collection<? extends @NotNull TickRegistry<?>> getTickRegisteries() {
        return Server.get().getTickRegisteries();
    }

    static <T extends TickSpec<T>> long getCombinedTickLength() {
        long length = 0L;

        for (TickRegistry<?> registry : Server.get().getTickRegisteries()) {
            length += registry.getLastTickLength();
        }

        return length;
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

    long getLastTickLength();

    @NotNull
    T getSpec();
}
