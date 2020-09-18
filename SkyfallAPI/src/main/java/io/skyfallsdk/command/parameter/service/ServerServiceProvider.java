package io.skyfallsdk.command.parameter.service;

import io.skyfallsdk.Server;

public class ServerServiceProvider implements ServiceProvider {

    @Override
    public boolean canProvide(Class<?> serviceClass) {
        return Server.class.isAssignableFrom(serviceClass);
    }

    @Override
    public <T> T getService(Class<T> serviceClass) {
        return (T) Server.get();
    }
}
