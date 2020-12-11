package io.skyfallsdk.packet.play;

import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;
import io.skyfallsdk.world.Position;

import java.util.UUID;

public abstract class PlayOutSpawnPlayer extends NetPacketOut {

    public PlayOutSpawnPlayer(Class<? extends PlayOutSpawnPlayer> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return PlayOutSpawnPlayer.class;
    }

    public abstract int getEntityId();

    public abstract UUID getUuid();

    public abstract Position getPosition();
}
