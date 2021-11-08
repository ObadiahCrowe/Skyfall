package io.skyfallsdk.enchantment;

import com.google.common.collect.Maps;
import io.skyfallsdk.expansion.Expansion;
import it.unimi.dsi.fastutil.ints.Int2ReferenceArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class SkyfallEnchantmentRegistry implements EnchantmentRegistry {

    private final Int2ReferenceMap<Enchantment> enchantmentsById;
    private final Map<Class<? extends Enchantment>, Enchantment> classToEnchantment;

    private final Int2ReferenceMap<Class<?>> enchantmentHandlers;

    public SkyfallEnchantmentRegistry() {
        this.enchantmentsById = new Int2ReferenceArrayMap<>();
        this.classToEnchantment = Maps.newHashMap();

        this.enchantmentHandlers = new Int2ReferenceArrayMap<>();
    }

    @Override
    public boolean registerEnchantment(@NotNull Expansion expansion, @NotNull Enchantment enchantment, @NotNull Class<?> handler) {
        return false;
    }

    @Override
    public boolean deregisterEnchantment(@NotNull Enchantment enchantment) {
        return false;
    }

    @Override
    public @Nullable Enchantment getEnchantment(int protocolId) {
        return this.enchantmentsById.getOrDefault(protocolId, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Enchantment> @Nullable T getEnchantment(@NotNull Class<T> enchantmentClass) {
        return (T) this.classToEnchantment.getOrDefault(enchantmentClass, null);
    }

    @Override
    public @NotNull Collection<@NotNull Enchantment> getRegisteredEnchantments() {
        return Collections.unmodifiableCollection(this.enchantmentsById.values());
    }
}
