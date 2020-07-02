package io.skyfallsdk.packet.version.v1_16_1;

import io.skyfallsdk.packet.version.NetPacketMapper;
import io.skyfallsdk.protocol.ProtocolVersion;

public class PacketMapper extends NetPacketMapper {

    public PacketMapper() {
        super(ProtocolVersion.v1_16_1, io.skyfallsdk.packet.version.v1_16.PacketMapper.class);
    }
}
