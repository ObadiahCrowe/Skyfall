package io.skyfallsdk.item;

import io.skyfallsdk.enchantment.Enchantment;
import io.skyfallsdk.substance.Substance;
import it.unimi.dsi.fastutil.ints.Int2ShortArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.objects.Reference2ShortArrayMap;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Map;

public class Item {

    private final Substance substance;

    private int amount;
    private final Int2ShortMap enchantments;

    public Item(Substance substance) {
        this(substance, 1);
    }

    public Item(Substance substance, int amount) {
        this.substance = substance;
        this.amount = amount;

        this.enchantments = new Int2ShortArrayMap();
    }

    private Item(Builder builder) {
        this.substance = builder.substance;

        this.amount = builder.amount;
        this.enchantments = new Int2ShortArrayMap();
    }

    public Substance getSubstance() {
        return this.substance;
    }

    public boolean hasEnchantment(@NotNull Enchantment enchantment) {
        return this.enchantments.containsKey(enchantment.getProtocolId());
    }

    public int getEnchantmentLevel(@NotNull Enchantment enchantment) {
        return this.enchantments.getOrDefault(enchantment.getProtocolId(), (short) -1);
    }

    @NotThreadSafe
    public static class Builder implements Cloneable {

        private final Substance substance;

        private int amount;

        public Builder(Substance substance) {
            this.substance = substance;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;

            return this;
        }

        public Item build() {
            return new Item(this.substance, this.amount);
        }
    }
}
