package io.skyfallsdk.inventory;

import io.skyfallsdk.inventory.type.entity.PlayerInventory;
import io.skyfallsdk.player.SkyfallPlayer;
import org.jetbrains.annotations.NotNull;

public class SkyfallPlayerInventory extends SkyfallEntityInventory implements PlayerInventory {

    public SkyfallPlayerInventory(@NotNull SkyfallPlayer player) {
        super(player);
    }

    @Override
    public @NotNull SkyfallPlayer getEntity() {
        return (SkyfallPlayer) this.entity;
    }
}
