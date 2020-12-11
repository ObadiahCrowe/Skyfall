package io.skyfallsdk.packet.play;

import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;
import io.skyfallsdk.world.Direction;
import io.skyfallsdk.world.block.Painting;
import io.skyfallsdk.world.Position;

import java.util.UUID;

public abstract class PlayOutSpawnPainting extends NetPacketOut {

    public PlayOutSpawnPainting(Class<? extends PlayOutSpawnPainting> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return PlayOutSpawnLivingEntity.class;
    }

    public abstract int getEntityId();

    public abstract UUID getEntityUuid();

    public abstract Painting getPainting();

    public abstract Position getPosition();

    public abstract Direction getDirection();
}
