package io.skyfallsdk.inventory;

public enum InventoryType {

    CHEST_SINGULAR,
    CHEST_DOUBLE,
    ANVIL,
    BEACON,
    BLAST_FURNACE,
    BREWING_STAND,
    CRAFTING_TABLE,
    ENCHANTMENT_TABLE,
    FURNACE,
    GRINDSTONE,
    HOPPER,
    LECTERN,
    LOOM,
    VILLAGER,
    SHULKER_BOX,
    SMOKER,
    CARTOGRAPHY_TABLE,
    STONE_CUTTER;

    private final String internalName;
    private final Class<? extends Inventory> inventoryClass;

    InventoryType(String internalName, Class<? extends Inventory> inventoryClass) {
        this.internalName = internalName;
        this.inventoryClass = inventoryClass;
    }

    public String getInternalName() {
        return this.internalName;
    }

    public Class<? extends Inventory> getInventoryClass() {
        return this.inventoryClass;
    }
}
