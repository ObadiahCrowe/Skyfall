package io.skyfallsdk.protocol.client;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.protocol.ProtocolVersion;

import java.net.InetAddress;
import java.util.UUID;

public interface ClientInfo {

    ProtocolVersion getVersion();

    InetAddress getAddress();

    ClientType getType();

    String getUsername();

    UUID getUuid();

    default void disconnect(String reason) {
        this.disconnect(ChatComponent.from(reason));
    }

    void disconnect(ChatComponent reason);
}
