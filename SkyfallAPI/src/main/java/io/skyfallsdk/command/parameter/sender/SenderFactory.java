package net.treasurewars.core.command.parameter.sender;

import com.google.common.collect.Maps;

import java.util.Map;

public class SenderFactory {

    private static final SenderFactory INSTANCE = new SenderFactory();

    public static SenderFactory getInstance() {
        return INSTANCE;
    }

    private final Map<Class<?>, SenderParameter<?>> senderParameterByClass;

    private SenderFactory() {
        this.senderParameterByClass = Maps.newConcurrentMap();
    }

    public <T> void register(Class<T> senderClass, SenderParameter<T> parameter) {
        this.senderParameterByClass.put(senderClass, parameter);
    }

    public <T> SenderParameter<T> getParameter(Class<T> senderClass) {
        return (SenderParameter<T>) this.senderParameterByClass.get(senderClass);
    }
}
