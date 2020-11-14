package io.skyfallsdk.expansion;

import javax.annotation.concurrent.ThreadSafe;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@ThreadSafe
public interface ExpansionRegistry {

    void loadExpansion(Path path);

    default void unloadExpansion(Expansion expansion) {
        this.unloadExpansion(expansion.getClass());
    }

    void unloadExpansion(Class<? extends Expansion> expansionClass);

    default ExpansionInfo getExpansionInfo(Expansion expansion) {
        return this.getExpansionInfo(expansion.getClass());
    }

    ExpansionInfo getExpansionInfo(Class<? extends Expansion> expansionClass);

    <T extends Expansion> T getExpansion(Class<T> expansionClass);

    Collection<Expansion> getExpansions();
}
