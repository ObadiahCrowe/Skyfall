package io.skyfallsdk.mojang.response;

import java.util.UUID;

public class ResponseNameToUuid {

    private final UUID uuid;
    private final String name;
    private final boolean legacy;
    private final boolean demo;

    public ResponseNameToUuid(UUID uuid, String name, boolean legacy, boolean demo) {
        this.uuid = uuid;
        this.name = name;
        this.legacy = legacy;
        this.demo = demo;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public boolean isLegacy() {
        return this.legacy;
    }

    public boolean isDemo() {
        return this.demo;
    }

    @Override
    public String toString() {
        return "ResponseNameToUuid{" +
          "uuid=" + uuid +
          ", name='" + name + '\'' +
          ", legacy=" + legacy +
          ", demo=" + demo +
          '}';
    }
}
