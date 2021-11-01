package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.BlockInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StonecutterInventory extends BlockInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.STONE_CUTTER;
    }

    @Override
    default int getSlots() {
        return 3;
    }

    default @Nullable Item getInput() {
        return this.getItem(0);
    }

    default void setInput(@Nullable Item item) {
        this.setItem(0, item);
    }

    default @Nullable Item getResult() {
        return this.getItem(1);
    }

    default void setResult(@Nullable Item item) {
        this.setItem(1, item);
    }
}
