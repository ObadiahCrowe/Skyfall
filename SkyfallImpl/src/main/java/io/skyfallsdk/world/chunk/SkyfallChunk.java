package io.skyfallsdk.world.chunk;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.skyfallsdk.Server;
import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.entity.SkyfallEntity;
import io.skyfallsdk.nbt.stream.NBTSerializable;
import io.skyfallsdk.nbt.tag.type.*;
import io.skyfallsdk.net.NetSerializable;
import io.skyfallsdk.world.Biome;
import io.skyfallsdk.world.Position;
import io.skyfallsdk.world.SkyfallWorld;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.generate.WorldGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SkyfallChunk implements Chunk, NetSerializable {

    private final SkyfallWorld world;
    private final int x;
    private final int z;

    private ChunkStatus status;
    private final int[] biomes;

    private final SkyfallChunkSection[] sections;

    public SkyfallChunk(SkyfallWorld world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;

        this.status = ChunkStatus.EMPTY;
        this.biomes = new int[1024];

        this.sections = new SkyfallChunkSection[0];
    }

    @SuppressWarnings("unchecked")
    public SkyfallChunk(@NotNull SkyfallWorld world, @NotNull TagCompound level) {
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

/*        System.out.println("Sections: " + sections.toString());
        System.out.println("Tile Entities: " + tileEntities.toString());
        System.out.println("Heightmap: " + heightMaps.toString());
        System.out.println("Last Update: " + lastUpdate);
        System.out.println("Liquid Ticks: " + liquidTicks.toString());
        System.out.println("Inhabited time: " + inhabitedTime);
        System.out.println("Post Processing: " + postProcessing.toString());
        System.out.println("Tile Ticks: " + tileTicks.toString());
        System.out.println("Structures: " + structures.toString());
        System.out.println("Light on: " + isLightOn);*/

        this.world = world;
        this.x = xPos;
        this.z = zPos;

        this.status = ChunkStatus.getByRaw(rawStatus);
        this.biomes = new int[1024];
        System.arraycopy(biomeData, 0, this.biomes, 0, biomeData.length);

        this.sections = new SkyfallChunkSection[sections.size()];

        for (int i = 0; i < sections.size(); i++) {
            this.sections[i] = new SkyfallChunkSection(sections.get(i));
        }
    }

    public @NotNull CompletableFuture<@NotNull Chunk> generate(@NotNull WorldGenerator generator) {
        return null;
    }

    public @NotNull Biome getBiomeAt(int x, int y, int z) {
        //Biome.getById(biomeData[0])
        return null;
    }

    public void setBiomeAt(int x, int y, int z, @NotNull Biome biome) {
        //
    }

    public @NotNull ChunkStatus getStatus() {
        return this.status;
    }

    public void setChunkStatus(@NotNull ChunkStatus status) {
        this.status = status;
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
        return new SkyfallEntity[0];
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
