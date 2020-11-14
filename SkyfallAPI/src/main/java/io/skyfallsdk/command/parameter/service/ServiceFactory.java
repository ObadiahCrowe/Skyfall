package io.skyfallsdk.command.parameter.service;

import com.google.common.collect.Lists;

import java.util.List;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE.registerProvider(new ExpansionServiceProvider());
        INSTANCE.registerProvider(new ServerServiceProvider());
        INSTANCE.registerProvider(new SchedulerServiceProvider());
    }

    private final List<ServiceProvider> providers;

    private ServiceFactory() {
        this.providers = Lists.newArrayList();
    }

    public void registerProvider(ServiceProvider provider) {
        this.providers.add(provider);
    }

    public <T> T getService(Class<T> serviceClass) {
        for (ServiceProvider provider : this.providers) {
            if (!provider.canProvide(serviceClass)) {
                continue;
            }

            return provider.getService(serviceClass);
        }

        throw new IllegalArgumentException("No provider was able to provide service for \"" + serviceClass.getName() + "\"!");
    }
}
