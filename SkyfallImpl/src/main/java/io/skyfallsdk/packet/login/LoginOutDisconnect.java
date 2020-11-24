package io.skyfallsdk.packet.login;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;

public abstract class LoginOutDisconnect extends NetPacketOut {

    public LoginOutDisconnect(Class<? extends LoginOutDisconnect> clazz) {
        super(clazz);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return LoginOutDisconnect.class;
    }

    public abstract ChatComponent getReason();
}
