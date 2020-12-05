package io.skyfallsdk.packet.play;

import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;

public abstract class PlayOutSpawnEntity extends NetPacketOut {

    public PlayOutSpawnEntity(Class<? extends Packet> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return PlayOutSpawnEntity.class;
    }
}
