package io.skyfallsdk.world;

import io.skyfallsdk.world.generate.WorldGenerator;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class SkyfallWorldLoader implements WorldLoader {

    private static final Reference2ReferenceMap<String, SkyfallWorld> WORLDS = new Reference2ReferenceOpenHashMap<>();

    private final Path baseDir;
    private final Logger logger;

    public SkyfallWorldLoader(Path baseDir) {
        this.baseDir = baseDir;
        this.logger = LogManager.getLogger(SkyfallWorldLoader.class);
    }

    @Override
    public Optional<World> load(Path path) throws IOException {
        return Optional.empty();
    }

    @Override
    public void unload(World world) {
        //
    }

    @Override
    public Optional<World> get(String name) {
        return Optional.empty();
    }

    @Override
    public World create(String name, Dimension dimension, WorldGenerator generator) throws IOException {
        return null;
    }

    @Override
    public Collection<World> getLoadedWorlds() {
        return Collections.unmodifiableCollection(WORLDS.values());
    }
}
