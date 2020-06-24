package net.treasurewars.core.command.parameter.service;

import net.treasurewars.core.module.Module;
import net.treasurewars.core.module.ModuleManager;

public class ModuleServiceProvider implements ServiceProvider {

    @Override
    public boolean canProvide(Class<?> serviceClass) {
        if (!Module.class.isAssignableFrom(serviceClass)) {
            return false;
        }

        Class<? extends Module> moduleClass = (Class<? extends Module>) serviceClass;
        return ModuleManager.getModule(moduleClass) != null;
    }

    @Override
    public <T> T getService(Class<T> serviceClass) {
        Class<? extends Module> moduleClass = (Class<? extends Module>) serviceClass;
        return (T) ModuleManager.getModule(moduleClass);
    }
}
