package io.skyfallsdk.world.block.entity.type;

import io.skyfallsdk.world.block.entity.BlockEntity;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntityBrewingStandMetadata;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntityConduitMetadata;

public interface BlockEntityConduit<M extends BlockEntityConduitMetadata<?>> extends BlockEntity<M> {
}
