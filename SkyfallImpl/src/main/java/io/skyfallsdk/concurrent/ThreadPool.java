package io.skyfallsdk.concurrent;

import com.google.common.collect.Maps;
import io.skyfallsdk.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@ThreadSafe
public class ThreadPool implements ScheduledExecutorService, Scheduler {

    private static final Map<PoolSpec, ThreadPool> THREAD_POOLS = Maps.newHashMap();

    private final PoolSpec spec;
    private final ExecutorService service;
    private final boolean isScheduledService;

    protected ThreadPool(ExecutorService service, PoolSpec spec) {
        this.service = service;
        this.spec = spec;
        this.isScheduledService = service instanceof ScheduledExecutorService;
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        if (this.isScheduledService) {
            return ((ScheduledExecutorService) this.service).schedule(command, delay, unit);
        }

        return Executors.newSingleThreadScheduledExecutor().schedule(command, delay, unit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        if (this.isScheduledService) {
            return ((ScheduledExecutorService) this.service).schedule(callable, delay, unit);
        }

        return Executors.newSingleThreadScheduledExecutor().schedule(callable, delay, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        if (this.isScheduledService) {
            return ((ScheduledExecutorService) this.service).scheduleAtFixedRate(command, initialDelay, period, unit);
        }

        return Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        if (this.isScheduledService) {
            return ((ScheduledExecutorService) this.service).scheduleWithFixedDelay(command, initialDelay, delay, unit);
        }

        return Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    @Override
    public void shutdown() {
        this.service.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return this.service.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return this.service.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return this.service.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return this.service.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return this.service.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return this.service.submit(task, result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return this.service.submit(task);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return this.service.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return this.service.invokeAll(tasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return this.service.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.service.invokeAny(tasks, timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        this.service.execute(command);
    }

    public static ThreadPool createForSpec(PoolSpec spec) {
        return THREAD_POOLS.computeIfAbsent(spec, func -> {
            int maxThreads = spec.getMaxThreads();

            if (spec.isStealing()) {
                return new ThreadPool(new ForkJoinPool(maxThreads, spec, spec, true), spec);
            }

            return new ThreadPool(new ScheduledThreadPoolExecutor(1, spec, (r, executor) -> Server.get().getLogger().error("Could not add new thread to " + spec.getName() + " pool!")), spec);
        });
    }

    public static void initDefaultPools() {
        createForSpec(PoolSpec.CHUNKS);
        createForSpec(PoolSpec.ENTITIES);
        createForSpec(PoolSpec.PLAYERS);
        createForSpec(PoolSpec.SCHEDULER);
        createForSpec(PoolSpec.WORLD);
    }

    public static void shutdownAll() {
        for (ThreadPool pool : THREAD_POOLS.values()) {
            Server.get().getLogger().info("Shutting down ThreadPool: " + pool.spec.getName());
            pool.shutdown();
        }
    }
}
