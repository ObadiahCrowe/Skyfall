package io.skyfallsdk.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.skyfallsdk.Server;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.chat.colour.ChatColour;
import io.skyfallsdk.chat.colour.HexColour;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.protocol.client.ClientInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class PingResponse {

    private final ProtocolVersion version;

    // TODO: 10/12/2020 work with event 
    private PingResponse(ProtocolVersion version) {
        this.version = version;
    }

    public String toJson() {
        JsonObject object = new JsonObject();
        JsonObject version = new JsonObject();

        version.addProperty("name", "Skyfall " + this.version.getName());
        version.addProperty("protocol", this.version.getVersion());

        JsonObject players = new JsonObject();

        players.addProperty("max", Server.get().getMaxPlayers());
        players.addProperty("online", Server.get().getPlayers().size());

        JsonArray sample = new JsonArray();

        players.add("sample", sample);
        object.add("version", version);
        object.add("players", players);
        object.add("description", ChatComponent.from("Powered by ").setColour(HexColour.of(114, 152, 214))
          .addExtra(ChatComponent.from("Skyfall.").setColour(HexColour.of(182, 208, 222))).toJson()); // TODO: 11/12/2020 Add fromJson method

        if (Server.get().getServerIcon() != null) {
            try {
                object.addProperty("favicon", "data:image/png;base64," + Base64.getEncoder().encodeToString(Files.readAllBytes(Server.get().getServerIcon())));
            } catch (IOException e) {
                Server.get().getLogger().error("An unexpected error occurred whilst parsing your server's favicon!", e);
            }
        }

        return object.toString();
    }

    public static PingResponse createResponse(ClientInfo connection) {
        return new PingResponse(connection.getVersion());
    }
}
