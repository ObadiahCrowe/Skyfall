package io.skyfallsdk.packet.login;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.version.NetPacketOut;

public abstract class LoginOutDisconnect extends NetPacketOut {

    public LoginOutDisconnect(Class<? extends LoginOutDisconnect> clazz) {
        super(clazz);
    }

    public abstract ChatComponent getReason();
}
