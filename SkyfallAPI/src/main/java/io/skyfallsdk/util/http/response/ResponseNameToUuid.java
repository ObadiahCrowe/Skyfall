package io.skyfallsdk.util.http.response;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ResponseNameToUuid {

    private final UUID uuid;
    private final String name;
    private final boolean legacy;
    private final boolean demo;

    public ResponseNameToUuid(@NotNull UUID uuid, @NotNull String name) {
        this.uuid = uuid;
        this.name = name;
        this.legacy = false;
        this.demo = false;
    }

    public ResponseNameToUuid(@NotNull UUID uuid, @NotNull String name, boolean legacy, boolean demo) {
        this.uuid = uuid;
        this.name = name;
        this.legacy = legacy;
        this.demo = demo;
    }

    public @NotNull UUID getUuid() {
        return this.uuid;
    }

    public @NotNull String getName() {
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
