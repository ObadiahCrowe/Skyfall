package net.treasurewars.core.command.parameter.service;

import net.treasurewars.core.module.data.DataLoader;

public class DataLoaderServiceProvider implements ServiceProvider {

    @Override
    public boolean canProvide(Class<?> serviceClass) {
        if (DataLoader.class.isAssignableFrom(serviceClass)) {
            return false;
        }

        return DataLoader.getByClass(serviceClass) != null;
    }

    @Override
    public <T> T getService(Class<T> serviceClass) {
        return (T) DataLoader.getByClass(serviceClass);
    }
}
