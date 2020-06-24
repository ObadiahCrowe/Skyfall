package io.skyfallsdk;

import com.google.gson.Gson;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.server.CommandSender;
import org.apache.logging.log4j.Logger;

public interface Server extends CommandSender {

    static Server get() {
        return null;
    }

    Logger getLogger();

    Scheduler getScheduler();
}
