package io.skyfallsdk.util.http.response;

import java.util.UUID;

public class ResponseUuidAtTime {

    private final UUID id;
    private final String name;

    ResponseUuidAtTime(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getUuid() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "ResponseUuidAtTime{" +
          "uuid=" + id +
          ", name='" + name + '\'' +
          '}';
    }
}
