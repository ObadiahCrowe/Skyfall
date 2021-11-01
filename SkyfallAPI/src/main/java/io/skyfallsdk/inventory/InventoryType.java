package io.skyfallsdk.inventory;

import io.skyfallsdk.inventory.type.block.*;
import org.jetbrains.annotations.NotNull;

public enum InventoryType {

    CHEST_SINGULAR("minecraft:generic_9x3", ChestInventory.class, 27),
    CHEST_DOUBLE("minecraft:generic_9x6", DoubleChestInventory.class, 54),
    ANVIL("minecraft:anvil", AnvilInventory.class, 3),
    BEACON("minecraft:beacon", BeaconInventory.class, 1),
    BLAST_FURNACE("minecraft:blast_furnace", BlastFurnaceInventory.class, 3),
    BREWING_STAND("minecraft:brewing_stand", BrewingStandInventory.class, 5),
    CRAFTING_TABLE("minecraft:crafting", CraftingTableInventory.class, 9),
    ENCHANTMENT_TABLE("minecraft:enchantment", EnchantmentTableInventory.class, 2),
    FURNACE("minecraft:furnace", FurnaceInventory.class, 3),
    GRINDSTONE("minecraft:grindstone", GrindstoneInventory.class, 3),
    HOPPER("minecraft:hopper", HopperInventory.class, 5),
    LECTERN("minecraft:lectern", LecternInventory.class, 0),
    LOOM("minecraft:loom", LoomInventory.class, 4),
    VILLAGER("minecraft:merchant", TradeInventory.class, 3),
    SHULKER_BOX("minecraft:shulker_box", ShulkerInventory.class, 27),
    SMOKER("minecraft:smoker", SmokerInventory.class, 3),
    CARTOGRAPHY_TABLE("minecraft:cartography", CartographyTableInventory.class, 3),
    STONE_CUTTER("minecraft:stonecutter", StonecutterInventory.class, 2);

    private final String internalName;
    private final Class<? extends Inventory> inventoryClass;
    private final int defaultSlots;

    InventoryType(@NotNull String internalName, @NotNull Class<? extends Inventory> inventoryClass, int defaultSlots) {
        this.internalName = internalName;
        this.inventoryClass = inventoryClass;
        this.defaultSlots = defaultSlots;
    }

    public @NotNull String getInternalName() {
        return this.internalName;
    }

    public @NotNull Class<? extends Inventory> getInventoryClass() {
        return this.inventoryClass;
    }

    public int getDefaultSlots() {
        return this.defaultSlots;
    }
}
