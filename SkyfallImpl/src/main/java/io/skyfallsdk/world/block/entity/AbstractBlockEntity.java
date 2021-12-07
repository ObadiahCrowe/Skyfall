package io.skyfallsdk.world.block.entity;

import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.world.block.SkyfallBlock;
import io.skyfallsdk.world.block.entity.meta.AbstractBlockEntityMetadata;
import io.skyfallsdk.world.block.entity.meta.BlockEntityMetadata;
import it.unimi.dsi.fastutil.ints.Int2ReferenceArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractBlockEntity<M extends BlockEntityMetadata<?>> extends SkyfallBlock implements BlockEntity<M> {

    private static final Int2ReferenceMap<Class<? extends AbstractBlockEntity<?>>> TYPE_TO_CLASS = new Int2ReferenceArrayMap<>();

    static {
        register(BlockEntityType.BANNER, SkyfallBlockEntityBanner.class);
        register(BlockEntityType.BARREL, SkyfallBlockEntityBarrel.class);
        register(BlockEntityType.DISPENSER, SkyfallBlockEntityDispenser.class);
    }

    private static void register(@NotNull BlockEntityType type, @NotNull Class<? extends AbstractBlockEntity<?>> implementation) {
        TYPE_TO_CLASS.put(type.ordinal(), implementation);
    }

    private final BlockEntityType type;
    private final M metadata;

    private boolean keepPacked;

    protected AbstractBlockEntity(@NotNull BlockEntityType type, @NotNull TagCompound entityTag) {
        this.type = type;
        this.metadata = this.parseEntityTag(entityTag);

        this.keepPacked = ((byte) entityTag.get("keepPacked").getValue()) == 1;
    }

    @Override
    public @NotNull BlockEntityType getType() {
        return this.type;
    }

    @NotNull
    @Override
    public M getMetadata() {
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

    @SuppressWarnings("unchecked")
    public static @Nullable <T extends AbstractBlockEntity<?>> T newInstance(@NotNull TagCompound entityTag) {
        System.out.println("reading entity: " + entityTag.get("id").getValue());

        BlockEntityType type = BlockEntityType.getByNamespaced((String) entityTag.get("id").getValue());
        if (type == null) {
            return null;
        }

        System.out.println("got type: " + type.name());

        Class<? extends AbstractBlockEntity<?>> entityClass = TYPE_TO_CLASS.get(type.ordinal());
        if (entityClass == null) {
            return null;
        }

        try {
            return (T) entityClass.getConstructor(BlockEntityType.class, TagCompound.class).newInstance(type, entityTag);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
