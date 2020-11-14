package io.skyfallsdk.packet.login;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.version.NetPacketOut;

public abstract class LoginOutDisconnect extends NetPacketOut {

    public LoginOutDisconnect() {
        super(LoginOutDisconnect.class);
    }

    public abstract ChatComponent getReason();
}
