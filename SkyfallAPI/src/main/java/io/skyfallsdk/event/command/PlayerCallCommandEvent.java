package io.skyfallsdk.event.command;

import io.skyfallsdk.command.CoreCommand;
import io.skyfallsdk.event.Cancellable;
import io.skyfallsdk.event.player.PlayerEvent;
import io.skyfallsdk.player.Player;

public class PlayerCallCommandEvent extends PlayerEvent implements Cancellable {

    private final CoreCommand command;
    private final String[] args;
    private boolean cancelled;

    public PlayerCallCommandEvent(Player who, CoreCommand command, String[] args) {
        super(who);

        this.command = command;
        this.args = args;
    }

    public CoreCommand getCommand() {
        return this.command;
    }

    public String[] getArgs() {
        return this.args;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
