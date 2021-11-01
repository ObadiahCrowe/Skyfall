package io.skyfallsdk.command.parameter;

import io.skyfallsdk.Server;
import io.skyfallsdk.command.Command;
import io.skyfallsdk.command.exception.CommandException;
import io.skyfallsdk.concurrent.Scheduler;
import io.skyfallsdk.command.CommandSender;

public class ParameterParsingTask<T> implements Runnable {

    private final CommandSender sender;
    private final Command command;
    private final String[] args;
    private final CommandParameter<T> parameter;
    private final Scheduler scheduler;
    private final ParsingListener listener;

    public ParameterParsingTask(CommandSender sender, Command command, String[] args, CommandParameter<T> parameter, ParsingListener<T> listener, boolean first) {
        this.sender = sender;
        this.command = command;
        this.args = args;
        this.parameter = parameter;
        this.scheduler = Server.get().getScheduler();
        this.listener = listener;
    }

    public void execute() {
        this.scheduler.execute(this);
    }

    @Override
    public void run() {
        try {
            T value = this.parameter.parse(this.sender, this.command, this.args);
            this.listener.onSuccess(this.parameter, value);
        } catch (Throwable t) {
            if (!(t instanceof CommandException)) {
                t.printStackTrace();
                this.listener.onFailure(this.parameter, "An internal error occurred!");
                return;
            }

            this.listener.onFailure(this.parameter, t.getMessage());
        }
    }
}
