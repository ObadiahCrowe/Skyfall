package io.skyfallsdk.inventory;

import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.world.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface InventoryFactory {

    <T extends Inventory> @NotNull T createInventory(@NotNull InventoryType type, @NotNull String title);

    <T extends Inventory> @NotNull T createInventory(int size, @NotNull String title);

    <T extends Inventory> @NotNull Optional<@Nullable T> getBlockInventory(@NotNull Block block);

    <T extends Inventory> @NotNull Optional<@Nullable T> getEntityInventory(@NotNull Entity entity);
}
