package io.skyfallsdk.concurrent;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * A wrapper around the Skyfall extension thread pool. All tasks are run asynchronously.
 */
@ThreadSafe
public interface Scheduler {

    /**
     * Schedules a task to be executed after the given delay.
     *
     * @param command Action to run after the delay.
     * @param delay Amount to delay by.
     * @param unit TimeUnit to delay by.
     *
     * @return A future representing the pending task.
     */
    ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);

    /**
     *
     * @param callable
     * @param delay
     * @param unit
     * @param <V>
     * @return
     */
    <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit);

    ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit);

    ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit);

    <T> Future<T> submit(Callable<T> task);

    <T> Future<T> submit(Runnable task, T result);

    Future<?> submit(Runnable task);

    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException;

    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException;

    <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException;

    <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;

    void execute(Runnable command);
}
