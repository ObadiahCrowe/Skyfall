package io.skyfallsdk.util.http.response;

import io.skyfallsdk.player.PlayerProperties;

import java.util.UUID;

public class ResponseUuidToProperties {

    private final UUID uuid;
    private final String name;

    private final PlayerProperties properties;

    public ResponseUuidToProperties(UUID uuid, String name, PlayerProperties properties) {
        this.uuid = uuid;
        this.name = name;
        this.properties = properties;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public PlayerProperties getProperties() {
        return this.properties;
    }

    @Override
    public String toString() {
        return "ResponseUuidToProperties{" +
          "uuid='" + uuid + '\'' +
          ", name='" + name + '\'' +
          ", properties=" + properties +
          '}';
    }
}
