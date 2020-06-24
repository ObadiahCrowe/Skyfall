package io.skyfallsdk.command;

import net.treasurewars.core.command.parameter.CommandParameter;
import net.treasurewars.core.command.parameter.argument.CommandArgument;
import net.treasurewars.core.command.parameter.sender.SenderParameter;

import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class CommandMethodWrapper extends AnnotatedPermissible {

    private final CoreCommand command;
    private final Object commandInstance;
    private final Method method;
    private final CommandParameter[] parameters;

    public CommandMethodWrapper(CoreCommand command, Object commandInstance, Method method, CommandParameter[] parameters) {
        super(method);

        this.command = command;
        this.commandInstance = commandInstance;
        this.method = method;
        this.parameters = parameters;
    }

    public CoreCommand getCommand() {
        return command;
    }

    public Object getCommandInstance() {
        return commandInstance;
    }

    public Method getMethod() {
        return method;
    }

    public CommandArgument[] getArguments() {
        return Arrays.stream(this.getParameters())
                .filter(parameter -> parameter instanceof CommandArgument)
                .map(parameter -> (CommandArgument) parameter)
                .toArray(CommandArgument[]::new);
    }

    public CommandParameter[] getParameters() {
        return parameters;
    }

    @Override
    public boolean isPlayerOnly() {
        return !Arrays.stream(this.parameters)
                .filter(parameter -> parameter instanceof SenderParameter)
                .map(parameter -> (SenderParameter) parameter)
                .allMatch(SenderParameter::supportsCommandSender);
    }
}
