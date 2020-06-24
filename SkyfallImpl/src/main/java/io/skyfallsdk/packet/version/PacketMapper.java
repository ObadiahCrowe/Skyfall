package io.skyfallsdk.packet.version;

import io.skyfallsdk.protocol.ProtocolVersion;

public abstract class PacketMapper {

    private final ProtocolVersion from;
    private final Class<? extends PacketMapper> to;

    public PacketMapper(ProtocolVersion from, Class<? extends PacketMapper> to) {
        this.from = from;
        this.to = to;
    }
}
