package io.skyfallsdk.packet.version.v1_16_4.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.net.NetData;

import java.util.UUID;

public class LoginOutSuccess extends io.skyfallsdk.packet.login.LoginOutSuccess {

    private final NetClient client;

    public LoginOutSuccess(NetClient client) {
        super(LoginOutSuccess.class);

        this.client = client;
    }

    @Override
    public UUID getUuid() {
        return this.client.getUuid();
    }

    @Override
    public String getUsername() {
        return this.client.getUsername();
    }

    @Override
    public void write(ByteBuf buf) {
        NetData.writeUuid(buf, this.client.getUuid());
        NetData.writeString(buf, this.client.getUsername());
    }
}
