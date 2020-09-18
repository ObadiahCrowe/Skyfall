package io.skyfallsdk.event.server;

import io.skyfallsdk.command.Command;
import io.skyfallsdk.player.Player;

import java.util.List;

public class CommandTabCompleteEvent extends CommandEvent {

    private final Player player;
    private final List<String> result;

    public CommandTabCompleteEvent(Player player, Command command, List<String> result) {
        super(command);

        this.player = player;
        this.result = result;
    }

    public Player getPlayer() {
        return this.player;
    }

    public List<String> getResult() {
        return this.result;
    }
}
