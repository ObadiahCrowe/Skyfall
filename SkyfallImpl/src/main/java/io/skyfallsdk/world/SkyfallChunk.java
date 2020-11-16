package io.skyfallsdk.world;

import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.world.chunk.Chunk;

public class SkyfallChunk implements Chunk {

    @Override
    public String getWorldName() {
        return null;
    }

    @Override
    public World getWorld() {
        return null;
    }

    @Override
    public Entity[] getEntities() {
        return new Entity[0];
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getZ() {
        return 0;
    }
}
