package io.skyfallsdk.world.block.entity.type;

import io.skyfallsdk.world.block.entity.BlockEntity;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntityBrewingStandMetadata;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntityStructureMetadata;

public interface BlockEntityStructure<M extends BlockEntityStructureMetadata<?>> extends BlockEntity<M> {
}
