package io.skyfallsdk.world;

import io.skyfallsdk.Server;
import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.entity.SkyfallEntity;
import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.world.chunk.Chunk;
import io.skyfallsdk.world.chunk.ChunkSection;
import io.skyfallsdk.world.chunk.SkyfallChunkSection;
import io.skyfallsdk.world.region.RegionFile;

import java.io.IOException;
import java.util.Arrays;

public class SkyfallChunk implements Chunk {

    private final SkyfallWorld world;
    private final int x;
    private final int z;

    private final RegionFile file;
    private final SkyfallChunkSection[] sections;

    public SkyfallChunk(SkyfallWorld world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;

        try {
            this.file = new RegionFile(x, z, this.getWorld().getDirectory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.sections = new SkyfallChunkSection[0];
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
        return Arrays.stream(this.sections)
          .flatMap(section -> Arrays.stream(section.getEntities()))
          .toArray(Entity[]::new);
    }

    @Override
    public ChunkSection[] getSections() {
        return this.sections;
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
