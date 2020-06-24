package net.treasurewars.core.command.parameter.service;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginServiceProvider implements ServiceProvider {

    @Override
    public boolean canProvide(Class<?> serviceClass) {
        return JavaPlugin.class.isAssignableFrom(serviceClass);
    }

    @Override
    public <T> T getService(Class<T> serviceClass) {
        Class<? extends JavaPlugin> pluginClass = (Class<? extends JavaPlugin>) serviceClass;
        return (T) JavaPlugin.getPlugin(pluginClass);
    }
}
