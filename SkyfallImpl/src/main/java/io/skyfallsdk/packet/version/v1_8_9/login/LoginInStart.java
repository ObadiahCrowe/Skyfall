package io.skyfallsdk.packet.version.v1_8_9.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.packet.version.NetPacketIn;

public class LoginInStart extends NetPacketIn {

    public LoginInStart() {
        super(LoginInStart.class);
    }

    @Override
    public void read(ByteBuf buf, NetClient connection) {

    }
}
