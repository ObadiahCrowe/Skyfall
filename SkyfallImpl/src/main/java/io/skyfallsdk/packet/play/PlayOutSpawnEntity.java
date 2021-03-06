package io.skyfallsdk.packet.play;

import io.skyfallsdk.entity.EntityType;
import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;
import io.skyfallsdk.world.Position;
import io.skyfallsdk.world.vector.Vector;

import java.util.UUID;

public abstract class PlayOutSpawnEntity extends NetPacketOut {

    public PlayOutSpawnEntity(Class<? extends PlayOutSpawnEntity> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return PlayOutSpawnEntity.class;
    }

    public abstract int getEntityId();

    public abstract UUID getEntityUuid();

    public abstract EntityType getEntityType();

    public abstract Position getPosition();

    public abstract int getData();

    public abstract Vector getVelocity();
}
