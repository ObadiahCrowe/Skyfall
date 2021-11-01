package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

public interface SmokerInventory extends FurnaceInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.SMOKER;
    }
}
