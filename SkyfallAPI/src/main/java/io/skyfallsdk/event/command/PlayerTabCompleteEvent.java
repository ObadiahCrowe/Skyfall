package io.skyfallsdk.event.command;

import io.skyfallsdk.command.Command;
import io.skyfallsdk.event.player.PlayerEvent;
import io.skyfallsdk.player.Player;

import java.util.List;

public class PlayerTabCompleteEvent extends PlayerEvent {

    private final Command command;
    private final List<String> result;

    public PlayerTabCompleteEvent(Player who, Command command, List<String> result) {
        super(who);

        this.command = command;
        this.result = result;
    }

    public Command getCommand() {
        return this.command;
    }

    public List<String> getResult() {
        return this.result;
    }
}
