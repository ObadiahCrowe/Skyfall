package io.skyfallsdk.enchantment;

import io.skyfallsdk.item.Item;
import org.jetbrains.annotations.NotNull;

public interface Enchantment {

    int getProtocolId();

    @NotNull
    String getMinecraftId();

    @NotNull
    String getName();

    @NotNull
    EnchantmentTarget @NotNull[] getTargets();

    boolean hasEnchantment(@NotNull Item item);
}
