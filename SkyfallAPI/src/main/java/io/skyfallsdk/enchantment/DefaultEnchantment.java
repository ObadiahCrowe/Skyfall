package io.skyfallsdk.enchantment;

import com.google.common.collect.Maps;
import io.skyfallsdk.item.Item;
import io.skyfallsdk.substance.Substance;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public enum DefaultEnchantment implements Enchantment {

    PROTECTION(0, "minecraft:protection", "Protection", EnchantmentTarget.ARMOUR),
    FIRE_PROTECTION(1, "minecraft:fire_protection", "Fire Protection", EnchantmentTarget.ARMOUR),
    FEATHER_FALLING(2, "minecraft:feather_falling", "Feather Falling", EnchantmentTarget.BOOTS),
    BLAST_PROTECTION(3, "minecraft:blast_protection", "Blast Protection", EnchantmentTarget.ARMOUR),
    PROJECTILE_PROTECTION(4, "minecraft:projectile_protection", "Projectile Protection", EnchantmentTarget.ARMOUR),
    RESPIRATION(5, "minecraft:respiration", "Respiration"),
    AQUA_AFFINITY(6, "minecraft:aqua_affinity", "Aqua Affinity"),
    THORNS(7, "minecraft:thorns", "Thorns"),
    DEPTH_STRIDER(8, "minecraft:depth_strider", "Depth Strider"),
    FROST_WALKER(9, "minecraft:frost_walker", "Frost Walker"),
    BINDING_CURSE(10, "minecraft:binding_curse", "Curse of Binding", EnchantmentTarget.values()),
    SHARPNESS(11, "minecraft:sharpness", "Sharpness", EnchantmentTarget.AXE, EnchantmentTarget.SWORD),
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
    INFINITY(25, "minecraft:infinity", "Infinity", EnchantmentTarget.BOW, EnchantmentTarget.CROSSBOW),
    LUCK_OF_THE_SEA(26, "minecraft:luck_of_the_sea", "Luck of the Sea", EnchantmentTarget.FISHING_ROD),
    LURE(27, "minecraft:lure", "Lure", EnchantmentTarget.FISHING_ROD),
    LOYALTY(28, "minecraft:loyalty", "Loyalty", EnchantmentTarget.TRIDENT),
    IMPALING(29, "minecraft:impaling", "Impaling", EnchantmentTarget.TRIDENT),
    RIPTIDE(30, "minecraft:riptide", "Riptide", EnchantmentTarget.TRIDENT),
    CHANNELING(31, "minecraft:channeling", "Channeling", EnchantmentTarget.TRIDENT),
    MENDING(32, "minecraft:mending", "Mending", EnchantmentTarget.TOOL, EnchantmentTarget.WEAPON),
    VANISHING_CURSE(33, "minecraft:vanishing_curse", "Curse of Vanishing", EnchantmentTarget.values());

    static final Int2IntMap PROTOCOL_ID_TO_ENCHANTMENT = new Int2IntArrayMap();
    private static final Map<String, DefaultEnchantment> NAMESPACED_ID_TO_ENCHANTMENT = Maps.newHashMap();

    static {
        for (DefaultEnchantment enchantment : DefaultEnchantment.values()) {
            NAMESPACED_ID_TO_ENCHANTMENT.put(enchantment.getMinecraftId(), enchantment);
        }
    }

    private final int protocolId;
    private final String minecraftId;
    private final String name;
    private final EnchantmentTarget[] targets;

    DefaultEnchantment(int protocolId, String minecraftId, String name, EnchantmentTarget... targets) {
        this.protocolId = protocolId;
        this.minecraftId = minecraftId;
        this.name = name;
        this.targets = targets;
    }

    @Override
    public int getProtocolId() {
        return this.protocolId;
    }

    @Override
    public @NotNull String getMinecraftId() {
        return this.minecraftId;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull EnchantmentTarget @NotNull[] getTargets() {
        return this.targets;
    }

    @Override
    public boolean hasEnchantment(@NotNull Item item) {
        return item.hasEnchantment(this);
    }

    public static @Nullable Enchantment getById(int protocolId) {
        int index = PROTOCOL_ID_TO_ENCHANTMENT.getOrDefault(protocolId, -1);
        if (index == -1) {
            return null;
        }

        return DefaultEnchantment.values()[protocolId];
    }

    public static @Nullable DefaultEnchantment getByNamespacedId(@NotNull String namespacedId) {
        return NAMESPACED_ID_TO_ENCHANTMENT.getOrDefault(namespacedId, null);
    }
}
