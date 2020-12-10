package io.skyfallsdk.util.http.response;

import java.util.UUID;

public class ResponseUuidAtTime {

    private final UUID uuid;
    private final String name;

    ResponseUuidAtTime(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "ResponseUuidAtTime{" +
          "uuid=" + uuid +
          ", name='" + name + '\'' +
          '}';
    }
}
