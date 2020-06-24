package io.skyfallsdk;

import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.server.CommandSender;

public interface Server extends CommandSender {

    static Server get() {
        return null;
    }

    Scheduler getScheduler();
}
