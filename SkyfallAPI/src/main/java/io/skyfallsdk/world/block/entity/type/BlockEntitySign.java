package io.skyfallsdk.world.block.entity.type;

import io.skyfallsdk.world.block.entity.BlockEntity;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntityBrewingStandMetadata;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntitySignMetadata;

public interface BlockEntitySign<M extends BlockEntitySignMetadata<?>> extends BlockEntity<M> {
}
