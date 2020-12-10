package io.skyfallsdk.item;

import io.skyfallsdk.substance.Substance;

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
