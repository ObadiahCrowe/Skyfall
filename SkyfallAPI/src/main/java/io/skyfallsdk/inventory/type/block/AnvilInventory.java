package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.BlockInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AnvilInventory extends BlockInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.ANVIL;
    }

    @Override
    default int getSlots() {
        return 3;
    }

    @Nullable String getRenameText();

    void setRenameText(@NotNull String renameText);

    int getRepairCost();

    void setRepairCost(int repairCost);

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

    default @Nullable Item getResult() {
        return this.getItem(2);
    }

    default void setResult(@Nullable Item item) {
        this.setItem(2, item);
    }
}
