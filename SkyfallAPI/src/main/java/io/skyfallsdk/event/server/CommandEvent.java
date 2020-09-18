package io.skyfallsdk.event.server;

import io.skyfallsdk.command.CoreCommand;
import io.skyfallsdk.event.Event;

public abstract class CommandEvent implements Event {

    private final CoreCommand command;

    public CommandEvent(CoreCommand command) {
        this.command = command;
    }

    public CoreCommand getCommand() {
        return this.command;
    }
}
