package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.BlockInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface FurnaceInventory extends BlockInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.FURNACE;
    }

    @Override
    default int getSlots() {
        return 3;
    }

    default @Nullable Item getIngredient() {
        return this.getItem(0);
    }

    default void setIngredient(@Nullable Item item) {
        this.setItem(0, item);
    }

    default @Nullable Item getFuel() {
        return this.getItem(1);
    }

    default void setFuel(@Nullable Item item) {
        this.setItem(1, item);
    }

    default @Nullable Item getOutput() {
        return this.getItem(2);
    }

    default void setOutput(@Nullable Item item) {
        this.setItem(2, item);
    }
}
