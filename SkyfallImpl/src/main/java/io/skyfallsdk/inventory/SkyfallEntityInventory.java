package io.skyfallsdk.inventory;

import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.entity.SkyfallEntity;
import io.skyfallsdk.inventory.type.entity.EntityInventory;
import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkyfallEntityInventory extends SkyfallInventory implements EntityInventory {

    protected final SkyfallEntity entity;

    public SkyfallEntityInventory(@NotNull SkyfallEntity entity) {
        super(InventoryType.CHEST_SINGULAR, "");

        this.entity = entity;
    }

    @Override
    public @NotNull SkyfallEntity getEntity() {
        return this.entity;
    }

    @Override
    public @Nullable Item getHelmet() {
        return null;
    }

    @Override
    public void setHelmet(@Nullable Item item) {

    }

    @Override
    public @Nullable Item getChestplate() {
        return null;
    }

    @Override
    public void setChestplate(@Nullable Item item) {

    }

    @Override
    public @Nullable Item getLeggings() {
        return null;
    }

    @Override
    public void setLeggings(@Nullable Item item) {

    }

    @Override
    public @Nullable Item getBoots() {
        return null;
    }

    @Override
    public void setBoots(@Nullable Item item) {

    }
}
