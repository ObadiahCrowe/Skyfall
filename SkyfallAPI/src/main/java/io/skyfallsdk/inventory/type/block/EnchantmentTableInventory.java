package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.BlockInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EnchantmentTableInventory extends BlockInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.ENCHANTMENT_TABLE;
    }

    @Override
    default int getSlots() {
        return 2;
    }

    default @Nullable Item getTarget() {
        return this.getItem(0);
    }

    default void setTarget(@Nullable Item item) {
        this.setItem(0, item);
    }

    default @Nullable Item getFuel() {
        return this.getItem(1);
    }

    default void setFuel(@Nullable Item item) {
        this.setItem(1, item);
    }
}
