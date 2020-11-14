package io.skyfallsdk.protocol.client;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.protocol.ProtocolVersion;

import java.net.InetAddress;

public interface ClientInfo {

    ProtocolVersion getVersion();

    InetAddress getAddress();

    ClientType getType();

    void sendPacket(Packet packet);

    default void disconnect(String reason) {
        this.disconnect(ChatComponent.from(reason));
    }

    void disconnect(ChatComponent reason);
}
