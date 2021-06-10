package io.skyfallsdk.inventory;

import io.skyfallsdk.item.Item;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.substance.Substance;

import java.util.List;

public interface Inventory {

    int getInventoryId();

    String getTitle();

    InventoryType getType();

    int getSlots();

    Item[] getItems();

    Item getItem(int slot);

    void setItem(int slot, Item item);

    void addItem(Substance substance);

    void addItems(Substance... substances);

    void addItem(Item item);

    void addItems(Item... items);

    int getNextAvailableSlot();

    int[] getSlotsMatching(Substance substance);

    int[] getSlotsMatching(Item item);

    int findFirstContaining(Substance substance);

    int findFirstContaining(Item item);

    int findLastContaining(Substance substance);

    int findLastContaining(Item item);

    void removeItem(int slot);

    void removeItems(int... slots);

    void removeItem(Substance substance);

    void removeItems(Substance... substances);

    void removeItem(Item item);

    void removeItems(Item... items);

    void clear();

    void addViewer(Player player);

    void addViewers(Player... players);

    void removeViewer(Player player);

    void removeViewers(Player... players);

    List<Player> getViewers();
}
