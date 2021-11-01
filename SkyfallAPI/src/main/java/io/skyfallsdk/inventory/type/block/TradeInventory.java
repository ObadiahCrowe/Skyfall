package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.entity.EntityInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TradeInventory extends EntityInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.VILLAGER;
    }

    @Override
    default int getSlots() {
        return 3;
    }

    default @Nullable Item getFirstInput() {
        return this.getItem(0);
    }

    default void setFirstInput(@Nullable Item item) {
        this.setItem(0, item);
    }

    default @Nullable Item getSecondInput() {
        return this.getItem(1);
    }

    default void setSecondInput(@Nullable Item item) {
        this.setItem(1, item);
    }

    default @Nullable Item getResult() {
        return this.getItem(2);
    }

    default void setResult(@Nullable Item item) {
        this.setItem(2, item);
    }
}
