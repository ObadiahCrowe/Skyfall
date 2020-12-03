package io.skyfallsdk.packet.version.v1_16_4.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.packet.version.NetPacketIn;

public class LoginInEncryptionResponse extends NetPacketIn implements io.skyfallsdk.packet.login.LoginInEncryptionResponse {

    public LoginInEncryptionResponse() {
        super(LoginInEncryptionResponse.class);
    }

    @Override
    public int getSharedSecretLength() {
        return 0;
    }

    @Override
    public byte[] getSharedSecret() {
        return new byte[0];
    }

    @Override
    public int getVerifyTokenLength() {
        return 0;
    }

    @Override
    public byte[] getVerifyToken() {
        return new byte[0];
    }

    @Override
    public void read(ByteBuf buf, NetClient connection) {

    }
}
