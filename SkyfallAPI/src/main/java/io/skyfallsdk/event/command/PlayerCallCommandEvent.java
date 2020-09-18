package io.skyfallsdk.event.command;

import io.skyfallsdk.command.Command;
import io.skyfallsdk.event.Cancellable;
import io.skyfallsdk.event.player.PlayerEvent;
import io.skyfallsdk.player.Player;

public class PlayerCallCommandEvent extends PlayerEvent implements Cancellable {

    private final Command command;
    private final String[] args;
    private boolean cancelled;

    public PlayerCallCommandEvent(Player who, Command command, String[] args) {
        super(who);

        this.command = command;
        this.args = args;
    }

    public Command getCommand() {
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
