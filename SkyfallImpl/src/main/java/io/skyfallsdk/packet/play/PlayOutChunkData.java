package io.skyfallsdk.packet.play;

import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;

public abstract class PlayOutChunkData extends NetPacketOut {

    public PlayOutChunkData(Class<? extends PlayOutChunkData> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return PlayOutChunkData.class;
    }

    public abstract int getChunkX();

    public abstract int getChunkZ();

    public abstract long[] getBitMask();

    public abstract TagCompound getHeightMaps();

    public abstract int[] getBiomes();

    public abstract byte[] getData();

    public abstract NBTTag<?>[] getBlockEntities();
}
