package io.skyfallsdk.world.loader;

import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.world.Dimension;
import io.skyfallsdk.world.SkyfallWorld;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.generate.WorldGenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class SlimeWorldLoader extends AbstractWorldLoader<NBTInputStream, NBTOutputStream> {

    public SlimeWorldLoader(SkyfallServer server, Path baseDir) {
        super(server, baseDir);
    }

    @Override
    public CompletableFuture<Optional<World>> load(Path path) throws IOException {
        return null;
    }

    @Override
    public CompletableFuture<Void> unload(String name) {
        return null;
    }

    @Override
    public CompletableFuture<Void> unload(World world) {
        return null;
    }

    @Override
    public void unloadAll() {

    }

    @Override
    public CompletableFuture<Optional<World>> get(String name) {
        return null;
    }

    @Override
    public CompletableFuture<World> create(String name, Dimension dimension, WorldGenerator generator) throws IOException {
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
