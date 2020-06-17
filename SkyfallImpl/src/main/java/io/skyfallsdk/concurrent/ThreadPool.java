package io.skyfallsdk.concurrent;

import com.google.common.collect.Sets;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@ThreadSafe
public class ThreadPool implements ScheduledExecutorService, Scheduler {

    private final ExecutorService service;
    private final Set<Thread> scheduledThreads;

    protected ThreadPool(ExecutorService service) {
        this.service = service;
        this.scheduledThreads = service instanceof ScheduledExecutorService ? null : Sets.newConcurrentHashSet();
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        if (this.scheduledThreads == null) {
            return ((ScheduledExecutorService) this.service).schedule(command, delay, unit);
        }

        if (command == null || unit == null) {
            throw new NullPointerException();
        }

        if (delay <= 0) {
            throw new IllegalArgumentException();
        }



        // TODO: 17/06/2020
        return null;
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        if (this.scheduledThreads == null) {
            return ((ScheduledExecutorService) this.service).schedule(callable, delay, unit);
        }

        // TODO: 17/06/2020
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        if (this.scheduledThreads == null) {
            return ((ScheduledExecutorService) this.service).scheduleAtFixedRate(command, initialDelay, period, unit);
        }

        // TODO: 17/06/2020
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        if (this.scheduledThreads == null) {
            return ((ScheduledExecutorService) this.service).scheduleWithFixedDelay(command, initialDelay, delay, unit);
        }

        // TODO: 17/06/2020
        return null;
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
        return null;
    }
}
