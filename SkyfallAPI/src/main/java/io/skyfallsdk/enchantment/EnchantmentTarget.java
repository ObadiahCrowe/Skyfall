package io.skyfallsdk.enchantment;

import com.google.common.collect.Sets;
import io.skyfallsdk.item.Item;
import io.skyfallsdk.substance.Substance;

import java.util.Arrays;
import java.util.Set;

public enum EnchantmentTarget {

    HELMET(
      Substance.LEATHER_HELMET, Substance.CHAINMAIL_HELMET, Substance.IRON_HELMET, Substance.GOLDEN_HELMET,
      Substance.DIAMOND_HELMET, Substance.NETHERITE_HELMET
    ),

    CHESTPLATE(
      Substance.LEATHER_CHESTPLATE, Substance.CHAINMAIL_CHESTPLATE, Substance.IRON_CHESTPLATE, Substance.GOLDEN_CHESTPLATE,
      Substance.DIAMOND_CHESTPLATE, Substance.NETHERITE_CHESTPLATE
    ),

    LEGGINGS(
      Substance.LEATHER_LEGGINGS, Substance.CHAINMAIL_LEGGINGS, Substance.IRON_LEGGINGS, Substance.GOLDEN_LEGGINGS,
      Substance.DIAMOND_LEGGINGS, Substance.NETHERITE_LEGGINGS
    ),

    BOOTS(
      Substance.LEATHER_BOOTS, Substance.CHAINMAIL_BOOTS, Substance.IRON_BOOTS, Substance.GOLDEN_BOOTS,
      Substance.DIAMOND_BOOTS, Substance.NETHERITE_BOOTS
    ),

    SHIELD(
      Substance.SHIELD
    ),

    ARMOUR(
      EnchantmentTarget.HELMET, EnchantmentTarget.CHESTPLATE, EnchantmentTarget.LEGGINGS, EnchantmentTarget.BOOTS
    ),

    BOW(
      Substance.BOW
    ),

    CROSSBOW(
      Substance.CROSSBOW
    ),

    TRIDENT(
      Substance.TRIDENT
    ),

    SWORD(
      Substance.WOODEN_SWORD, Substance.STONE_SWORD, Substance.IRON_SWORD, Substance.GOLDEN_SWORD,
      Substance.DIAMOND_SWORD, Substance.NETHERITE_SWORD
    ),

    HOE(
      Substance.WOODEN_HOE, Substance.STONE_HOE, Substance.IRON_HOE, Substance.GOLDEN_HOE,
      Substance.DIAMOND_HOE, Substance.NETHERITE_HOE
    ),

    SHOVEL(
      Substance.WOODEN_SHOVEL, Substance.STONE_SHOVEL, Substance.IRON_SHOVEL, Substance.GOLDEN_SHOVEL,
      Substance.DIAMOND_SHOVEL, Substance.NETHERITE_SHOVEL
    ),

    PICKAXE(
      Substance.WOODEN_PICKAXE, Substance.STONE_PICKAXE, Substance.IRON_PICKAXE, Substance.GOLDEN_PICKAXE,
      Substance.DIAMOND_PICKAXE, Substance.NETHERITE_PICKAXE
    ),

    AXE(
      Substance.WOODEN_AXE, Substance.STONE_AXE, Substance.IRON_AXE, Substance.GOLDEN_AXE,
      Substance.DIAMOND_AXE, Substance.NETHERITE_AXE
    ),

    FISHING_ROD(
      Substance.FISHING_ROD
    ),

    TOOL(
      EnchantmentTarget.HOE, EnchantmentTarget.SHOVEL, EnchantmentTarget.PICKAXE, EnchantmentTarget.AXE,
      EnchantmentTarget.FISHING_ROD
    ),

    WEAPON(
      EnchantmentTarget.BOW, EnchantmentTarget.CROSSBOW, EnchantmentTarget.TRIDENT, EnchantmentTarget.SWORD,
      EnchantmentTarget.AXE
    );

    private final Substance[] validSubstances;

    EnchantmentTarget(Substance... validSubstances) {
        this.validSubstances = validSubstances;
    }

    EnchantmentTarget(EnchantmentTarget... targets) {
        Set<Substance> substances = Sets.newHashSet();

        for (EnchantmentTarget target : targets) {
            substances.addAll(Arrays.asList(target.validSubstances));
        }

        this.validSubstances = substances.toArray(new Substance[0]);
    }

    public Substance[] getValidSubstances() {
        return this.validSubstances;
    }

    public boolean canEnchant(Item item) {
        for (Substance substance : this.validSubstances) {
            if (item.getSubstance() == substance) {
                return true;
            }
        }

        return false;
    }
}
