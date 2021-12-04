package io.skyfallsdk.world.block.entity.meta;

import io.skyfallsdk.world.block.entity.AbstractBlockEntity;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBlockEntityMetadata<E extends AbstractBlockEntity<?>> implements BlockEntityMetadata<E> {

    protected final E entity;

    protected AbstractBlockEntityMetadata(E entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull E getBlockEntity() {
        return this.entity;
    }
}
