package io.skyfallsdk.world.chunk;

import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.world.World;

public interface Chunk {

    String getWorldName();

    World getWorld();

    Entity[] getEntities();

    ChunkSection[] getSections();

    int getX();

    int getZ();
}
