package io.skyfallsdk.world;

import io.skyfallsdk.Server;
import io.skyfallsdk.concurrent.tick.DefaultTickSpec;
import io.skyfallsdk.concurrent.tick.Tickable;
import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.world.option.Difficulty;
import io.skyfallsdk.world.option.Gamemode;
import io.skyfallsdk.world.block.Block;
import io.skyfallsdk.world.chunk.Chunk;
import io.skyfallsdk.world.meta.Weather;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface World extends Tickable<DefaultTickSpec> {

    @NotNull
    String getName();

    @NotNull
    UUID getUuid();

    @Nullable
    Path getDirectory();

    @NotNull
    CompletableFuture<@NotNull Optional<@Nullable Block>> getBlockAt(@NotNull Position position);

    default @NotNull CompletableFuture<@NotNull Optional<@Nullable Block>> getBlockAt(int x, int y, int z) {
        return this.getBlockAt(new Position(this, x, y, z));
    }

    @NotNull
    Dimension getDimension();

    @NotNull
    Gamemode getGamemode();

    void setGamemode(@NotNull Gamemode gamemode);

    @NotNull
    Difficulty getDifficulty();

    void setDifficulty(@NotNull Difficulty difficulty);

    @NotNull
    WorldBorder getBorder();

    @NotNull
    Weather getWeather();

    @NotNull
    Set<? extends @NotNull Entity> getEntities();

    @NotNull
    Set<? extends @NotNull Player> getPlayers();

    @NotNull
    default CompletableFuture<@NotNull Chunk> getChunk(int x, int z) {
        return this.getChunk(x, z, true);
    }

    @NotNull
    CompletableFuture<@Nullable Chunk> getChunk(int x, int z, boolean generate);

    default @NotNull CompletableFuture<@NotNull Void> unload() {
        return Server.get().getWorldLoader().unload(this);
    }
}
