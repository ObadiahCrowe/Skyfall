package io.skyfallsdk.inventory.type.entity;

import io.skyfallsdk.player.Player;
import org.jetbrains.annotations.NotNull;

public interface PlayerInventory extends EntityInventory {

    @Override
    @NotNull Player getEntity();
}
