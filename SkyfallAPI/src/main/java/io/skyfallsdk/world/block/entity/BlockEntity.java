package io.skyfallsdk.world.block.entity;

import io.skyfallsdk.world.Position;
import io.skyfallsdk.world.block.Block;
import io.skyfallsdk.world.block.entity.meta.BlockEntityMetadata;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("rawtypes")
public interface BlockEntity<M extends BlockEntityMetadata> extends Block {

    @NotNull
    BlockEntityType getType();

    @NotNull
    Position getPosition();

    @NotNull
    M getMetadata();

    boolean shouldKeepPacked();

    void setKeepPacked(boolean keepPacked);
}
