package io.skyfallsdk.world.chunk;

import io.skyfallsdk.nbt.tag.type.TagCompound;

public interface ChunkSection {

    void write(TagCompound compound);

    void read(TagCompound compound);
}
