package io.skyfallsdk.item;

import io.skyfallsdk.item.meta.ItemMetadata;
import io.skyfallsdk.substance.Substance;
import org.jetbrains.annotations.NotNull;

public class SkyfallItemFactory implements ItemFactory {

    @Override
    public <T extends ItemMetadata<T>> @NotNull T getNewMetadata(@NotNull Substance substance) {
        return null;
    }

    @Override
    public boolean isMetadataEqual(@NotNull Item first, @NotNull Item second) {
        return false;
    }

    @Override
    public <T extends ItemMetadata<T>> boolean isMetadataApplicable(@NotNull Substance substance, @NotNull Class<T> metadataClass) {
        return false;
    }
}
