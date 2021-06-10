package io.skyfallsdk.world.chunk;

import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.entity.SkyfallEntity;
import io.skyfallsdk.nbt.tag.type.TagCompound;

public class SkyfallChunkSection implements ChunkSection {

    private final SkyfallEntity[] entities;

    public SkyfallChunkSection() {
        this.entities = new SkyfallEntity[0];
    }

    @Override
    public Entity[] getEntities() {
        return this.entities;
    }

    @Override
    public void write(TagCompound compound) {

    }

    @Override
    public void read(TagCompound compound) {

    }
}
