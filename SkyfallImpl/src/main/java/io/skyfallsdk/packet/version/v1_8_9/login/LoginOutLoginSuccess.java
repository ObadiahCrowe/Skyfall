package io.skyfallsdk.packet.version.v1_8_9.login;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class LoginOutLoginSuccess extends io.skyfallsdk.packet.login.LoginOutSuccess {

    public LoginOutLoginSuccess() {
        super(LoginOutLoginSuccess.class);
    }

    @Override
    public UUID getUuid() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public void write(ByteBuf buf) {

    }
}
