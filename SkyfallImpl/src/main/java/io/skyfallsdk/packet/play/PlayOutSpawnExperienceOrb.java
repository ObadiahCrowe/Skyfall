package io.skyfallsdk.packet.play;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;
import io.skyfallsdk.world.Position;

public abstract class PlayOutSpawnExperienceOrb extends NetPacketOut {

    public PlayOutSpawnExperienceOrb(Class<? extends PlayOutSpawnExperienceOrb> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return PlayOutSpawnExperienceOrb.class;
    }

    public abstract int getEntityId();

    public abstract Position getPosition();

    public abstract short getAmount();
}
