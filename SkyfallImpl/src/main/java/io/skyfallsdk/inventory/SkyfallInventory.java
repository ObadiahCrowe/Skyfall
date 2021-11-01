package io.skyfallsdk.inventory;

import com.google.common.collect.Sets;
import io.skyfallsdk.item.Item;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.player.SkyfallPlayer;
import io.skyfallsdk.substance.Substance;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class SkyfallInventory implements Inventory {

    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);

    private final int id;

    private final InventoryType type;
    private final String title;
    private final int slots;

    private final Int2ObjectMap<Item> inventoryContents;
    private final Set<SkyfallPlayer> viewers;

    public SkyfallInventory(@NotNull InventoryType type, @NotNull String title) {
        this(type, title, type.getDefaultSlots());
    }

    public SkyfallInventory(@NotNull InventoryType type, @NotNull String title, int slots) {
        this.id = NEXT_ID.getAndIncrement();

        this.type = type;
        this.title = title;
        this.slots = slots;

        this.inventoryContents = new Int2ObjectOpenHashMap<>(this.slots);
        this.viewers = Sets.newConcurrentHashSet();
    }

    @Override
    public int getInventoryId() {
        return this.id;
    }

    @Override
    public @NotNull String getTitle() {
        return this.title;
    }

    @Override
    public @NotNull InventoryType getType() {
        return this.type;
    }

    @Override
    public int getSlots() {
        return this.slots;
    }

    @Override
    public @NotNull Item @Nullable [] getItems() {
        return this.inventoryContents.values().toArray(new Item[0]);
    }

    @Override
    public @Nullable Item getItem(int slot) {
        return this.inventoryContents.get(slot);
    }

    @Override
    public void setItem(int slot, @Nullable Item item) {
        this.inventoryContents.put(slot, item);
    }

    @Override
    public void addItem(@Nullable Substance substance) {

    }

    @Override
    public void addItems(@NotNull Substance @Nullable ... substances) {

    }

    @Override
    public void addItem(@Nullable Item item) {

    }

    @Override
    public void addItems(@NotNull Item @Nullable ... items) {

    }

    @Override
    public int getNextAvailableSlot() {
        return 0;
    }

    @Override
    public int[] getSlotsMatching(@Nullable Substance substance) {
        return new int[0];
    }

    @Override
    public int[] getSlotsMatching(@Nullable Item item) {
        return new int[0];
    }

    @Override
    public int findFirstContaining(@NotNull Substance substance) {
        return 0;
    }

    @Override
    public int findFirstContaining(@NotNull Item item) {
        return 0;
    }

    @Override
    public int findLastContaining(@NotNull Substance substance) {
        return 0;
    }

    @Override
    public int findLastContaining(@NotNull Item item) {
        return 0;
    }

    @Override
    public void removeItem(int slot) {
        this.inventoryContents.remove(slot);
    }

    @Override
    public void removeItems(int... slots) {
        for (int slot : slots) {
            this.inventoryContents.remove(slot);
        }
    }

    @Override
    public void removeItem(@NotNull Substance substance) {

    }

    @Override
    public void removeItems(@NotNull Substance @NotNull ... substances) {

    }

    @Override
    public void removeItem(@NotNull Item item) {

    }

    @Override
    public void removeItems(@NotNull Item @NotNull ... items) {

    }

    @Override
    public boolean contains(@NotNull Item item) {
        return false;
    }

    @Override
    public boolean contains(@NotNull Item item, int amount) {
        return false;
    }

    @Override
    public boolean contains(@NotNull Substance substance) {
        return false;
    }

    @Override
    public boolean contains(@NotNull Substance substance, int amount) {
        return false;
    }

    @Override
    public void clear() {
        this.inventoryContents.clear();
    }

    @Override
    public void addViewer(@NotNull Player player) {

    }

    @Override
    public void addViewers(@NotNull Player @Nullable ... players) {

    }

    @Override
    public void removeViewer(@NotNull Player player) {

    }

    @Override
    public void removeViewers(@NotNull Player @Nullable ... players) {

    }

    @Override
    public @NotNull Set<? extends @NotNull Player> getViewers() {
        return Collections.unmodifiableSet(this.viewers);
    }
}
