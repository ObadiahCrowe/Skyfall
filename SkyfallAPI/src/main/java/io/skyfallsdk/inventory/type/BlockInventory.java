package io.skyfallsdk.inventory.type;

import io.skyfallsdk.inventory.Inventory;
import io.skyfallsdk.world.Position;
import org.jetbrains.annotations.NotNull;

public interface BlockInventory extends Inventory {

    @NotNull Position getPosition();
}
