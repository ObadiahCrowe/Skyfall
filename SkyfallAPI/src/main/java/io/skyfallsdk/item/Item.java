package io.skyfallsdk.item;

import io.skyfallsdk.Server;
import io.skyfallsdk.enchantment.DefaultEnchantment;
import io.skyfallsdk.enchantment.Enchantment;
import io.skyfallsdk.item.meta.ItemMetadata;
import io.skyfallsdk.nbt.tag.type.TagByte;
import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.nbt.tag.type.TagList;
import io.skyfallsdk.substance.Substance;
import it.unimi.dsi.fastutil.ints.Int2ShortArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.objects.Reference2ShortArrayMap;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;
import java.util.Map;

public class Item {

    private final Substance substance;

    private int amount;
    private int damage;

    private final Int2ShortMap enchantments;
    private final ItemMetadata<?> metadata;

    public Item(Substance substance) {
        this(substance, 1);
    }

    public Item(Substance substance, int amount) {
        this.substance = substance;
        this.amount = amount;
        this.damage = 0;

        this.enchantments = new Int2ShortArrayMap();
        this.metadata = Server.get().getItemFactory().getNewMetadata(this);
    }

    @SuppressWarnings("unchecked")
    public Item(@NotNull TagCompound compound) {
        this.substance = Substance.getByNamespacedId((String) compound.get("id").getValue());
        this.amount = (int) ((TagByte) compound.get("Count")).getValue();
        this.damage = compound.containsKey("Damage") ? (int) compound.get("Damage").getValue() : 0;

        this.enchantments = new Int2ShortArrayMap();

        if (compound.containsKey("tag")) {
            TagCompound tag = (TagCompound) compound.get("tag");

            if (tag.containsKey("Enchantments")) {
                List<TagCompound> enchantments = ((TagList<TagCompound>) tag.get("Enchantments")).getValue();

                for (TagCompound enchantment : enchantments) {
                    String namespacedId = (String) enchantment.get("id").getValue();
                    short level = (short) enchantment.get("lvl").getValue();

                    DefaultEnchantment enchant = DefaultEnchantment.getByNamespacedId(namespacedId);
                    if (enchant == null) {
                        continue;
                    }

                    this.enchantments.put(enchant.getProtocolId(), level);
                }
            }
        }

        this.metadata = Server.get().getItemFactory().getNewMetadata(this);
    }

    private Item(Builder builder) {
        this.substance = builder.substance;

        this.amount = builder.amount;
        this.enchantments = new Int2ShortArrayMap();

        this.metadata = Server.get().getItemFactory().getNewMetadata(this);
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
