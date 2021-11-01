package io.skyfallsdk.inventory.type.entity;

import io.skyfallsdk.item.Item;
import io.skyfallsdk.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PlayerInventory extends EntityInventory {

    @Override
    @NotNull Player getEntity();
}
