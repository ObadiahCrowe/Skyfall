package io.skyfallsdk.packet.version;

import io.skyfallsdk.protocol.ProtocolVersion;
import it.unimi.dsi.fastutil.ints.Int2IntMap;

public abstract class NetPacketMapper {

    private final ProtocolVersion from;
    private final Class<? extends NetPacketMapper> to;

    public NetPacketMapper(ProtocolVersion from, Class<? extends NetPacketMapper> to) {
        this.from = from;
        this.to = to;
    }

    public abstract Int2IntMap getSubstanceMappings();

    public abstract Int2IntMap getEntityIdMappings();
}
