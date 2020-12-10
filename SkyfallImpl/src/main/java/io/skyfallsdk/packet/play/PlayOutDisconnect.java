package io.skyfallsdk.packet.play;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;
import io.skyfallsdk.protocol.ProtocolVersion;

public abstract class PlayOutDisconnect extends NetPacketOut {

    public PlayOutDisconnect(Class<? extends PlayOutDisconnect> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return PlayOutDisconnect.class;
    }

    public abstract ChatComponent getReason();

    public static PlayOutDisconnect make(ProtocolVersion version, ChatComponent reason) {
        switch (version) {
            case v1_16_4:
                return new io.skyfallsdk.packet.version.v1_16_4.play.PlayOutDisconnect(reason);
        }

        return null;
    }
}
