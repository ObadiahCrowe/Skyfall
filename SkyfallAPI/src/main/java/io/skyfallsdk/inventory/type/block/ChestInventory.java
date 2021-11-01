package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.BlockInventory;
import org.jetbrains.annotations.NotNull;

public interface ChestInventory extends BlockInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.CHEST_SINGULAR;
    }

    @Override
    default int getSlots() {
        return 27;
    }
}
