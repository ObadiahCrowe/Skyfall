package io.skyfallsdk;

import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.concurrent.ThreadPool;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SkyfallServer implements Server {

    SkyfallServer() {
    }

    @Override
    public Scheduler getScheduler() {
        return ThreadPool.createForSpec(PoolSpec.SCHEDULER);
    }
}
