package io.skyfallsdk.packet.version.v1_11_1;

import io.skyfallsdk.packet.version.NetPacketMapper;
import io.skyfallsdk.protocol.ProtocolVersion;
import it.unimi.dsi.fastutil.ints.Int2IntMap;

public class PacketMapper extends NetPacketMapper {

    public PacketMapper() {
        super(ProtocolVersion.v1_11_1, io.skyfallsdk.packet.version.v1_11.PacketMapper.class);
    }

    @Override
    public Int2IntMap getSubstanceMappings() {
        return null;
    }

    @Override
    public Int2IntMap getEntityIdMappings() {
        return null;
    }
}
