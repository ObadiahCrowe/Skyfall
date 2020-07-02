package io.skyfallsdk.packet.version.v1_15_2;

import io.skyfallsdk.packet.version.NetPacketMapper;
import io.skyfallsdk.protocol.ProtocolVersion;

public class PacketMapper extends NetPacketMapper {

    public PacketMapper(ProtocolVersion from, Class<? extends NetPacketMapper> to) {
        super(from, to);
    }
}
