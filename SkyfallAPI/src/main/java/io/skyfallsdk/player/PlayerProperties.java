package io.skyfallsdk.player;

import org.jetbrains.annotations.Unmodifiable;

@Unmodifiable
public final class PlayerProperties {

    private final String name;

    private final String value;
    private final String signature;

    public PlayerProperties(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String getSignature() {
        return this.signature;
    }

    @Override
    public String toString() {
        return "PlayerProperties{" +
          "name='" + name + '\'' +
          ", value='" + value + '\'' +
          ", signature='" + signature + '\'' +
          '}';
    }
}
