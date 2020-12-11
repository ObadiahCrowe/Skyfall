package io.skyfallsdk.enchantment;

import io.skyfallsdk.item.Item;

public enum DefaultEnchantment implements Enchantment {

    PROTECTION(0, "minecraft:protection", "Protection"),
    FIRE_PROTECTION(1, "minecraft:fire_protection", "Fire Protection"),
    FEATHER_FALLING(2, "minecraft:feather_falling", "Feather Falling"),
    BLAST_PROTECTION(3, "minecraft:blast_protection", "Blast Protection"),
    PROJECTILE_PROTECTION(4, "minecraft:projectile_protection", "Projectile Protection"),
    RESPIRATION(5, "minecraft:respiration", "Respiration"),
    AQUA_AFFINITY(6, "minecraft:aqua_affinity", "Aqua Affinity"),
    THORNS(7, "minecraft:thorns", "Thorns"),
    DEPTH_STRIDER(8, "minecraft:depth_strider", "Depth Strider"),
    FROST_WALKER(9, "minecraft:frost_walker", "Frost Walker"),
    BINDING_CURSE(10, "minecraft:binding_curse", "Curse of Binding"),
    SHARPNESS(11, "minecraft:sharpness", "Sharpness"),
    SMITE(12, "minecraft:smite", "Smite"),
    BANE_OF_ARTHROPODS(13, "minecraft:bane_of_arthropods", "Bane of Arthropods"),
    KNOCKBACK(14, "minecraft:knockback", "Knockback"),
    FIRE_ASPECT(15, "minecraft:fire_aspect", "Fire Aspect"),
    LOOTING(16, "minecraft:looting", "Looting"),
    SWEEPING_EDGE(17, "minecraft:sweeping_edge", "Sweeping Edge"),
    EFFICIENCY(18, "minecraft:efficiency", "Efficiency"),
    SILK_TOUCH(19, "minecraft:silk_touch", "Silk Touch"),
    UNBREAKING(20, "minecraft:unbreaking", "Unbreaking"),
    FORTUNE(21, "minecraft:fortune", "Fortune"),
    POWER(22, "minecraft:power", "Power"),
    PUNCH(23, "minecraft:punch", "Punch"),
    FLAME(24, "minecraft:flame", "Flame"),
    INFINITY(25, "minecraft:infinity", "Infinity"),
    LUCK_OF_THE_SEA(26, "minecraft:luck_of_the_sea", "Luck of the Sea"),
    LURE(27, "minecraft:lure", "Lure"),
    LOYALTY(28, "minecraft:loyalty", "Loyalty"),
    IMPALING(29, "minecraft:impaling", "Impaling"),
    RIPTIDE(30, "minecraft:riptide", "Riptide"),
    CHANNELING(31, "minecraft:channeling", "Channeling"),
    MENDING(32, "minecraft:mending", "Mending"),
    VANISHING_CURSE(33, "minecraft:vanishing_curse", "Curse of Vanishing");

    private final int protocolId;
    private final String minecraftId;
    private final String name;

    DefaultEnchantment(int protocolId, String minecraftId, String name) {
        this.protocolId = protocolId;
        this.minecraftId = minecraftId;
        this.name = name;
    }

    @Override
    public int getProtocolId() {
        return this.protocolId;
    }

    @Override
    public String getMinecraftId() {
        return this.minecraftId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public EnchantmentTarget[] getTargets() {
        return new EnchantmentTarget[0];
    }

    @Override
    public boolean hasEnchantment(Item item) {
        return false;
    }
}
