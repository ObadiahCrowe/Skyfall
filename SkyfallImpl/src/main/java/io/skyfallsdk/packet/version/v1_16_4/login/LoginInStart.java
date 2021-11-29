package io.skyfallsdk.packet.version.v1_16_4.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.Server;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.chat.colour.ChatColour;
import io.skyfallsdk.chat.colour.HexColour;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.net.crypto.NetCrypt;
import io.skyfallsdk.packet.version.NetPacketIn;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.player.PlayerProperties;
import io.skyfallsdk.player.SkyfallPlayer;
import io.skyfallsdk.util.http.response.ResponseNameHistory;

import java.util.UUID;

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

            if (Server.get().isOnlineMode()) {
                NetCrypt crypt = NetCrypt.get(connection);

                connection.sendPacket(new LoginOutEncryptionRequest(serverId, crypt.getKeyPair().getPublic().getEncoded(), crypt.getToken()));
            } else {
                LoginOutSuccess success = new LoginOutSuccess(connection);

                connection.sendPacket(success).addListener(future -> {
                    Server.get().getMojangApi().getPlayerProperties(success.getUuid()).thenAcceptAsync(properties -> {
                        SkyfallPlayer player = new SkyfallPlayer(connection, properties == null ? new PlayerProperties("textures", "", "") : properties);

                        player.spawn();
                    });
                });
            }
        });
    }
}
