package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

public interface ShulkerInventory extends ChestInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.SHULKER_BOX;
    }
}
