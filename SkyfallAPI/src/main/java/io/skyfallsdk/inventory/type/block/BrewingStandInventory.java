package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.BlockInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BrewingStandInventory extends BlockInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.BREWING_STAND;
    }

    @Override
    default int getSlots() {
        return 5;
    }

    default @Nullable Item getFirstResult() {
        return this.getItem(0);
    }

    default void setFirstResult(@Nullable Item item) {
        this.setItem(0, item);
    }

    default @Nullable Item getSecondResult() {
        return this.getItem(1);
    }

    default void setSecondResult(@Nullable Item item) {
        this.setItem(1, item);
    }

    default @Nullable Item getThirdResult() {
        return this.getItem(2);
    }

    default void setThirdResult(@Nullable Item item) {
        this.setItem(2, item);
    }

    default @Nullable Item getIngredient() {
        return this.getItem(3);
    }

    default void setIngredient(@Nullable Item item) {
        this.setItem(3, item);
    }

    default @Nullable Item getFuel() {
        return this.getItem(4);
    }

    default void setFuel(@Nullable Item item)  {
        this.setItem(4, item);
    }
}
