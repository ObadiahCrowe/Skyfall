package io.skyfallsdk.packet.version;

import io.skyfallsdk.protocol.ProtocolVersion;

public abstract class NetPacketMapper {

    private final ProtocolVersion from;
    private final Class<? extends NetPacketMapper> to;

    public NetPacketMapper(ProtocolVersion from, Class<? extends NetPacketMapper> to) {
        this.from = from;
        this.to = to;
    }
}
