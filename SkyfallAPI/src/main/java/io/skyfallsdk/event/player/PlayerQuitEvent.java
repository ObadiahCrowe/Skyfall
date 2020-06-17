package io.skyfallsdk.event.player;

import io.skyfallsdk.player.Player;

public class PlayerQuitEvent extends PlayerEvent {

    private String quitMessage;

    public PlayerQuitEvent(Player player, String quitMessage) {
        super(player);

        this.quitMessage = quitMessage;
    }

    public String getQuitMessage() {
        return this.quitMessage;
    }

    public void setQuitMessage(String quitMessage) {
        this.quitMessage = quitMessage;
    }
}
