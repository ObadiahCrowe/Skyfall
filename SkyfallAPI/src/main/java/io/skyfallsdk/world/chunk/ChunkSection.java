package io.skyfallsdk.world.chunk;

import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.nbt.tag.type.TagCompound;

public interface ChunkSection {

    int getIndexY();

    boolean isEmpty();
}
