package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.BlockInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface HopperInventory extends BlockInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.HOPPER;
    }

    @Override
    default int getSlots() {
        return 5;
    }

    default @Nullable Item getFirst() {
        return this.getItem(0);
    }

    default void setFirst(@Nullable Item item) {
        this.setItem(0, item);
    }

    default @Nullable Item getSecond() {
        return this.getItem(1);
    }

    default void setSecond(@Nullable Item item) {
        this.setItem(1, item);
    }

    default @Nullable Item getThird() {
        return this.getItem(2);
    }

    default void setThird(@Nullable Item item) {
        this.setItem(2, item);
    }

    default @Nullable Item getFourth() {
        return this.getItem(3);
    }

    default void setFourth(@Nullable Item item) {
        this.setItem(3, item);
    }

    default @Nullable Item getFifth() {
        return this.getItem(4);
    }

    default void setFifth(@Nullable Item item) {
        this.setItem(4, item);
    }
}
