package io.skyfallsdk.packet;

import io.skyfallsdk.protocol.ProtocolVersion;

import java.util.Optional;

public interface MappablePacket extends Packet {

    Optional<? extends Packet> attemptMapTo(ProtocolVersion version);
}
