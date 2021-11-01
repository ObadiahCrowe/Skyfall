package io.skyfallsdk.bossbar;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.util.Validator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public class SkyfallBossBar implements BossBar {

    @Override
    public void sendToPlayer(@NotNull Player player) {
        Validator.notNull(player);
    }

    @Override
    public void removeFromPlayer(@NotNull Player player) {
        Validator.notNull(player);
    }

    @Override
    public void updateTitle(@NotNull ChatComponent title) {
        Validator.notNull(title);
    }

    @Override
    public void updateHealth(float health) {

    }

    @Override
    public void updateStyle(@NotNull BarDivision division) {
        Validator.notNull(division);
    }

    @Override
    public void updateFlags(@NotNull BarFlag @NotNull ... flags) {
        Validator.notNull(flags);
    }

    @Override
    public void remove() {

    }

    @Override
    public @NotNull UUID getUuid() {
        return null;
    }

    @Override
    public @Nullable ChatComponent getTitle() {
        return null;
    }

    @Override
    public float getHealth() {
        return 0;
    }

    @Override
    public @NotNull BarColour getColour() {
        return null;
    }

    @Override
    public @NotNull BarDivision getDivision() {
        return null;
    }

    @Override
    public boolean willDarkenSky() {
        return false;
    }

    @Override
    public boolean isDragonBar() {
        return false;
    }

    @Override
    public @NotNull Set<? extends @NotNull Player> getRecipients() {
        return null;
    }
}
