package io.skyfallsdk.command;

import com.google.common.collect.Lists;
import io.skyfallsdk.command.exception.CommandException;
import io.skyfallsdk.command.options.TabCompleter;
import io.skyfallsdk.server.CommandSender;
import net.treasurewars.core.command.parameter.CommandParameter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public class TabCompleterMethod extends CommandMethodWrapper {

    public static TabCompleterMethod fromMethod(CoreCommand command, Object commandInstance, Method method) {
        TabCompleter annotation = method.getAnnotation(TabCompleter.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Method is not tab completer!");
        }

        CommandParameter[] parameters = CommandParameter.getParameters(method);
        return new TabCompleterMethod(command, commandInstance, method, parameters, annotation.argument(), annotation.targetMethod());
    }

    private final int targetArgument;
    private final String targetMethod;

    public TabCompleterMethod(CoreCommand command, Object commandInstance, Method method, CommandParameter[] parameters, int argument, String targetMethod) {
        super(command, commandInstance, method, parameters);
        this.targetArgument = argument;
        this.targetMethod = targetMethod;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public int getTargetArgument() {
        return targetArgument;
    }

    public Collection<String> call(CommandSender sender, String[] args) throws InvocationTargetException, IllegalAccessException {
        if (this.getParameters().length == 0) {
            return (Collection<String>) this.getMethod().invoke(this.getCommandInstance());
        }

        Object[] values = new Object[this.getParameters().length];
        for (int i = 0; i < this.getParameters().length; i++) {
            try {
                values[i] = this.getParameters()[i].parse(sender, this.getCommand(), args);
            } catch (Throwable t) {
                if (t instanceof CommandException) {
                    this.getCommand().printError(sender, t.getMessage());
                    return Lists.newArrayList();
                }

                sender.sendMessage(MessageTemplate.UNEXPECTED_ERROR);
                t.printStackTrace();
                return Lists.newArrayList();
            }
        }

        return (Collection<String>) this.getMethod().invoke(this.getCommandInstance(), values);
    }
}
