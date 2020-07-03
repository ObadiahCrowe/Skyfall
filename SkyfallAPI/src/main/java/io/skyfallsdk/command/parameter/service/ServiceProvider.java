package io.skyfallsdk.command.parameter.service;

public interface ServiceProvider {

    boolean canProvide(Class<?> serviceClass);

    <T> T getService(Class<T> serviceClass);
}
