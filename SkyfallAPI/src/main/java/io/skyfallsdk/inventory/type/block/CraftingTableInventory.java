package io.skyfallsdk.inventory.type.block;

import io.skyfallsdk.inventory.InventoryType;
import io.skyfallsdk.inventory.type.BlockInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CraftingTableInventory extends BlockInventory {

    @Override
    default @NotNull InventoryType getType() {
        return InventoryType.CRAFTING_TABLE;
    }

    @Override
    default int getSlots() {
        return 10;
    }

    default @NotNull Item @Nullable [] getInput() {
        Item[] items = new Item[9];

        for (int i = 0; i < 9; i++) {
            items[i] = this.getItem(i);
        }

        return items;
    }

    void setInput(@NotNull Item @Nullable[] input);

    @Nullable Item getResult();

    void setResult(@Nullable Item item);
}
