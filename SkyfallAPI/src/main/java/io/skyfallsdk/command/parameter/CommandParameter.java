package io.skyfallsdk.command.parameter;

import io.skyfallsdk.command.Command;
import io.skyfallsdk.command.options.Sender;
import io.skyfallsdk.command.parameter.argument.Arg;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.command.parameter.sender.SenderCommandSenderParameter;
import io.skyfallsdk.command.parameter.sender.SenderFactory;
import io.skyfallsdk.command.parameter.sender.SenderParameter;
import io.skyfallsdk.command.parameter.sender.SenderPlayerParameter;
import io.skyfallsdk.command.parameter.service.Service;
import io.skyfallsdk.command.parameter.service.ServiceParameter;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.server.CommandSender;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.atomic.AtomicInteger;

public interface CommandParameter<T> {

    static CommandParameter[] getParameters(Method method) {
        CommandParameter[] parameters = new CommandParameter[method.getParameterCount()];
        AtomicInteger varIndex = new AtomicInteger(0);
        Parameter[] methodParameters = method.getParameters();
        for (int i = 0; i < methodParameters.length; i++) {
            Parameter parameter = methodParameters[i];
            parameters[i] = CommandParameter.fromMethodParameter(varIndex, parameter);
        }

        return parameters;
    }

    static <T> CommandParameter<T> fromMethodParameter(AtomicInteger fieldIndex, Parameter parameter) {
        Class<?> type = parameter.getType();
        if (parameter.isAnnotationPresent(Service.class)) {
            return new ServiceParameter<T>((Class<T>) type);
        }

        Sender senderAnnot = parameter.getAnnotation(Sender.class);
        if (senderAnnot != null) {
            // Check if explicit registered
            SenderParameter<?> senderParameter = SenderFactory.getInstance().getParameter(type);
            if (senderParameter != null) {
                return (CommandParameter<T>) senderParameter;
            }

            // Start at weakest (CommandSender) since CommandSender is assignable from Player,
            // which would cause any actual console support commands to be player only
            if (type.isAssignableFrom(CommandSender.class)) {
                return (CommandParameter<T>) new SenderCommandSenderParameter();
            }

            if (type.isAssignableFrom(Player.class)) {
                return (CommandParameter<T>) new SenderPlayerParameter(senderAnnot.allowNull());
            }

            throw new IllegalArgumentException("Parameter is annotated \"Sender\" but type doesn't implement CommandSender!");
        }

        Arg argAnnot = parameter.getAnnotation(Arg.class);
        if (argAnnot == null) {
            throw new IllegalArgumentException("Couldn't parse " + parameter + "!");
        }

        // Only arguments have field indexes
        int index = fieldIndex.getAndIncrement();
        return new CommandArgument<T>(index, argAnnot, (Class<T>) type, parameter.getName());
    }

    T parse(CommandSender sender, Command command, String[] args) throws Exception;
}
