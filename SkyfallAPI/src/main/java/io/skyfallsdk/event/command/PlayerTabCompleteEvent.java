package io.skyfallsdk.event.command;

import io.skyfallsdk.command.CoreCommand;
import io.skyfallsdk.event.player.PlayerEvent;
import io.skyfallsdk.player.Player;

import java.util.List;

public class PlayerTabCompleteEvent extends PlayerEvent {

    private final CoreCommand command;
    private final List<String> result;

    public PlayerTabCompleteEvent(Player who, CoreCommand command, List<String> result) {
        super(who);

        this.command = command;
        this.result = result;
    }

    public CoreCommand getCommand() {
        return this.command;
    }

    public List<String> getResult() {
        return this.result;
    }
}
