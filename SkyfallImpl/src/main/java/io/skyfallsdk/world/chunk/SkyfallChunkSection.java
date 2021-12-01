package io.skyfallsdk.world.chunk;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.skyfallsdk.Server;
import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.entity.SkyfallEntity;
import io.skyfallsdk.nbt.tag.type.TagByteArray;
import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.nbt.tag.type.TagList;
import io.skyfallsdk.nbt.tag.type.TagLongArray;
import io.skyfallsdk.net.NetSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkyfallChunkSection implements ChunkSection, NetSerializable {

    private final int indexY;
    private final byte[] blockLight;
    private final long[] blockStates;
    private final byte[] skyLight;

    private boolean isEmpty;

    @SuppressWarnings("unchecked")
    public SkyfallChunkSection(@NotNull TagCompound compound) {
        this.indexY = (byte) compound.get("Y").getValue();
        this.blockLight = new byte[2048];
        this.blockStates = new long[4096];
        this.skyLight = new byte[2048];

        boolean hasBlockLight = compound.containsKey("BlockLight");
        boolean hasBlockStates = compound.containsKey("BlockStates");
        boolean hasPalette = compound.containsKey("Palette");
        boolean hasSkyLight = compound.containsKey("SkyLight");

        if (hasBlockLight) {
            byte[] blockLight = ((TagByteArray) compound.get("BlockLight")).getValue();
            System.arraycopy(blockLight, 0, this.blockLight, 0, blockLight.length);
        }

        if (hasBlockStates) {
            long[] blockStates = ((TagLongArray) compound.get("BlockStates")).getValue();
            System.arraycopy(blockStates, 0, this.blockStates, 0, blockStates.length);
        }

        if (hasPalette) {
            List<TagCompound> palette = ((TagList<TagCompound>) compound.get("Palette")).getValue();

            // TODO: 30/11/2021
        }

        if (hasSkyLight) {
            byte[] skyLight = ((TagByteArray) compound.get("SkyLight")).getValue();
            System.arraycopy(skyLight, 0, this.skyLight, 0, skyLight.length);
        }

        this.isEmpty = !hasBlockLight && !hasBlockStates && !hasPalette && !hasSkyLight;
    }

    public void write(@NotNull TagCompound compound) {

    }

    public void read(@NotNull TagCompound compound) {

    }

    @Override
    public void write(@NotNull ByteBuf buf) {

    }

    @Override
    public void read(@NotNull ByteBuf buf) {

    }

    @Override
    public int getIndexY() {
        return this.indexY;
    }

    @Override
    public boolean isEmpty() {
        return this.isEmpty;
    }
}
