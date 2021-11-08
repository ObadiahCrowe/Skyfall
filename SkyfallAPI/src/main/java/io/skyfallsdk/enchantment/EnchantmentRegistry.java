package io.skyfallsdk.enchantment;

import io.skyfallsdk.expansion.Expansion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface EnchantmentRegistry {

    boolean registerEnchantment(@NotNull Expansion expansion, @NotNull Enchantment enchantment, @NotNull Class<?> handler);

    boolean deregisterEnchantment(@NotNull Enchantment enchantment);

    @Nullable Enchantment getEnchantment(int protocolId);

    @Nullable <T extends Enchantment> T getEnchantment(@NotNull Class<T> enchantmentClass);

    @NotNull Collection<@NotNull Enchantment> getRegisteredEnchantments();
}
