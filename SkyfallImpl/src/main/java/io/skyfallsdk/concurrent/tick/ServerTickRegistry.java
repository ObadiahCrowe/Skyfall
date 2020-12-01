package io.skyfallsdk.concurrent.tick;

import it.unimi.dsi.fastutil.longs.Long2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

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
    private final Long2IntArrayMap executionTimes;

    private ServerTickRegistry(T spec) {
        this.spec = spec;
        this.nextId = new AtomicInteger();
        this.tickableToId = new Object2LongOpenHashMap<>();
        this.executionTimes = new Long2IntArrayMap();
    }

    @Override
    public Optional<Tickable<T>> getById(long id) {
        return this.tickableToId.object2LongEntrySet().stream()
          .filter(entry -> entry.getLongValue() == id)
          .map(Map.Entry::getKey)
          .findFirst();
    }

    @Override
    public long getId(Tickable<T> tickable) {
        if (!this.tickableToId.containsKey(tickable)) {
            throw new NoSuchElementException("This TickRegistry does not include this Tickable.");
        }

        return this.tickableToId.getLong(tickable);
    }

    @Override
    public long getLastExecutionTime(Tickable<T> tickable) {
        return this.executionTimes.get(this.getId(tickable));
    }

    @Override
    public long getLastExecutionTime(long id) {
        return this.executionTimes.get(id);
    }

    @Override
    public T getSpec() {
        return this.spec;
    }

    @SuppressWarnings("unchecked")
    public static <T extends TickSpec<T>> ServerTickRegistry<T> getTickRegistry(T spec) {
        return (ServerTickRegistry<T>) TICK_REGISTRIES.computeIfAbsent(spec, ts -> new ServerTickRegistry<>(spec));
    }
}
