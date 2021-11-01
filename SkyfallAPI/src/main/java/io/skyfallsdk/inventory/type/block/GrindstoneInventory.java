package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.BlockInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface GrindstoneInventory extends BlockInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.GRINDSTONE;
    }

    @Override
    default int getSlots() {
        return 3;
    }

    @Nullable
    default Item getFirst() {
        return this.getItem(0);
    }

    default void setFirst(@Nullable Item item) {
        this.setItem(0, item);
    }

    @Nullable
    default Item getSecond() {
        return this.getItem(1);
    }

    default void setSecond(@Nullable Item item) {
        this.setItem(1, item);
    }

    @Nullable
    default Item getResult() {
        return this.getItem(2);
    }

    default void setResult(@Nullable Item item) {
        this.setItem(2, item);
    }
}
