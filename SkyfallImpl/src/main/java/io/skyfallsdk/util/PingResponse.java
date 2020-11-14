package io.skyfallsdk.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.skyfallsdk.Server;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.protocol.ProtocolVersion;

public class PingResponse {

    private final ProtocolVersion version;

    private PingResponse(ProtocolVersion version) {
        this.version = version;
    }

    public String toJson() {
        JsonObject object = new JsonObject();
        JsonObject version = new JsonObject();

        version.addProperty("name", this.version.getName());
        version.addProperty("protocol", this.version.getVersion());

        JsonObject players = new JsonObject();

        players.addProperty("max", Server.get().getMaxPlayers());
        players.addProperty("online", Server.get().getPlayers().size());

        JsonArray sample = new JsonArray();

        players.add("sample", sample);

        JsonObject description = new JsonObject();

        description.addProperty("text", Server.get().getMotd());

        object.add("version", version);
        object.add("players", players);
        object.add("description", description);

        return object.toString();
    }

    public static PingResponse createResponse(NetClient connection) {
        return new PingResponse(connection.getVersion());
    }
}
