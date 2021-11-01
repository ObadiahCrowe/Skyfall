package io.skyfallsdk.inventory;

import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.world.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SkyfallInventoryFactory implements InventoryFactory {

    private final SkyfallServer server;

    public SkyfallInventoryFactory(SkyfallServer server) {
        this.server = server;
    }

    @Override
    public <T extends Inventory> @NotNull T createInventory(@NotNull InventoryType type, @NotNull String title) {
        return null;
    }

    @Override
    public <T extends Inventory> @NotNull T createInventory(int size, @NotNull String title) {
        return null;
    }

    @Override
    public @NotNull <T extends Inventory> Optional<@Nullable T> getBlockInventory(@NotNull Block block) {
        return Optional.empty();
    }

    @Override
    public @NotNull <T extends Inventory> Optional<@Nullable T> getEntityInventory(@NotNull Entity entity) {
        return Optional.empty();
    }
}
