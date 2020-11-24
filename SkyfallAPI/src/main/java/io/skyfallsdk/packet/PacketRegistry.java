package io.skyfallsdk.packet;

import io.skyfallsdk.protocol.ProtocolVersion;

public interface PacketRegistry {

    int getId(Class<? extends Packet> packet);

    PacketState getState(Class<? extends Packet> packet);

    PacketDestination getDestination(Class<? extends Packet> packet);

    ProtocolVersion getProtocolVersion(Class<? extends Packet> packet);
}
