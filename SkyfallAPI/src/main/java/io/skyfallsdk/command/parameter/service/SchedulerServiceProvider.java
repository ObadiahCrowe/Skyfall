package io.skyfallsdk.command.parameter.service;

import io.skyfallsdk.Server;
import io.skyfallsdk.concurrent.Scheduler;

public class SchedulerServiceProvider implements ServiceProvider {

    @Override
    public boolean canProvide(Class<?> serviceClass) {
        return Scheduler.class.isAssignableFrom(serviceClass);
    }

    @Override
    public <T> T getService(Class<T> serviceClass) {
        return (T) Server.get().getScheduler();
    }
}
