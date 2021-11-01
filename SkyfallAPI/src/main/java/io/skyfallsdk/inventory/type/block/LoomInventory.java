package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.BlockInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LoomInventory extends BlockInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.LOOM;
    }

    @Override
    default int getSlots() {
        return 4;
    }

    default @Nullable Item getBanner() {
        return this.getItem(0);
    }

    default void setBanner(@Nullable Item item) {
        this.setItem(0, item);
    }

    default @Nullable Item getDye() {
        return this.getItem(1);
    }

    default void setDye(@Nullable Item item) {
        this.setItem(1, item);
    }

    default @Nullable Item getPattern() {
        return this.getItem(2);
    }

    default void setPattern(@Nullable Item item) {
        this.setItem(2, item);
    }

    default @Nullable Item getResult() {
        return this.getItem(3);
    }

    default void setResult(@Nullable Item item) {
        this.setItem(3, item);
    }
}
