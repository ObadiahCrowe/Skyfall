package io.skyfallsdk.inventory;

import io.skyfallsdk.item.Item;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.substance.Substance;

import java.util.List;

public class SkyfallInventory implements Inventory {
    @Override
    public int getInventoryId() {
        return 0;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public InventoryType getType() {
        return null;
    }

    @Override
    public int getSlots() {
        return 0;
    }

    @Override
    public Item[] getItems() {
        return new Item[0];
    }

    @Override
    public Item getItem(int slot) {
        return null;
    }

    @Override
    public void setItem(int slot, Item item) {

    }

    @Override
    public void addItem(Substance substance) {

    }

    @Override
    public void addItems(Substance... substances) {

    }

    @Override
    public void addItem(Item item) {

    }

    @Override
    public void addItems(Item... items) {

    }

    @Override
    public int getNextAvailableSlot() {
        return 0;
    }

    @Override
    public int[] getSlotsMatching(Substance substance) {
        return new int[0];
    }

    @Override
    public int[] getSlotsMatching(Item item) {
        return new int[0];
    }

    @Override
    public int findFirstContaining(Substance substance) {
        return 0;
    }

    @Override
    public int findFirstContaining(Item item) {
        return 0;
    }

    @Override
    public int findLastContaining(Substance substance) {
        return 0;
    }

    @Override
    public int findLastContaining(Item item) {
        return 0;
    }

    @Override
    public void removeItem(int slot) {

    }

    @Override
    public void removeItems(int... slots) {

    }

    @Override
    public void removeItem(Substance substance) {

    }

    @Override
    public void removeItems(Substance... substances) {

    }

    @Override
    public void removeItem(Item item) {

    }

    @Override
    public void removeItems(Item... items) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void addViewer(Player player) {

    }

    @Override
    public void addViewers(Player... players) {

    }

    @Override
    public void removeViewer(Player player) {

    }

    @Override
    public void removeViewers(Player... players) {

    }

    @Override
    public List<Player> getViewers() {
        return null;
    }
}
