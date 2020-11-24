package io.skyfallsdk.packet.version.v1_8_9.login;

import io.netty.buffer.ByteBuf;

public class LoginOutEncryptionRequest extends io.skyfallsdk.packet.login.LoginOutEncryptionRequest {

    public LoginOutEncryptionRequest() {
        super(LoginOutEncryptionRequest.class);
    }

    @Override
    public void write(ByteBuf buf) {

    }
}
