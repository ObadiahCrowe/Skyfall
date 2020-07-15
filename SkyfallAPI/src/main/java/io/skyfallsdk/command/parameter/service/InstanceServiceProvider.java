package io.skyfallsdk.command.parameter.service;

import io.skyfallsdk.command.parameter.service.ServiceProvider;

public class InstanceServiceProvider implements ServiceProvider {

    private final Object service;

    public InstanceServiceProvider(Object service) {
        this.service = service;
    }

    @Override
    public boolean canProvide(Class<?> serviceClass) {
        return this.service.getClass() == serviceClass;
    }

    @Override
    public <T> T getService(Class<T> serviceClass) {
        return (T) this.service;
    }
}
