package io.skyfallsdk.packet.version;

import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.protocol.ProtocolVersion;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;

import java.util.Optional;

public abstract class NetPacketMapper {

    /**
     * Represents a combined packet id mapping to the method to turn a packet into another.
     * Example:
     *  - Key: PacketPlayOutWindowClick 1.8 + PacketPlayOutWindowClick 1.16.3
     *  - Value: Mappable PacketPlayOutWindowClick for sending 1.8.8 packets to 1.16.3 clients.
     */
    public static final Int2ReferenceMap<Optional<Class<? extends Packet>>> PACKET_MAPPINGS = new Int2ReferenceOpenHashMap<>();

    private final ProtocolVersion from;
    private final Class<? extends NetPacketMapper> to;

    public NetPacketMapper(ProtocolVersion from, Class<? extends NetPacketMapper> to) {
        this.from = from;
        this.to = to;
    }

    public abstract Int2IntMap getSubstanceMappings();

    public abstract Int2IntMap getEntityIdMappings();
}
