package io.skyfallsdk.event.server;

import io.skyfallsdk.command.Command;
import io.skyfallsdk.event.Event;

public abstract class CommandEvent implements Event {

    private final Command command;

    public CommandEvent(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return this.command;
    }
}
