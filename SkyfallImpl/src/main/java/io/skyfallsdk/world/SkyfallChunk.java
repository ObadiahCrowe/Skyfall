package io.skyfallsdk.world;

import io.skyfallsdk.Server;
import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.world.chunk.Chunk;
import io.skyfallsdk.world.chunk.ChunkSection;
import io.skyfallsdk.world.region.RegionFile;

import java.io.IOException;

public class SkyfallChunk implements Chunk {

    private final SkyfallWorld world;
    private final int x;
    private final int z;

    private final RegionFile file;

    public SkyfallChunk(SkyfallWorld world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;

        try {
            this.file = new RegionFile(x, z, this.getWorld().getDirectory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(TagCompound compound) {
        //
    }

    @Override
    public String getWorldName() {
        return this.world.getName();
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public Entity[] getEntities() {
        return new Entity[0];
    }

    @Override
    public ChunkSection[] getSections() {
        return new ChunkSection[0];
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getZ() {
        return this.z;
    }

    public RegionFile getRegionFile() {
        return this.file;
    }
}
