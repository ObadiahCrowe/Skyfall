package io.skyfallsdk.expansion;

import com.google.common.collect.Maps;
import io.skyfallsdk.SkyfallServer;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ServerExpansionRegistry implements ExpansionRegistry {

    private static final Map<Class<? extends Expansion>, Expansion> EXPANSION_INSTANCES = Maps.newHashMap();
    private static final Map<Class<? extends Expansion>, ExpansionInfo> EXPANSION_INFO = Maps.newHashMap();

    private final SkyfallServer server;
    private final Path expansionPath;

    public ServerExpansionRegistry(SkyfallServer server) {
        this.server = server;

        this.expansionPath = this.server.getPath().resolve("expansions");
        if (!Files.exists(this.expansionPath)) {
            try {
                Files.createDirectory(this.expansionPath);
            } catch (IOException e) {
                this.server.getLogger().fatal(e);
            }
        }
    }

    public void loadAllExpansions() {
        try {
            Files.walkFileTree(this.expansionPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (Files.isDirectory(file)) {
                        return FileVisitResult.CONTINUE;
                    }

                    if (!file.getFileName().toString().endsWith(".jar")) {
                        return FileVisitResult.CONTINUE;
                    }

                    ServerExpansionRegistry.this.loadExpansion(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            this.server.getLogger().fatal(e);
        }
    }

    @Override
    public void loadExpansion(Path path) {
        this.server.getLogger().debug("Attempting to load expansion at: " + path.toString());
    }

    @Override
    public void unloadExpansion(Class<? extends Expansion> expansionClass) {
        
    }

    @Override
    public ExpansionInfo getExpansionInfo(Class<? extends Expansion> expansionClass) {
        return EXPANSION_INFO.getOrDefault(expansionClass, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Expansion> T getExpansion(Class<T> expansionClass) {
        return (T) EXPANSION_INSTANCES.getOrDefault(expansionClass, null);
    }

    @Override
    public Collection<Expansion> getExpansions() {
        return Collections.unmodifiableCollection(EXPANSION_INSTANCES.values());
    }
}
