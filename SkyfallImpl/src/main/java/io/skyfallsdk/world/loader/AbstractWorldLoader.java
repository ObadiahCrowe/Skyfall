package io.skyfallsdk.world.loader;

import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.world.SkyfallWorld;
import io.skyfallsdk.world.WorldLoader;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AbstractWorldLoader implements WorldLoader {

    protected static final Reference2ReferenceMap<String, SkyfallWorld> WORLDS = new Reference2ReferenceOpenHashMap<>();

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

    @Override
    public Path getWorldDirectory() {
        return this.worldDirectory;
    }

    public abstract void unloadAll();
}
