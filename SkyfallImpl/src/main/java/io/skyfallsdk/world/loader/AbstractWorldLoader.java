package io.skyfallsdk.world.loader;

import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.util.Validator;
import io.skyfallsdk.world.SkyfallWorld;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.WorldLoader;
import io.skyfallsdk.world.generate.WorldGenerator;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractWorldLoader<I extends InputStream, O extends OutputStream> implements WorldLoader {

    protected static final Reference2ReferenceMap<String, SkyfallWorld> WORLDS = new Reference2ReferenceOpenHashMap<>();
    protected static final Reference2ReferenceMap<Class<? extends WorldGenerator>, WorldGenerator> GENERATORS = new Reference2ReferenceOpenHashMap<>();

    protected final SkyfallServer server;
    protected final Path worldDirectory;
    protected final Logger logger;

    public AbstractWorldLoader(SkyfallServer server, Path baseDir) {
        this.server = server;
        this.logger = LogManager.getLogger(AnvilWorldLoader.class);

        Path worldDirectory = baseDir.resolve("worlds");
        if (!Files.exists(worldDirectory)) {
            try {
                Files.createDirectory(worldDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.worldDirectory = worldDirectory;
    }

    protected void registerDefaultGenerators() {

    }

    @Override
    public Path getWorldDirectory() {
        return this.worldDirectory;
    }

    @Override
    public Collection<World> getLoadedWorlds() {
        return Collections.unmodifiableCollection(WORLDS.values());
    }

    @Override
    public void registerWorldGenerator(@NotNull WorldGenerator generator) {
        Validator.notNull(generator);

        GENERATORS.put(generator.getClass(), generator);
    }

    @Override
    public void unregisterWorldGenerator(@NotNull WorldGenerator generator) {
        Validator.notNull(generator);

        GENERATORS.remove(generator.getClass(), generator);
    }

    public boolean hasGenerator(@NotNull WorldGenerator generator) {
        return GENERATORS.containsKey(generator.getClass());
    }

    @Override
    public @NotNull Collection<@NotNull WorldGenerator> getWorldGenerators() {
        return Collections.unmodifiableCollection(GENERATORS.values());
    }

    protected abstract SkyfallWorld deserialize(I input);

    protected abstract O serialize(SkyfallWorld world);

    public abstract void unloadAll();
}
