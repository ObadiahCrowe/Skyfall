package io.skyfallsdk.item;

import com.google.common.collect.Maps;
import io.skyfallsdk.Server;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.enchantment.DefaultEnchantment;
import io.skyfallsdk.enchantment.Enchantment;
import io.skyfallsdk.item.attribute.ItemAttribute;
import io.skyfallsdk.item.meta.ItemMetadata;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;
import io.skyfallsdk.nbt.tag.type.TagByte;
import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.nbt.tag.type.TagList;
import io.skyfallsdk.substance.Substance;
import it.unimi.dsi.fastutil.ints.Int2ShortArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.objects.Reference2ShortArrayMap;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Item {

    private final Substance substance;

    private int amount;
    private int damage;

    private final Int2ShortMap enchantments;
    private final ItemMetadata metadata;

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

    public Substance getSubstance() {
        return this.substance;
    }

    public boolean hasEnchantment(@NotNull Enchantment enchantment) {
        return this.enchantments.containsKey(enchantment.getProtocolId());
    }

    public int getEnchantmentLevel(@NotNull Enchantment enchantment) {
        return this.enchantments.getOrDefault(enchantment.getProtocolId(), (short) -1);
    }

    public @NotNull Map<@NotNull Enchantment, @NotNull Integer> getEnchantments() {
        Map<Enchantment, Integer> enchantments = Maps.newHashMap();

        for (Int2ShortMap.Entry entry : this.enchantments.int2ShortEntrySet()) {
            enchantments.put(Server.get().getEnchantmentRegistry().getEnchantment(entry.getIntKey()), (int) entry.getShortValue());
        }

        return Collections.unmodifiableMap(enchantments);
    }

    @SuppressWarnings("unchecked")
    public <T extends ItemMetadata> T getMetadata() {
        return (T) this.metadata;
    }

    @NotThreadSafe
    public static class Builder implements Cloneable {

        protected final Item item;

        public Builder(@NotNull Substance substance) {
            this.item = new Item(substance);
        }

        public Builder setAmount(int amount) {
            this.item.amount = amount;

            return this;
        }

        public Builder setDamage(int damage) {
            this.item.damage = damage;

            return this;
        }

        public Builder addEnchantment(@NotNull Enchantment enchantment, int level) {
            this.item.enchantments.put(enchantment.getProtocolId(), (short) level);

            return this;
        }

        public <V, T extends ItemAttribute<V, T>> Builder setAttribute(@NotNull ItemAttribute<@NotNull V, @NotNull T> attribute, @NotNull V value) {
            this.item.metadata.setAttribute(attribute, value);

            return this;
        }

        public Builder setDisplayName(@NotNull ChatComponent displayName) {
            this.item.metadata.setDisplayName(displayName);

            return this;
        }

        public Builder setLore(@NotNull List<@NotNull ChatComponent> lines) {
            this.item.metadata.setLore(lines);

            return this;
        }

        public Builder addLoreLines(@NotNull List<@NotNull ChatComponent> lines) {
            this.item.metadata.addLoreLines(lines);

            return this;
        }

        public <T> Builder addNBTTag(@NotNull String name, T value) {
            NBTTag<T> tag = NBTTagType.fromRawType(value.getClass()).newInstance(name, value);

            return this;
        }

        public Item build() {
            return this.item;
        }

        @Override
        public Builder clone() {
            try {
                return (Builder) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
