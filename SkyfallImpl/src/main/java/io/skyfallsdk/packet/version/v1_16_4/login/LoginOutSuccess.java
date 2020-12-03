package io.skyfallsdk.packet.version.v1_16_4.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetClient;

import java.util.UUID;

public class LoginOutSuccess extends io.skyfallsdk.packet.login.LoginOutSuccess {

    private final NetClient client;

    public LoginOutSuccess(NetClient client) {
        super(LoginOutSuccess.class);

        this.client = client;
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
