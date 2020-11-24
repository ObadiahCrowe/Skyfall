package io.skyfallsdk.packet.version.v1_8_9.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.packet.version.NetPacketIn;

public class LoginInEncryptionResponse extends NetPacketIn {

    public LoginInEncryptionResponse() {
        super(LoginInEncryptionResponse.class);
    }

    @Override
    public void read(ByteBuf buf, NetClient connection) {

    }
}
