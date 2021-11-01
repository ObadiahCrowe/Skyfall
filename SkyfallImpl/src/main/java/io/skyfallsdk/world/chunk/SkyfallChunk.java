package io.skyfallsdk.world.chunk;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.nbt.tag.type.*;
import io.skyfallsdk.net.NetSerializable;
import io.skyfallsdk.world.SkyfallWorld;
import io.skyfallsdk.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class SkyfallChunk implements Chunk, NetSerializable {

    private final SkyfallWorld world;
    private final int x;
    private final int z;

    private final SkyfallChunkSection[] sections;

    public SkyfallChunk(SkyfallWorld world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;

        this.sections = new SkyfallChunkSection[0];
    }

    @SuppressWarnings("unchecked")
    public SkyfallChunk(@NotNull SkyfallWorld world, @NotNull TagCompound level) {
        this.world = world;

        int xPos = (int) level.get("xPos").getValue();
        int zPos = (int) level.get("zPos").getValue();
        String rawStatus = (String) level.get("Status").getValue();
        int[] biomeData = ((TagIntArray) level.get("Biomes")).getValue();
        List<TagCompound> sections = ((TagList<TagCompound>) level.get("Sections")).getValue();
        List<TagCompound> tileEntities = ((TagList<TagCompound>) level.get("TileEntities")).getValue();
        TagCompound heightMaps = (TagCompound) level.get("Heightmaps");
        long lastUpdate = (long) level.get("LastUpdate").getValue();
        List<TagCompound> liquidTicks = ((TagList<TagCompound>) level.get("LiquidTicks")).getValue();
        long inhabitedTime = (long) level.get("InhabitedTime").getValue();
        TagList<TagList<TagShort>> postProcessing = (TagList<TagList<TagShort>>) level.get("PostProcessing");
        List<TagCompound> tileTicks = ((TagList<TagCompound>) level.get("TileTicks")).getValue();
        TagCompound structures = (TagCompound) level.get("Structures");
        boolean isLightOn = ((TagByte) level.get("isLightOn")).getValue() == 1;

        this.x = xPos;
        this.z = zPos;
        this.sections = new SkyfallChunkSection[sections.size()];

        for (int i = 0; i < sections.size(); i++) {
            this.sections[i] = new SkyfallChunkSection(sections.get(i));
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

    public static long getChunkKey(int x, int z) {
        return ((long) x << 32) | (z & 0xFFFFFFFFL);
    }

    @Override
    public void write(@NotNull ByteBuf buf) {

    }

    @Override
    public void read(@NotNull ByteBuf buf) {

    }
}
