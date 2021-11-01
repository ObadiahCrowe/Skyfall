package io.skyfallsdk.inventory.type.entity;

import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.inventory.Inventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EntityInventory extends Inventory {

    @NotNull Entity getEntity();

    @Nullable Item getHelmet();

    void setHelmet(@Nullable Item item);

    @Nullable Item getChestplate();

    void setChestplate(@Nullable Item item);

    @Nullable Item getLeggings();

    void setLeggings(@Nullable Item item);

    @Nullable Item getBoots();

    void setBoots(@Nullable Item item);
}
