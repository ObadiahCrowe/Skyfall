package io.skyfallsdk.expansion;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

@ThreadSafe
public interface ExpansionRegistry {

    void loadExpansion(@NotNull Path path) throws IOException;

    default void unloadExpansion(@NotNull Expansion expansion) {
        this.unloadExpansion(expansion.getClass());
    }

    void unloadExpansion(@NotNull Class<? extends Expansion> expansionClass);

    default @NotNull ExpansionInfo getExpansionInfo(@NotNull Expansion expansion) {
        return this.getExpansionInfo(expansion.getClass());
    }

    @NotNull
    ExpansionInfo getExpansionInfo(@NotNull Class<? extends Expansion> expansionClass);

    <T extends Expansion> @Nullable T getExpansion(@NotNull Class<T> expansionClass);

    @NotNull
    Collection<@NotNull Expansion> getExpansions();

    @Nullable
    Thread getLocalThread(@NotNull Expansion expansion);
}
