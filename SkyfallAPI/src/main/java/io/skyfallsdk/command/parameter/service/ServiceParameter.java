package net.treasurewars.core.command.parameter.service;

import net.treasurewars.core.command.CoreCommand;
import net.treasurewars.core.command.parameter.CommandParameter;
import org.bukkit.command.CommandSender;

public class ServiceParameter<T> implements CommandParameter<T> {

    private final Class<T> serviceClass;

    public ServiceParameter(Class<T> serviceClass) {
        this.serviceClass = serviceClass;
    }

    @Override
    public boolean forceAsync() {
        return false;
    }

    @Override
    public boolean supportsAsync() {
        return true;
    }

    @Override
    public T parse(CommandSender sender, CoreCommand command, String[] args) throws Exception {
        return ServiceFactory.getInstance().getService(this.serviceClass);
    }
}
