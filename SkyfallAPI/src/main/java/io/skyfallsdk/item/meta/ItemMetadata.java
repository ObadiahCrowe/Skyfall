package io.skyfallsdk.item.meta;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.item.attribute.ItemAttribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemMetadata {

    <V, T extends ItemAttribute<V, T>> @NotNull Set<@NotNull ItemAttribute<@NotNull V, @NotNull T>> getAttributes();

    <V, T extends ItemAttribute<V, T>> void setAttribute(@NotNull ItemAttribute<@NotNull V, @NotNull T> attribute, @NotNull V value);

    <V, T extends ItemAttribute<V, T>> void removeAttribute(@NotNull ItemAttribute<@NotNull V, @NotNull T> attribute);

    <V, T extends ItemAttribute<V, T>> @NotNull Optional<@Nullable T> getAttribute(@NotNull ItemAttribute<@NotNull V, @NotNull T> attribute);

    @NotNull
    ChatComponent getDisplayName();

    void setDisplayName(@NotNull ChatComponent displayName);

    @NotNull
    List<@NotNull ChatComponent> getLore();

    void setLore(@NotNull List<@NotNull ChatComponent> lines);

    void addLoreLines(@NotNull List<@NotNull ChatComponent> lines);

    void addLoreLines(@NotNull List<@NotNull ChatComponent> lines, int atIndex);

    void setCustomModelKey(int modelKey);

    int getCustomModelKey();

    default boolean hasCustomModelKey() {
        return this.getCustomModelKey() != -1;
    }

    @NotNull EnumSet<@NotNull ItemFlag> getItemFlags();

    void addItemFlag(@NotNull ItemFlag flag);

    default void addItemFlags(@NotNull ItemFlag @NotNull... flags) {
        for (ItemFlag flag : flags) {
            this.addItemFlag(flag);
        }
    }

    void removeItemFlag(@NotNull ItemFlag flag);

    default void removeItemFlags(@NotNull ItemFlag @NotNull... flags) {
        for (ItemFlag flag : flags) {
            this.removeItemFlag(flag);
        }
    }

    void setUnbreakable(boolean unbreakable);

    boolean isUnbreakable();
}
