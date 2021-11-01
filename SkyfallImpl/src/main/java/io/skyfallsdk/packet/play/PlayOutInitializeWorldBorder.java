package io.skyfallsdk.packet.play;

import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.world.Position;
import io.skyfallsdk.world.WorldBorder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class PlayOutInitializeWorldBorder extends NetPacketOut {

    public PlayOutInitializeWorldBorder(Class<? extends PlayOutInitializeWorldBorder> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return PlayOutInitializeWorldBorder.class;
    }

    public abstract @NotNull Position getCenter();

    public abstract double getOldDiameter();

    public abstract double getNewDiameter();

    public abstract long getDiameterChangeSpeed();

    public abstract int getPortalTeleportBoundary();

    public abstract int getWarningBlockSize();

    public abstract int getWarningTime();

    public static @Nullable PlayOutInitializeWorldBorder make(@NotNull ProtocolVersion version, @NotNull WorldBorder border) {
        return null;
    }
}
