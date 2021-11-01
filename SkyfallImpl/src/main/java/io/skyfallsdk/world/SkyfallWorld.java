package io.skyfallsdk.world;

import com.google.common.collect.Lists;
import io.skyfallsdk.Server;
import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.ThreadPool;
import io.skyfallsdk.concurrent.tick.DefaultTickSpec;
import io.skyfallsdk.concurrent.tick.ServerTickRegistry;
import io.skyfallsdk.concurrent.tick.TickRegistry;
import io.skyfallsdk.concurrent.tick.TickStage;
import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.nbt.tag.type.TagString;
import io.skyfallsdk.packet.play.PlayOutTimeUpdate;
import io.skyfallsdk.player.SkyfallPlayer;
import io.skyfallsdk.world.option.Difficulty;
import io.skyfallsdk.world.option.Gamemode;
import io.skyfallsdk.util.Validator;
import io.skyfallsdk.world.block.Block;
import io.skyfallsdk.world.chunk.Chunk;
import io.skyfallsdk.world.chunk.SkyfallChunk;
import io.skyfallsdk.world.meta.SkyfallWeather;
import io.skyfallsdk.world.meta.Weather;
import io.skyfallsdk.world.meta.WeatherState;
import io.skyfallsdk.world.region.RegionFile;
import io.skyfallsdk.world.rule.GameRule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SkyfallWorld implements World {

    private final ReentrantReadWriteLock lock;

    private final String name;
    private final Path path;
    private final UUID levelUuid;

    private final AtomicLong worldAge;
    private final AtomicLong time;

    private final Dimension dimension;
    private final SkyfallWorldBorder worldBorder;
    private final SkyfallWeather weather;

    private final List<GameRule> gameRules;
    private Gamemode gamemode;
    private Difficulty difficulty;

    public SkyfallWorld(@NotNull Path path, @NotNull TagCompound data, @NotNull UUID levelUuid) {
        this.lock = new ReentrantReadWriteLock(true);

        this.name = ((TagString) data.get("LevelName")).getValue();
        this.path = path;
        this.levelUuid = levelUuid;

        this.worldAge = new AtomicLong((long) data.get("Time").getValue());
        this.time = new AtomicLong((long) data.get("DayTime").getValue());

        this.dimension = Dimension.OVERWORLD;
        this.worldBorder = new SkyfallWorldBorder(
          this,
          new Position(this, (double) data.get("BorderCenterX").getValue(), 0, (double) data.get("BorderCenterZ").getValue()),
          (double) data.get("BorderDamagePerBlock").getValue(),
          (double) data.get("BorderSafeZone").getValue(),
          (double) data.get("BorderSizeLerpTarget").getValue(),
          (double) data.get("BorderSize").getValue(),
          (double) data.get("BorderWarningBlocks").getValue(),
          (double) data.get("BorderWarningTime").getValue()
        );
        this.weather = new SkyfallWeather(
          ((byte) data.get("raining").getValue()) == 1 ? WeatherState.RAINING : ((byte) data.get("thundering").getValue()) == 1 ? WeatherState.THUNDERING : WeatherState.CLEAR,
          (int) data.get("clearWeatherTime").getValue(),
          (int) data.get("rainTime").getValue(),
          (int) data.get("thunderTime").getValue()
        );

        this.gameRules = Lists.newArrayList();
        this.gamemode = Gamemode.values()[(int) data.get("GameType").getValue()];
        this.difficulty = Difficulty.values()[(byte) data.get("Difficulty").getValue()];
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull UUID getUuid() {
        return this.levelUuid;
    }

    @Override
    public @Nullable Path getDirectory() {
        return this.path;
    }

    @Override
    public @NotNull CompletableFuture<@NotNull Optional<@Nullable Block>> getBlockAt(@NotNull Position position) {
        Validator.notNull(position);

        int chunkX = ((int) position.getX()) >> 4;
        int chunkZ = ((int) position.getZ()) >> 4;

        return this.getChunk(chunkX, chunkZ, true).thenApply(chunk -> {
            // (this.x << 4) | x, y, (this.z << 4) | z)

            return Optional.empty();
        });
    }

    @Override
    public @NotNull Dimension getDimension() {
        return this.dimension;
    }

    @Override
    public @NotNull Gamemode getGamemode() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.gamemode;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setGamemode(@NotNull Gamemode gamemode) {
        Validator.notNull(gamemode);

        Lock lock = this.lock.writeLock();
        try {
            lock.lock();

            this.gamemode = gamemode;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public @NotNull Difficulty getDifficulty() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.difficulty;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setDifficulty(@NotNull Difficulty difficulty) {
        Validator.notNull(difficulty);

        Lock lock = this.lock.writeLock();
        try {
            lock.lock();

            this.difficulty = difficulty;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public @NotNull WorldBorder getBorder() {
        return this.worldBorder;
    }

    @Override
    public @NotNull Weather getWeather() {
        return this.weather;
    }

    @Override
    public @NotNull List<@NotNull Entity> getEntities() {
        return null;
    }

    @Override
    public @NotNull List<@NotNull SkyfallPlayer> getPlayers() {
        return null;
    }

    @Override
    public @NotNull CompletableFuture<@Nullable Chunk> getChunk(int x, int z, boolean generate) {
        return CompletableFuture.supplyAsync(() -> {
            final int regionX = (int) Math.floor(x / 32.0D);
            final int regionZ = (int) Math.floor(z / 32.0D);

            try {
                RegionFile file = new RegionFile(regionX, regionZ, this.path);
                NBTInputStream chunkInputStream = new NBTInputStream(file.readChunk(x, z));

                TagCompound root = (TagCompound) chunkInputStream.readTag();

                int dataVersion = (int) root.get("DataVersion").getValue();
                int serverDataVersion = Server.get().getBaseVersion().getDataVersion();
                if (serverDataVersion != dataVersion) {
                    Server.get().getLogger().warn("Chunk for world, \"" + this.name + "\" at " + x + ", " + z + " has a mismatching Data Version (" + dataVersion + "). " +
                      "This may cause issues and instability. Current Skyfall Data Version: " + serverDataVersion);
                }

                TagCompound level = (TagCompound) root.get("Level");

                return new SkyfallChunk(this, level);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (generate) {

            }

            return null;
        }, ThreadPool.createForSpec(PoolSpec.CHUNKS));
    }

    @Override
    public void onTick() {
        // Change world time
        long worldTime;
        if (this.time.get() == 240_000L) {
            this.time.set(0L);
            worldTime = 0L;
        } else {
            worldTime = this.time.addAndGet(20L);
        }

        this.worldAge.addAndGet(1L);

        for (SkyfallPlayer player : this.getPlayers()) {
            player.getClient().sendPacket(PlayOutTimeUpdate.make(player.getClient().getVersion(), this.worldAge.get(), worldTime));
        }
    }

    @Override
    public TickRegistry<DefaultTickSpec> getRegistry() {
        return ServerTickRegistry.getTickRegistry(DefaultTickSpec.WORLD);
    }

    @Override
    public TickStage getStage() {
        return TickStage.DURING;
    }
}
