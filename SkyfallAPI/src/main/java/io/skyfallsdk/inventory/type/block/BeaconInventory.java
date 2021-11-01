package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.BlockInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BeaconInventory extends BlockInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.BEACON;
    }

    @Override
    default int getSlots() {
        return 1;
    }

    default @Nullable Item getPaymentItem() {
        return this.getItem(0);
    }

    default void setPaymentItem(@Nullable Item item) {
        this.setItem(0, item);
    }
}
