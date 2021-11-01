package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.world.Position;
import org.jetbrains.annotations.NotNull;

public interface DoubleChestInventory extends ChestInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.CHEST_DOUBLE;
    }

    @Override
    default int getSlots() {
        return 54;
    }

    @NotNull Position getSecondaryPosition();
}
