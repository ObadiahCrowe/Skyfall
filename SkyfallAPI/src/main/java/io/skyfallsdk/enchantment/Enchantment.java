package io.skyfallsdk.enchantment;

import io.skyfallsdk.item.Item;

public interface Enchantment {

    int getProtocolId();

    String getMinecraftId();

    String getName();

    EnchantmentTarget[] getTargets();

    boolean hasEnchantment(Item item);
}
