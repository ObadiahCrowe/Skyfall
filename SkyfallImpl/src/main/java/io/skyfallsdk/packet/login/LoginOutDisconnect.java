package io.skyfallsdk.packet.login;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;
import io.skyfallsdk.protocol.ProtocolVersion;

public abstract class LoginOutDisconnect extends NetPacketOut {

    public LoginOutDisconnect(Class<? extends LoginOutDisconnect> clazz) {
        super(clazz);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return LoginOutDisconnect.class;
    }

    public abstract ChatComponent getReason();

    // TODO: 08/12/2020 The rest.
    public static LoginOutDisconnect make(ProtocolVersion version, ChatComponent reason) {
        switch (version) {
            case v1_16_4:
                return new io.skyfallsdk.packet.version.v1_16_4.login.LoginOutDisconnect(reason);
        }

        return null;
    }
}
