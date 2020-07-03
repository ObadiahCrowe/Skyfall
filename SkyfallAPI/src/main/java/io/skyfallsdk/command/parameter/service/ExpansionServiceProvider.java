package io.skyfallsdk.command.parameter.service;

import io.skyfallsdk.Server;
import io.skyfallsdk.expansion.Expansion;

public class ExpansionServiceProvider implements ServiceProvider {

    @Override
    public boolean canProvide(Class<?> serviceClass) {
        return Expansion.class.isAssignableFrom(serviceClass);
    }

    @Override
    public <T> T getService(Class<T> serviceClass) {
        Class<? extends Expansion> expansionClass = (Class<? extends Expansion>) serviceClass;

        return (T) Server.get().getExpansion(expansionClass);
    }
}
