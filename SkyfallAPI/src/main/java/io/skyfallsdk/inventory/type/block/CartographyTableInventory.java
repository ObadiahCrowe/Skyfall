package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.BlockInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CartographyTableInventory extends BlockInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.CARTOGRAPHY_TABLE;
    }

    @Override
    default int getSlots() {
        return 3;
    }

    default @Nullable Item getMap() {
        return this.getItem(0);
    }

    default void setMap(@Nullable Item item) {
        this.setItem(0, item);
    }

    default @Nullable Item getPaper() {
        return this.getItem(1);
    }

    default void setPaper(@Nullable Item item) {
        this.setItem(1, item);
    }

    default @Nullable Item getOutput() {
        return this.getItem(2);
    }

    default void setOutput(@Nullable Item item) {
        this.setItem(2, item);
    }
}
