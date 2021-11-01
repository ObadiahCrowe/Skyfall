package io.skyfallsdk.bossbar;

import io.skyfallsdk.Server;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public interface BossBar {

    static @NotNull BossBar create() {
        return Server.get().createNewBossBar();
    }

    /**
     * Sends this BossBar to a player.
     *
     * @param player Player to send the BossBar to.
     */
    void sendToPlayer(@NotNull Player player);

    void removeFromPlayer(@NotNull Player player);

    void updateTitle(@NotNull ChatComponent title);

    void updateHealth(float health);

    void updateStyle(@NotNull BarDivision division);

    void updateFlags(@NotNull BarFlag @NotNull... flags);

    void remove();

    @NotNull
    UUID getUuid();

    @Nullable
    ChatComponent getTitle();

    float getHealth();

    @NotNull
    BarColour getColour();

    @NotNull
    BarDivision getDivision();

    boolean willDarkenSky();

    boolean isDragonBar();

    @NotNull
    Set<? extends @NotNull Player> getRecipients();
}
