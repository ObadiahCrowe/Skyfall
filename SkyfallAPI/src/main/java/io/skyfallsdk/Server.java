package io.skyfallsdk;

import io.skyfallsdk.concurrent.Scheduler;

public interface Server {

    static Server get() {
        return null;
    }

    Scheduler getScheduler();
}
