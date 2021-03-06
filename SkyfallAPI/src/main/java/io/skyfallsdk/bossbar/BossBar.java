package io.skyfallsdk.bossbar;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.player.Player;

import java.util.UUID;

public interface BossBar {

    /**
     * Sends this BossBar to a player.
     *
     * @param player Player to send the BossBar to.
     */
    void sendToPlayer(Player player);

    void removeFromPlayer(Player player);

    void updateTitle(ChatComponent title);

    void updateHealth(float health);

    void updateStyle(BarDivision division);

    void updateFlags(BarFlag... flags);

    void remove();

    UUID getUuid();

    ChatComponent getTitle();

    float getHealth();

    BarColour getColour();

    BarDivision getDivision();

    boolean willDarkenSky();

    boolean isDragonBar();
}
