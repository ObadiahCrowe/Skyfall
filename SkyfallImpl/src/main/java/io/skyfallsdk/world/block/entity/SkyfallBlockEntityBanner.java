package io.skyfallsdk.world.block.entity;

import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.world.block.entity.meta.SkyfallBlockEntityBannerMetadata;
import io.skyfallsdk.world.block.entity.type.BlockEntityBanner;
import org.jetbrains.annotations.NotNull;

public class SkyfallBlockEntityBanner extends AbstractBlockEntity<SkyfallBlockEntityBannerMetadata> implements BlockEntityBanner<SkyfallBlockEntityBannerMetadata> {

    public SkyfallBlockEntityBanner(@NotNull BlockEntityType type, @NotNull TagCompound entityTag) {
        super(type, entityTag);
    }

    @Override
    protected SkyfallBlockEntityBannerMetadata parseEntityTag(@NotNull TagCompound entityTag) {
        return null;
    }
}
