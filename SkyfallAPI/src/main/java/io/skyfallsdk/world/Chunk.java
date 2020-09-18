package io.skyfallsdk.world;

import io.skyfallsdk.entity.Entity;

public interface Chunk {

    String getWorldName();

    World getWorld();

    Entity[] getEntities();

    int getX();

    int getZ();
}
