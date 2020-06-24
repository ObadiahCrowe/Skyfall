package net.treasurewars.core.command.parameter;

import net.treasurewars.core.command.CoreCommand;
import net.treasurewars.core.command.options.Sender;
import net.treasurewars.core.command.parameter.argument.Arg;
import net.treasurewars.core.command.parameter.argument.CommandArgument;
import net.treasurewars.core.command.parameter.sender.*;
import net.treasurewars.core.command.parameter.service.Service;
import net.treasurewars.core.command.parameter.service.ServiceParameter;
import net.treasurewars.core.modules.player.data.CorePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

            if (type.isAssignableFrom(CorePlayer.class)) {
                return (CommandParameter<T>) new SenderCorePlayerParameter();
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

    boolean forceAsync();

    boolean supportsAsync();

    T parse(CommandSender sender, CoreCommand command, String[] args) throws Exception;
}
