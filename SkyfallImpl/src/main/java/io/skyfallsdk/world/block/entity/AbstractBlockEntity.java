package io.skyfallsdk.world.block.entity;

import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.world.block.SkyfallBlock;
import io.skyfallsdk.world.block.entity.meta.AbstractBlockEntityMetadata;
import it.unimi.dsi.fastutil.ints.Int2ReferenceArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractBlockEntity<M extends AbstractBlockEntityMetadata<?>> extends SkyfallBlock implements BlockEntity<M> {

    private static final Int2ReferenceMap<Class<? extends AbstractBlockEntity<AbstractBlockEntityMetadata<?>>>> TYPE_TO_CLASS = new Int2ReferenceArrayMap<>();

    private final BlockEntityType type;
    private final M metadata;

    private boolean keepPacked;

    protected AbstractBlockEntity(@NotNull BlockEntityType type, @NotNull TagCompound entityTag) {
        this.type = type;
        this.metadata = this.parseEntityTag(entityTag);

        this.keepPacked = (boolean) entityTag.get("keepPacked").getValue();
    }

    @Override
    public @NotNull BlockEntityType getType() {
        return this.type;
    }

    @Override
    public @NotNull M getMetadata() {
        return this.metadata;
    }

    @Override
    public boolean shouldKeepPacked() {
        return this.keepPacked;
    }

    @Override
    public void setKeepPacked(boolean keepPacked) {
        this.keepPacked = keepPacked;
    }

    protected abstract M parseEntityTag(@NotNull TagCompound entityTag);

    public static @Nullable AbstractBlockEntity<AbstractBlockEntityMetadata<?>> newInstance(@NotNull TagCompound entityTag) {
        BlockEntityType type = BlockEntityType.getByNamespaced((String) entityTag.get("id").getValue());
        if (type == null) {
            return null;
        }

        Class<? extends AbstractBlockEntity<AbstractBlockEntityMetadata<?>>> entityClass = TYPE_TO_CLASS.get(type.ordinal());
        if (entityClass == null) {
            return null;
        }

        try {
            return entityClass.getConstructor(BlockEntityType.class, TagCompound.class).newInstance(type, entityTag);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
