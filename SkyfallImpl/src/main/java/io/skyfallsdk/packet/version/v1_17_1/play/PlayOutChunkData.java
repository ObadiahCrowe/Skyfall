package io.skyfallsdk.packet.version.v1_17_1.play;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.world.chunk.SkyfallChunk;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PlayOutChunkData extends io.skyfallsdk.packet.play.PlayOutChunkData {

    private final SkyfallChunk chunk;

    public PlayOutChunkData(@NotNull SkyfallChunk chunk) {
        super(PlayOutChunkData.class);

        this.chunk = chunk;
    }

    @Override
    public int getChunkX() {
        return this.chunk.getX();
    }

    @Override
    public int getChunkZ() {
        return this.chunk.getZ();
    }

    @Override
    public long[] getBitMask() {
        return new long[0];
    }

    @Override
    public TagCompound getHeightMaps() {
        return new TagCompound("");
    }

    @Override
    public int[] getBiomes() {
        return new int[0];
    }

    @Override
    public byte[] getData() {
        return new byte[0];
    }

    @Override
    public NBTTag<?>[] getBlockEntities() {
        return new NBTTag[0];
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(this.getChunkX());
        buf.writeInt(this.getChunkZ());

        NetData.writeVarInt(buf, this.getBitMask().length);
        for (int i = 0; i < this.getBitMask().length; i++) {
            buf.writeLong(this.getBitMask()[i]);
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); NBTOutputStream nbt = new NBTOutputStream(out)) {
            this.getHeightMaps().write(nbt);

            buf.writeBytes(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        NetData.writeVarInt(buf, this.getBiomes().length);
        for (int i = 0; i < this.getBiomes().length; i++) {
            NetData.writeVarInt(buf, this.getBiomes()[i]);
        }

        NetData.writeVarInt(buf, this.getData().length);
        for (int i = 0; i < this.getData().length; i++) {
            buf.writeByte(this.getData()[i]);
        }

        NetData.writeVarInt(buf, this.getBlockEntities().length);
        for (int i = 0; i < this.getBlockEntities().length; i++) {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream(); NBTOutputStream nbt = new NBTOutputStream(out)) {
                this.getBlockEntities()[i].write(nbt);

                buf.writeBytes(out.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
