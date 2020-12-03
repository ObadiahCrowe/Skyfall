package io.skyfallsdk.packet.version.v1_16_4.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.packet.version.NetPacketIn;

public class LoginInPluginResponse extends NetPacketIn implements io.skyfallsdk.packet.login.LoginInPluginResponse {

    public LoginInPluginResponse() {
        super(LoginInPluginResponse.class);
    }

    @Override
    public int getMessageId() {
        return 0;
    }

    @Override
    public boolean wasSuccessful() {
        return false;
    }

    @Override
    public byte[] getData() {
        return new byte[0];
    }

    @Override
    public void read(ByteBuf buf, NetClient connection) {

    }
}
