package io.skyfallsdk.event.player;

import io.skyfallsdk.player.Player;

public class PlayerJoinEvent extends PlayerEvent {

    private String joinMessage;

    /**
     *
     * @param player
     * @param joinMessage
     */
    public PlayerJoinEvent(Player player, String joinMessage) {
        super(player);

        this.joinMessage = joinMessage;
    }

    public String getJoinMessage() {
        return this.joinMessage;
    }

    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }
}
