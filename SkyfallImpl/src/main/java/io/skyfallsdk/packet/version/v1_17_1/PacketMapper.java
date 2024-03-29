package io.skyfallsdk.packet.version.v1_17_1;

import io.skyfallsdk.packet.PacketDestination;
import io.skyfallsdk.packet.PacketState;
import io.skyfallsdk.packet.version.NetPacketMapper;
import io.skyfallsdk.packet.version.v1_17_1.handshake.HandshakeIn;
import io.skyfallsdk.packet.version.v1_17_1.handshake.HandshakeInLegacy;
import io.skyfallsdk.packet.version.v1_17_1.status.StatusInPing;
import io.skyfallsdk.packet.version.v1_17_1.status.StatusInRequest;
import io.skyfallsdk.packet.version.v1_17_1.status.StatusOutPong;
import io.skyfallsdk.packet.version.v1_17_1.status.StatusOutResponse;
import io.skyfallsdk.protocol.ProtocolVersion;
import it.unimi.dsi.fastutil.ints.Int2IntMap;

public class PacketMapper extends NetPacketMapper {

    public PacketMapper() {
        super(ProtocolVersion.v1_17_1, io.skyfallsdk.packet.version.v1_17.PacketMapper.class);

        this.register(HandshakeIn.class, PacketState.HANDSHAKE, PacketDestination.IN, 0x00);
        this.register(HandshakeInLegacy.class, PacketState.HANDSHAKE, PacketDestination.IN, 0xFE);

        // Status
        this.register(StatusInRequest.class, PacketState.STATUS, PacketDestination.IN, 0x00);
        this.register(StatusInPing.class, PacketState.STATUS, PacketDestination.IN, 0x01);
        this.register(StatusOutResponse.class, PacketState.STATUS, PacketDestination.OUT, 0x00);
        this.register(StatusOutPong.class, PacketState.STATUS, PacketDestination.OUT, 0x01);
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
