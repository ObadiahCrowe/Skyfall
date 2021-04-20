package io.skyfallsdk.world.loader;

import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.ThreadPool;
import io.skyfallsdk.util.FileVisitorCallback;
import io.skyfallsdk.world.Dimension;
import io.skyfallsdk.world.SkyfallWorld;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.WorldLoader;
import io.skyfallsdk.world.generate.WorldGenerator;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class AnvilWorldLoader extends AbstractWorldLoader {

    public AnvilWorldLoader(SkyfallServer server, Path baseDir) {
        super(server, baseDir);
    }

    @Override
    public CompletableFuture<Optional<World>> load(Path path) throws IOException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                FileVisitorCallback visitor = new FileVisitorCallback("level.dat");
                Files.walkFileTree(this.worldDirectory, visitor);

                Optional<Path> levelDat = visitor.getMatchingPath();
                if (levelDat.isPresent()) {
                    return Optional.ofNullable(this.loadInternal(levelDat.get()));
                }
            } catch (IOException e) {
                this.server.getLogger().error(e);
            }

            return Optional.empty();
        }, ThreadPool.createForSpec(PoolSpec.WORLD));
    }

    private World loadInternal(Path path) {
        return null;
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
        if (!WORLDS.containsKey(world.getName().toLowerCase(Locale.ROOT))) {
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
        return null;
    }

    @Override
    public Collection<World> getLoadedWorlds() {
        return Collections.unmodifiableCollection(WORLDS.values());
    }
}
