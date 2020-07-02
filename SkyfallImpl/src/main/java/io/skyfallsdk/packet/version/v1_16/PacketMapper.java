package io.skyfallsdk.packet.version.v1_16;

import io.skyfallsdk.packet.version.NetPacketMapper;
import io.skyfallsdk.protocol.ProtocolVersion;

public class PacketMapper extends NetPacketMapper {

    public PacketMapper() {
        super(ProtocolVersion.v1_16, io.skyfallsdk.packet.version.v1_15_2.PacketMapper.class);
    }
}
