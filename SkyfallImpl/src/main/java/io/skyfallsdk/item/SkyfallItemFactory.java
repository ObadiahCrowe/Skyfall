package io.skyfallsdk.item;

import io.skyfallsdk.item.meta.ItemMetadata;
import io.skyfallsdk.item.meta.SkyfallItemMetadata;
import io.skyfallsdk.substance.Substance;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SkyfallItemFactory implements ItemFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemMetadata> @NotNull T getNewMetadata(@NotNull Substance substance) {
        return (T) new SkyfallItemMetadata(substance);
    }

    @Override
    public boolean isMetadataEqual(@NotNull Item first, @NotNull Item second) {
        if (!Objects.equals(first.getMetadata().getClass(), second.getMetadata().getClass())) {
            return false;
        }

        return first.getMetadata().equals(second.getMetadata());
    }

    @Override
    public <T extends ItemMetadata> boolean isMetadataApplicable(@NotNull Substance substance, @NotNull Class<T> metadataClass) {
        return false;
    }
}
