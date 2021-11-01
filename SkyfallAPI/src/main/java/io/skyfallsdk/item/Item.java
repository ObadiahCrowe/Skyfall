package io.skyfallsdk.item;

import io.skyfallsdk.enchantment.Enchantment;
import io.skyfallsdk.substance.Substance;
import org.jetbrains.annotations.NotNull;

public class Item {

    private final Substance substance;

    private int amount;

    public Item(Substance substance) {
        this(substance, 1);
    }

    public Item(Substance substance, int amount) {
        this.substance = substance;
        this.amount = amount;
    }

    public Substance getSubstance() {
        return this.substance;
    }

    public boolean hasEnchantment(@NotNull Enchantment enchantment) {
        return false;
    }

    public int getEnchantmentLevel(@NotNull Enchantment enchantment) {
        return 0;
    }

    public static class Builder implements Cloneable {

        private final Substance substance;

        public Builder(Substance substance) {
            this.substance = substance;
        }

        public Builder setAmount(int amount) {
            return this;
        }

        public Item build() {
            return new Item(substance, build().amount);
        }
    }
}
