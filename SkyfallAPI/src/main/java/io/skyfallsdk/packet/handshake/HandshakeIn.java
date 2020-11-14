package io.skyfallsdk.packet.handshake;

import io.skyfallsdk.packet.PacketIn;
import io.skyfallsdk.packet.PacketState;
import io.skyfallsdk.protocol.ProtocolVersion;

public interface HandshakeIn extends PacketIn {

    ProtocolVersion getProtocolVersion();

    String getServerAddress();

    short getPort();

    PacketState getNextState();
}
