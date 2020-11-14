package io.skyfallsdk.command.parameter.context;

import com.google.common.collect.Maps;

import java.util.Map;

public class CommandContext {

    private final Map<Class<?>, ContextContainer<?>> data;

    public CommandContext() {
        this.data = Maps.newConcurrentMap();
    }

    public <T> boolean hasContext(Class<T> tClass) {
        ContextContainer<T> container = (ContextContainer<T>) this.data.get(tClass);
        if (container == null) {
            return false;
        }

        if (container.isExpired()) {
            container.setValue(null);
            container.setExpiry(-1);
            return false;
        }

        return true;
    }

    public <T> ContextContainer<T> getContext(Class<T> tClass) {
        ContextContainer<T> container = (ContextContainer<T>) this.data.get(tClass);
        if (container == null) {
            return new ContextContainer<>(tClass);
        }

        if (container.isExpired()) {
            container.setValue(null);
            container.setExpiry(-1);
        }

        return container;
    }

    public <T> void setContext(ContextContainer<T> container) {
        if (container == null) {
            throw new IllegalArgumentException("Context container can not be null!");
        }

        this.data.put(container.getClass(), container);
    }
}