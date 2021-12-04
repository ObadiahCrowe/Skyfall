package io.skyfallsdk.world.block.entity.type;

import io.skyfallsdk.world.block.entity.BlockEntity;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntityBrewingStandMetadata;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntityDropperMetadata;

public interface BlockEntityDropper<M extends BlockEntityDropperMetadata<?>> extends BlockEntity<M> {
}
