package io.skyfallsdk.concurrent.tick;

import it.unimi.dsi.fastutil.longs.Long2LongArrayMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerTickRegistry<T extends TickSpec<T>> implements TickRegistry<T> {

    private static final Object2ObjectOpenHashMap<TickSpec<?>, ServerTickRegistry<?>> TICK_REGISTRIES = new Object2ObjectOpenHashMap<>();

    static {
        for (DefaultTickSpec tickSpec : DefaultTickSpec.values()) {
            TICK_REGISTRIES.put(tickSpec, new ServerTickRegistry<>(tickSpec));
        }
    }

    private final T spec;
    private final AtomicInteger nextId;
    private final Object2LongOpenHashMap<Tickable<T>> tickableToId;
    private final Long2LongArrayMap executionTimes;

    private ServerTickRegistry(T spec) {
        this.spec = spec;
        this.nextId = new AtomicInteger();
        this.tickableToId = new Object2LongOpenHashMap<>();
        this.executionTimes = new Long2LongArrayMap();
    }

    public @NotNull Object2LongMap.FastEntrySet<@NotNull Tickable<T>> getTickables() {
        return this.tickableToId.object2LongEntrySet();
    }

    public void setLastExecutionTime(long tickableId, long lastExecutionTime) {
        this.executionTimes.put(tickableId, lastExecutionTime);
    }

    @Override
    public @NotNull Optional<@NotNull Tickable<T>> getById(long id) {
        return this.tickableToId.object2LongEntrySet().stream()
          .filter(entry -> entry.getLongValue() == id)
          .map(Map.Entry::getKey)
          .findFirst();
    }

    @Override
    public void register(@NotNull Tickable<T> tickable) {
        this.tickableToId.put(tickable, this.nextId.incrementAndGet());
    }

    @Override
    public long getId(@NotNull Tickable<T> tickable) {
        if (!this.tickableToId.containsKey(tickable)) {
            throw new NoSuchElementException("This TickRegistry does not include this Tickable.");
        }

        return this.tickableToId.getLong(tickable);
    }

    @Override
    public long getLastExecutionTime(@NotNull Tickable<T> tickable) {
        return this.executionTimes.get(this.getId(tickable));
    }

    @Override
    public long getLastExecutionTime(long id) {
        return this.executionTimes.get(id);
    }

    @Override
    public @NotNull T getSpec() {
        return this.spec;
    }

    @SuppressWarnings("unchecked")
    public static <T extends TickSpec<T>> ServerTickRegistry<T> getTickRegistry(T spec) {
        return (ServerTickRegistry<T>) TICK_REGISTRIES.computeIfAbsent(spec, ts -> new ServerTickRegistry<>(spec));
    }
}
