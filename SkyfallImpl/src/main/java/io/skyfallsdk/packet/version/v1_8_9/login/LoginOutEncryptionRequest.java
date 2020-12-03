package io.skyfallsdk.packet.version.v1_8_9.login;

import io.netty.buffer.ByteBuf;

public class LoginOutEncryptionRequest extends io.skyfallsdk.packet.login.LoginOutEncryptionRequest {

    public LoginOutEncryptionRequest() {
        super(LoginOutEncryptionRequest.class);
    }

    @Override
    public void write(ByteBuf buf) {

    }

    @Override
    public String getServerId() {
        return null;
    }

    @Override
    public int getPublicKeyLength() {
        return 0;
    }

    @Override
    public byte[] getPublicKey() {
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
}
