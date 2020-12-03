package io.skyfallsdk.packet.version.v1_16_4.login;

import io.netty.buffer.ByteBuf;

public class LoginOutPluginRequest extends io.skyfallsdk.packet.login.LoginOutPluginRequest {

    public LoginOutPluginRequest() {
        super(LoginOutPluginRequest.class);
    }

    @Override
    public int getMessageId() {
        return 0;
    }

    @Override
    public String getChannel() {
        return null;
    }

    @Override
    public byte[] getData() {
        return new byte[0];
    }

    @Override
    public void write(ByteBuf buf) {

    }
}
