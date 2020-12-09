package io.skyfallsdk.packet.version.v1_16_4.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.Server;
import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.ThreadPool;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.packet.version.NetPacketIn;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

        connection.setUsername(this.username);
        Server.get().getMojangApi().getUuid(this.username).thenAccept(uuid -> {
            connection.setUuid(uuid);

            System.out.println(connection.getUsername());
            System.out.println(connection.getUuid());

            if (Server.get().isOnlineMode()) {
                // TODO: 10/12/2020 this
            }
        });
    }
}
