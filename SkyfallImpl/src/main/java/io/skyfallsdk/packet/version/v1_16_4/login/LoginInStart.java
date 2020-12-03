package io.skyfallsdk.packet.version.v1_16_4.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.Server;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.packet.version.NetPacketIn;

public class LoginInStart extends NetPacketIn implements io.skyfallsdk.packet.login.LoginInStart {

    private String username;

    public LoginInStart() {
        super(LoginInStart.class);
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void read(ByteBuf buf, NetClient connection) {
        this.username = NetData.readString(buf);

        if (Server.get().isOnlineMode()) {
            //
            return;
        }
    }
}
