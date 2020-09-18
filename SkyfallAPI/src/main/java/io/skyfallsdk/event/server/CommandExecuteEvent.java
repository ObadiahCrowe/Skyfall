package io.skyfallsdk.event.server;

import io.skyfallsdk.command.CoreCommand;
import io.skyfallsdk.event.Cancellable;
import io.skyfallsdk.server.CommandSender;

public class CommandExecuteEvent extends CommandEvent implements Cancellable {

    private final CommandSender sender;
    private final String[] args;

    private boolean cancelled;

    public CommandExecuteEvent(CommandSender sender, CoreCommand command, String[] args) {
        super(command);

        this.sender = sender;
        this.args = args;

        this.cancelled = false;
    }

    public CommandSender getSender() {
        return this.sender;
    }

    public String[] getArgs() {
        return this.args;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}
