package io.skyfallsdk.world.loader;

import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.ThreadPool;
import io.skyfallsdk.nbt.file.NBTFile;
import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.util.FileVisitorCallback;
import io.skyfallsdk.world.Dimension;
import io.skyfallsdk.world.SkyfallWorld;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.generate.WorldGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class AnvilWorldLoader extends AbstractWorldLoader<NBTInputStream, NBTOutputStream> {

    public AnvilWorldLoader(SkyfallServer server, Path baseDir) {
        super(server, baseDir);
    }

    @Override
    public @NotNull CompletableFuture<@NotNull Optional<@Nullable World>> load(@NotNull Path path) throws IOException {
        FileVisitorCallback visitor = new FileVisitorCallback("level.dat");
        Files.walkFileTree(path, visitor);

        Optional<Path> levelDat = visitor.getMatchingPath();
        if (levelDat.isEmpty()) {
            return CompletableFuture.completedFuture(Optional.empty());
        }

        return this.loadInternal(levelDat.get()).thenApply(world -> {
            if (world != null) {
                this.server.getLogger().info("Loaded world, \"" + world.getName() + "\", successfully.");
                WORLDS.put(world.getName(), world);
            }

            return Optional.ofNullable(world);
        });
    }

    private CompletableFuture<SkyfallWorld> loadInternal(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IllegalStateException("No level.dat found for: \"" + path.getFileName().toString() + "\". Cannot continue reading world.");
        }

        final Path parent = path.getParent();

        return CompletableFuture.supplyAsync(() -> {
            try (NBTInputStream stream = NBTFile.newInputStream(path, true)) {
                return (TagCompound) ((TagCompound) stream.readTag()).get("Data");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }, ThreadPool.createForSpec(PoolSpec.WORLD)).thenApply(data -> {
            if (data == null) {
                return null;
            }

            Path uid = parent.resolve("uid.dat");
            if (!Files.exists(uid)) {
                try (NBTInputStream stream = NBTFile.newInputStream(parent.resolve("uid.dat"), true)) {
                    UUID levelUuid = new UUID(stream.readLong(), stream.readLong());

                    return new SkyfallWorld(parent, data, levelUuid);
                } catch (IOException e) {
                    this.server.getLogger().error("Failed to obtain level UUID for \"" + path.getFileName().toString() + "\", generating a new one.");
                    return new SkyfallWorld(parent, data, UUID.randomUUID());
                }
            } else {
                return new SkyfallWorld(parent, data, UUID.randomUUID());
            }
        }).thenApply(world -> {
            if (this.server.getPerformanceConfig().getInitialChunkCache() <= 0) {
                return world;
            }

            for (int x = 0; x < 5; x++) {
                for (int z = 0; z < 5; z++) {
                    world.getChunk(x, z);
                }
            }

            return world;
        }).thenApply(world -> {


            return world;
        });
    }

    @Override
    public CompletableFuture<Void> unload(String name) {
        SkyfallWorld world = WORLDS.get(name.toLowerCase(Locale.ROOT));
        if (world == null) {
            throw new IllegalStateException("Could not find loaded world, " + name + "!");
        }

        return this.unload(world);
    }

    @Override
    public CompletableFuture<Void> unload(World world) {
        if (!WORLDS.containsKey(world.getName())) {
            throw new IllegalArgumentException("World, " + world.getName() + ", is not currently loaded!");
        }

        // TODO: 21/04/2021
        return null;
    }

    @Override
    public void unloadAll() {
        for (Iterator<SkyfallWorld> itr = WORLDS.values().stream().iterator(); itr.hasNext();) {
            this.unload(itr.next());
        }
    }

    @Override
    public CompletableFuture<Optional<World>> get(String name) {
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(WORLDS.getOrDefault(name, null)), ThreadPool.createForSpec(PoolSpec.WORLD));
    }

    @Override
    public CompletableFuture<World> create(String name, Dimension dimension, WorldGenerator generator) throws IOException {
        Path worldFolder = this.worldDirectory.resolve(name);
        if (Files.exists(worldFolder) && Files.isDirectory(worldFolder)) {
            throw new IOException("A world with the name, \"" + name + "\" already exists.");
        }

        return null;
    }

    @Override
    protected SkyfallWorld deserialize(NBTInputStream input) {
        return null;
    }

    @Override
    protected NBTOutputStream serialize(SkyfallWorld world) {
        return null;
    }
}
