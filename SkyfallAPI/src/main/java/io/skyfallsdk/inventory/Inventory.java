package io.skyfallsdk.inventory;

import io.skyfallsdk.item.Item;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.substance.Substance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public interface Inventory {

    int getInventoryId();

    @NotNull String getTitle();

    @NotNull InventoryType getType();

    int getSlots();

    @NotNull Item @Nullable[] getItems();

    @Nullable Item getItem(int slot);

    void setItem(int slot, @Nullable Item item);

    void addItem(@Nullable Substance substance);

    void addItems(@NotNull Substance @Nullable... substances);

    void addItem(@Nullable Item item);

    void addItems(@NotNull Item @Nullable... items);

    int getNextAvailableSlot();

    int[] getSlotsMatching(@Nullable Substance substance);

    int[] getSlotsMatching(@Nullable Item item);

    int findFirstContaining(@NotNull Substance substance);

    int findFirstContaining(@NotNull Item item);

    int findLastContaining(@NotNull Substance substance);

    int findLastContaining(@NotNull Item item);

    void removeItem(int slot);

    void removeItems(int... slots);

    void removeItem(@NotNull Substance substance);

    void removeItems(@NotNull Substance @NotNull... substances);

    void removeItem(@NotNull Item item);

    void removeItems(@NotNull Item @NotNull... items);

    boolean contains(@NotNull Item item);

    boolean contains(@NotNull Item item, int amount);

    boolean contains(@NotNull Substance substance);

    boolean contains(@NotNull Substance substance, int amount);

    void clear();

    void addViewer(@NotNull Player player);

    void addViewers(@NotNull Player @Nullable... players);

    void removeViewer(@NotNull Player player);

    void removeViewers(@NotNull Player @Nullable... players);

    @NotNull Set<? extends @NotNull Player> getViewers();
}
