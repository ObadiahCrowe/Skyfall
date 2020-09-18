package io.skyfallsdk.event.server;

import io.skyfallsdk.command.CoreCommand;
import io.skyfallsdk.player.Player;

import java.util.List;

public class PlayerCommandTabCompleteEvent extends CommandEvent {

    private final Player player;
    private final List<String> result;

    public PlayerCommandTabCompleteEvent(Player player, CoreCommand command, List<String> result) {
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
