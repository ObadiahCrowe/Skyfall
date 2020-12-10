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
                // TODO: 11/12/2020 Change this, this is solely test code until crypto and world loading is somewhat done
                connection.disconnect(ChatComponent.from("» rip u «").setColour(ChatColour.RED).addExtra(ChatComponent.from(" lol")
                  .setColour(HexColour.of(114, 152, 214))));
            }
        });


        Server.get().getMojangApi().getNameHistory(UUID.fromString("634e9b55-2a0a-4ac2-a05f-66bfe838bc84")).thenAccept(list -> {
            for (ResponseNameHistory hist : list) {
                System.out.println(hist.toString());
            }
        });
    }
}
