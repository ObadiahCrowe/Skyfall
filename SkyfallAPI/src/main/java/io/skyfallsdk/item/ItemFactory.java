package io.skyfallsdk.item;

import io.skyfallsdk.item.meta.ItemMetadata;
import io.skyfallsdk.substance.Substance;
import org.jetbrains.annotations.NotNull;

public interface ItemFactory {

    default <T extends ItemMetadata> @NotNull T getNewMetadata(@NotNull Item item) {
        return this.getNewMetadata(item.getSubstance());
    }

    <T extends ItemMetadata> @NotNull T getNewMetadata(@NotNull Substance substance);

    boolean isMetadataEqual(@NotNull Item first, @NotNull Item second);

    <T extends ItemMetadata> boolean isMetadataApplicable(@NotNull Substance substance, @NotNull Class<T> metadataClass);
}
