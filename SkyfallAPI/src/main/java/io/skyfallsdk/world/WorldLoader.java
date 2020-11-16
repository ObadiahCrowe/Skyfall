package io.skyfallsdk.world;

import io.skyfallsdk.world.generate.WorldGenerator;

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;

/**
 * Represents
 */
@ThreadSafe
public interface WorldLoader {

    /**
     * Loads a world from disk into memory.
     *
     * @param path Path of the world directory to load from.
     *
     * @return The corresponding World if it was found.
     * @throws IOException If the world files are found but an issue occurs whilst loading it.
     */
    Optional<World> load(Path path) throws IOException;

    /**
     * Unloads a world from memory and saves to disk by it's name.
     *
     * @param name Name of the world to unload. Not case sensitive.
     */
    default void unload(String name) {
        this.get(name).ifPresent(this::unload);
    }

    /**
     * Unloads a world from memory and saves to disk.
     *
     * @param world World to unload.
     */
    void unload(World world);

    /**
     * Obtains the in-memory version of the World. Will return as {@link Optional#empty()} if no in-memory World
     * was found.
     *
     * @param name Name of the World to obtain. Not case sensitive.
     *
     * @return The world if found, {@link Optional#empty()} if no world by that name was found.
     */
    Optional<World> get(String name);

    /**
     * Attempts to obtain a World by it's name, if none of that name was found, it will create a new World.
     *
     * @param name Name of the World to obtain or create. Not case sensitive.
     * @param dimension Dimension of the World if creation is required.
     * @param generator Generator of the World if creation is required.
     *
     * @return The obtained or newly created World.
     * @throws IOException If an unexpected error occurs whilst creating the World.
     */
    default World getOrCreate(String name, Dimension dimension, WorldGenerator generator) throws IOException {
        return this.get(name).orElse(this.create(name, dimension, generator));
    }

    /**
     * Creates a new World.
     *
     * @param name Name of the World to create.
     * @param dimension Dimension of the World.
     * @param generator Generator of the World.
     *
     * @return The newly created World.
     * @throws IOException If the world already exists on disk. Not case sensitive.
     */
    World create(String name, Dimension dimension, WorldGenerator generator) throws IOException;

    /**
     * @return All in-memory Worlds.
     */
    Collection<World> getLoadedWorlds();
}
