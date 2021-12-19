package io.skyfallsdk.item.meta;

import com.google.common.collect.Lists;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.item.attribute.ItemAttribute;
import io.skyfallsdk.substance.Substance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SkyfallItemMetadata implements ItemMetadata {

    private final Substance substance;

    private ChatComponent displayName;
    private final List<ChatComponent> lore;

    public SkyfallItemMetadata(@NotNull Substance substance) {
        this.substance = substance;


        this.lore = Lists.newArrayList();
    }

    @Override
    public @NotNull <V, T extends ItemAttribute<V, T>> Set<@NotNull ItemAttribute<@NotNull V, @NotNull T>> getAttributes() {
        return null;
    }

    @Override
    public <V, T extends ItemAttribute<V, T>> void setAttribute(@NotNull ItemAttribute<@NotNull V, @NotNull T> attribute, @NotNull V value) {

    }

    @Override
    public <V, T extends ItemAttribute<V, T>> void removeAttribute(@NotNull ItemAttribute<@NotNull V, @NotNull T> attribute) {

    }

    @Override
    public @NotNull <V, T extends ItemAttribute<V, T>> Optional<@Nullable T> getAttribute(@NotNull ItemAttribute<@NotNull V, @NotNull T> attribute) {
        return Optional.empty();
    }

    @Override
    public @NotNull ChatComponent getDisplayName() {
        return null;
    }

    @Override
    public void setDisplayName(@NotNull ChatComponent displayName) {

    }

    @Override
    public @NotNull List<@NotNull ChatComponent> getLore() {
        return null;
    }

    @Override
    public void setLore(@NotNull List<@NotNull ChatComponent> lines) {

    }

    @Override
    public void addLoreLines(@NotNull List<@NotNull ChatComponent> lines) {

    }

    @Override
    public void addLoreLines(@NotNull List<@NotNull ChatComponent> lines, int atIndex) {

    }

    @Override
    public void setCustomModelKey(int modelKey) {

    }

    @Override
    public int getCustomModelKey() {
        return 0;
    }

    @Override
    public @NotNull EnumSet<@NotNull ItemFlag> getItemFlags() {
        return null;
    }

    @Override
    public void addItemFlag(@NotNull ItemFlag flag) {

    }

    @Override
    public void removeItemFlag(@NotNull ItemFlag flag) {

    }

    @Override
    public void setUnbreakable(boolean unbreakable) {

    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }
}
