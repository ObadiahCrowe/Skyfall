package io.skyfallsdk.command;

import com.google.common.collect.ImmutableList;
import io.skyfallsdk.Server;
import io.skyfallsdk.command.exception.CommandException;
import io.skyfallsdk.command.options.CommandExecutor;
import io.skyfallsdk.command.parameter.CommandParameter;
import io.skyfallsdk.command.parameter.ParameterParsingTask;
import io.skyfallsdk.command.parameter.ParsingListener;
import io.skyfallsdk.command.parameter.argument.ArgumentParseException;
import io.skyfallsdk.command.parameter.argument.ArgumentSpecification;
import io.skyfallsdk.command.parameter.argument.CommandArgument;
import io.skyfallsdk.command.parameter.argument.signature.CommandSignature;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.concurrent.ThreadUnsafe;
import io.skyfallsdk.server.CommandSender;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class CommandExecutorMethod extends CommandMethodWrapper {

    public static CommandExecutorMethod fromMethod(Command command, Object commandInstance, Method method) {
        CommandExecutor annotation = method.getAnnotation(CommandExecutor.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Method is not command executor!");
        }

        CommandParameter[] parameters = CommandParameter.getParameters(method);
        ThreadUnsafe threadUnsafeAnnot = method.getAnnotation(ThreadUnsafe.class);
        Class threadUnsafe = threadUnsafeAnnot == null ? null : threadUnsafeAnnot.value();
        return new CommandExecutorMethod(command, commandInstance, method, parameters, Server.get().getScheduler(), threadUnsafe);
    }

    private final Scheduler scheduler;
    private final int minArgs;
    private final int maxArgs;
    private final Class threadUnsafe;
    private final AtomicBoolean running;

    public CommandExecutorMethod(Command command, Object commandInstance, Method method, CommandParameter[] parameters, Scheduler scheduler, Class threadUnsafe) {
        super(command, commandInstance, method, parameters);
        this.scheduler = scheduler;
        this.threadUnsafe = threadUnsafe;
        this.running = this.threadUnsafe != null ? new AtomicBoolean(false) : null;

        ArgumentSpecification specification = new ArgumentSpecification();
        for (CommandParameter parameter : this.getParameters()) {
            if (!(parameter instanceof CommandArgument)) {
                continue;
            }

            ((CommandArgument) parameter).applySpecifications(specification);
        }

        this.minArgs = specification.getMinArgs();
        this.maxArgs = specification.getMaxArgs();
    }

    public CommandSignature createSignature() {
        CommandSignature builder = new CommandSignature(this.getCommand());
        for (CommandArgument argument : this.getArguments()) {
            builder.addParameter(argument.getDescription(), argument.isOptional());
        }

        return builder;
    }

    public Collection<String> tabComplete(CommandSender sender, String[] args) throws ArgumentParseException {
        CommandArgument target = null;
        int currIndex = args.length - 1;
        for (CommandParameter parameter : this.getParameters()) {
            if (!(parameter instanceof CommandArgument)) {
                continue;
            }

            int firstIndex = ((CommandArgument) parameter).getFirstArgsIndex();
            if (firstIndex != currIndex) {
                // Current index is before the parameter
                if (firstIndex > currIndex) {
                    continue;
                }

                int lastIndex = ((CommandArgument) parameter).getLastArgsIndex();
                // Current index is after this parameter
                if (lastIndex != -1 && lastIndex < currIndex) {
                    continue;
                }
            }

            target = (CommandArgument) parameter;
            break;
        }

        if (target == null) {
            return ImmutableList.of();
        }

        return target.complete(sender, args);
    }

    public Future<CommandException> call(CommandSender sender, String[] args) throws InterruptedException {
        CompletableFuture<CommandException> future = new CompletableFuture<>();
        if (this.getParameters().length == 0) {
            this.waitForExecute();

            this.scheduler.execute(() -> {
                // Don't really see any case where this would be useful, but let's handle it anyway
                try {
                    getMethod().invoke(getCommandInstance());
                    future.complete(null);
                } catch (Throwable t) {
                    if (t instanceof CommandException) {
                        future.complete((CommandException) t);
                    } else {
                        t.printStackTrace();
                        future.complete(new CommandException(t));
                    }
                } finally {
                    this.closeExecute();
                }
            });

            return future;
        }

        if (args.length < this.minArgs || (this.maxArgs != -1 && args.length > this.maxArgs)) {
            if (this.minArgs == this.maxArgs) {
                future.complete(new ArgumentParseException("Incorrect amount of arguments! Please provide " + this.minArgs +
                        (this.minArgs == 1 ? " argument!" : " arguments!")));
            } else if (this.maxArgs == -1) {
                future.complete(new ArgumentParseException("Incorrect amount of arguments! Please provide at least " + this.minArgs +
                        (this.minArgs == 1 ? " argument!" : " arguments!")));
            } else {
                future.complete(new ArgumentParseException("Incorrect amount of arguments! Please provide between " + this.minArgs +
                        " and " + this.maxArgs + " arguments!"));
            }

            return future;
        }

        this.waitForExecute();

        new MethodParsingListenerImpl(sender, args, new ParsingListener<Object[]>() {
            @Override
            public void onSuccess(CommandParameter parameter, Object[] value) {
                scheduler.execute(() -> {
                    try {
                        getMethod().invoke(getCommandInstance(), value);
                        future.complete(null);
                    } catch (Throwable t) {
                        if (t instanceof CommandException) {
                            future.complete((CommandException) t);
                            return;
                        }

                        Throwable cause = t.getCause();
                        if (cause instanceof CommandException) {
                            future.complete((CommandException) cause);
                            return;
                        }

                        t.printStackTrace();
                        future.complete(new CommandException("An unexpected error occurred: " + t.getMessage()));
                    } finally {
                        closeExecute();
                    }
                });
            }

            @Override
            public void onFailure(CommandParameter parameter, String message) {
                closeExecute();
                future.complete(new ArgumentParseException(message));
            }
        });

        return future;
    }

    private void waitForExecute() throws InterruptedException {
        if (this.threadUnsafe == null) {
            return;
        }

        synchronized (this.threadUnsafe) {
            while (this.running.get()) {
                this.threadUnsafe.wait();
            }

            this.running.set(true);
        }
    }

    private void closeExecute() {
        if (this.threadUnsafe == null) {
            return;
        }

        synchronized (this.threadUnsafe) {
            this.running.set(false);
            this.threadUnsafe.notify();
        }
    }

    private class MethodParsingListenerImpl implements ParsingListener {

        private final CommandSender sender;
        private final String[] args;
        private final Object[] values;
        private final ParsingListener<Object[]> finalTask;
        private int index;

        public MethodParsingListenerImpl(CommandSender sender, String[] args, ParsingListener<Object[]> finalTask) {
            this.sender = sender;
            this.args = args;
            this.finalTask = finalTask;

            // There should be 1 CommandParameter for each method parameter
            this.values = new Object[getParameters().length];
            this.index = 0;

            this.executeNext();
        }

        private void executeNext() {
            boolean first = this.index == 0;
            CommandParameter parameter = getParameters()[this.index];

            new ParameterParsingTask(this.sender, getCommand(), this.args, parameter, this, first).execute();
        }

        @Override
        public void onSuccess(CommandParameter parameter, Object value) {
            this.values[this.index] = value;

            if (++this.index >= this.values.length) {
                this.finalTask.onSuccess(null, this.values);
                return;
            }

            this.executeNext();
        }

        @Override
        public void onFailure(CommandParameter parameter, String message) {
            this.finalTask.onFailure(parameter, message);
        }
    }
}
