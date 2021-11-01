package io.skyfallsdk.command.parameter.service;

import io.skyfallsdk.command.Command;
import io.skyfallsdk.command.parameter.CommandParameter;
import io.skyfallsdk.command.CommandSender;

public class ServiceParameter<T> implements CommandParameter<T> {

    private final Class<T> serviceClass;

    public ServiceParameter(Class<T> serviceClass) {
        this.serviceClass = serviceClass;
    }

    @Override
    public T parse(CommandSender sender, Command command, String[] args) throws Exception {
        return ServiceFactory.getInstance().getService(this.serviceClass);
    }
}
