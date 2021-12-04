package io.skyfallsdk.world.block.entity.meta;

import io.skyfallsdk.world.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public interface BlockEntityMetadata<E extends BlockEntity> {

    @NotNull
    E getBlockEntity();
}
