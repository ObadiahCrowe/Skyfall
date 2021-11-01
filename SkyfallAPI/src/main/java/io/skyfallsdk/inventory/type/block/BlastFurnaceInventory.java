package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

public interface BlastFurnaceInventory extends FurnaceInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.BLAST_FURNACE;
    }
}
