package io.skyfallsdk.packet.play;

import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;
import io.skyfallsdk.protocol.ProtocolVersion;
import org.jetbrains.annotations.NotNull;

public abstract class PlayOutTimeUpdate extends NetPacketOut {

    public PlayOutTimeUpdate(Class<? extends PlayOutTimeUpdate> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return PlayOutTimeUpdate.class;
    }

    public abstract long getWorldAge();

    public abstract long getTimeOfDay();

    public static @NotNull PlayOutTimeUpdate make(@NotNull ProtocolVersion version, long worldAge, long timeOfDay) {
        return null;
    }
}
