package io.skyfallsdk.event.player;

import io.skyfallsdk.event.Event;
import io.skyfallsdk.player.Player;

public abstract class PlayerEvent implements Event {

    private final Player player;

    public PlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
