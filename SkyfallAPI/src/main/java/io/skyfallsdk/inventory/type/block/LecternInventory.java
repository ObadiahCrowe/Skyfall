package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.BlockInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LecternInventory extends BlockInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.LECTERN;
    }

    @Override
    default int getSlots() {
        return 1;
    }

    default @Nullable Item getBook() {
        return this.getItem(0);
    }

    default void setBook(@Nullable Item item) {
        this.setItem(0, item);
    }
}
