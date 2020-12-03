package io.skyfallsdk.packet.version.v1_16_4.login;

import io.netty.buffer.ByteBuf;

public class LoginOutSetCompression extends io.skyfallsdk.packet.login.LoginOutSetCompression {

    public LoginOutSetCompression() {
        super(LoginOutSetCompression.class);
    }

    @Override
    public int getThreshold() {
        return 0;
    }

    @Override
    public void write(ByteBuf buf) {

    }
}
