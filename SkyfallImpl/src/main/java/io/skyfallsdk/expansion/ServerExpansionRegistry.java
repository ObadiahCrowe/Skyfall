package io.skyfallsdk.expansion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sentry.Sentry;
import io.skyfallsdk.Server;
import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.ThreadPool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ServerExpansionRegistry implements ExpansionRegistry {

    private static final Map<Class<? extends Expansion>, Expansion> EXPANSION_INSTANCES = Maps.newHashMap();
    private static final Map<Class<? extends Expansion>, ExpansionInfo> EXPANSION_INFO = Maps.newHashMap();
    private static final Map<Class<? extends Expansion>, Thread> EXPANSION_MAIN_THREADS = Maps.newHashMap();

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
                Sentry.captureException(e);
            }
        }
    }

    public void loadAllExpansions() {
        try {
            Files.list(this.expansionPath).forEachOrdered(file -> {
                if (Files.isDirectory(file)) {
                    return;
                }

                if (!file.getFileName().toString().endsWith(".jar")) {
                    return;
                }

                try {
                    ServerExpansionRegistry.this.loadExpansion(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unloadAllExpansions() {
        for (Class<? extends Expansion> expansion : EXPANSION_INSTANCES.keySet()) {
            try {
                this.unloadExpansion(expansion);
            } catch (Exception e) {
                Server.get().getLogger().error(e);
                Sentry.captureException(e);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadExpansion(@NotNull Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException("Could not find any file at: " + path);
        }

        if (Files.isDirectory(path)) {
            throw new IOException("Cannot load a directory as a plugin.");
        }

        this.server.getLogger().info("Attempting to load expansion at: " + path.getFileName().toString());
        try (JarFile file = new JarFile(path.toFile())) {
            ExpansionClassLoader classLoader = new ExpansionClassLoader(path);
            List<String> classNames = Lists.newArrayList();
            Class<? extends Expansion> entryPoint = null;
            ExpansionInfo info = null;

            for (Enumeration<JarEntry> itr = file.entries(); itr.hasMoreElements(); ) {
                JarEntry entry = itr.nextElement();

                if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
                    continue;
                }

                try {
                    String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                    Class<?> clazz = classLoader.findClass(className);
                    classNames.add(className);

                    if (clazz != null && clazz.isAnnotationPresent(ExpansionInfo.class)) {
                        if (info != null) {
                            throw new IllegalStateException("Expansion cannot have two classes annotation with ExpansionInfo.");
                        }

                        if (!Expansion.class.isAssignableFrom(clazz)) {
                            throw new IllegalStateException("Class, " + clazz.getName() + ", is annotated with ExpansionInfo but is not an Expansion.");
                        }

                        entryPoint = (Class<? extends Expansion>) clazz;
                        info = clazz.getAnnotation(ExpansionInfo.class);
                    }
                } catch (NoClassDefFoundError ignored) {} catch (ClassNotFoundException | LinkageError e) {
                    e.printStackTrace();
                }
            }

            if (entryPoint == null) {
                throw new IllegalStateException("Expansion. " + path.getFileName().toString() + ", does not have an entry point.");
            }

            if (info == null) {
                throw new IllegalStateException("Expansion, " + path.getFileName().toString() + ", does not have an associated ExpansionInfo.");
            }

            if (EXPANSION_INSTANCES.containsKey(entryPoint)) {
                throw new IllegalStateException("Expansion, " + info.name() + ", has already been loaded.");
            }

            // TODO: 07/12/2020 Check and load dependencies
            for (String className : classNames) {
                try {
                    classLoader.loadClass(className);
                } catch (NoClassDefFoundError ignored) {}
            }

            Expansion expansion = entryPoint.getConstructor().newInstance();

            EXPANSION_INSTANCES.put(entryPoint, expansion);
            EXPANSION_INFO.put(entryPoint, info);

            if (!Files.exists(expansion.getPath())) {
                Files.createDirectory(expansion.getPath());
            }

            this.getLocalThread(expansion).start();
            this.server.getLogger().info("Successfully loaded Expansion, " + info.name() + "!");
        } catch (IllegalStateException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unloadExpansion(@NotNull Class<? extends Expansion> expansionClass) {
        Expansion expansion = EXPANSION_INSTANCES.getOrDefault(expansionClass, null);
        if (expansion == null) {
            return;
        }

        expansion.onShutdown();
    }

    @Override
    public @NotNull ExpansionInfo getExpansionInfo(@NotNull Class<? extends Expansion> expansionClass) {
        return EXPANSION_INFO.getOrDefault(expansionClass, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Expansion> T getExpansion(@NotNull Class<T> expansionClass) {
        return (T) EXPANSION_INSTANCES.getOrDefault(expansionClass, null);
    }

    @Override
    public @NotNull Collection<Expansion> getExpansions() {
        return Collections.unmodifiableCollection(EXPANSION_INSTANCES.values());
    }

    @Override
    public @Nullable Thread getLocalThread(@NotNull Expansion expansion) {
        return EXPANSION_MAIN_THREADS.computeIfAbsent(expansion.getClass(), expansionClass -> {
            Thread thread = PoolSpec.SCHEDULER.newThread(expansion::onStartup);
            thread.setName(thread.getName() + "-" + this.getExpansionInfo(expansionClass).name());
            thread.setUncaughtExceptionHandler((t, e) -> e.printStackTrace());

            return thread;
        });
    }
}
