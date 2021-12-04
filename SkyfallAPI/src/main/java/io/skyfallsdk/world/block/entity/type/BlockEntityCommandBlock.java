package io.skyfallsdk.world.block.entity.type;

import io.skyfallsdk.world.block.entity.BlockEntity;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntityBrewingStandMetadata;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntityCommandBlockMetadata;

public interface BlockEntityCommandBlock<M extends BlockEntityCommandBlockMetadata<?>> extends BlockEntity<M> {
}
