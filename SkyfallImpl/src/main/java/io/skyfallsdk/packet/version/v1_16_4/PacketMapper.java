package io.skyfallsdk.packet.version.v1_16_4;

import io.skyfallsdk.packet.PacketDestination;
import io.skyfallsdk.packet.PacketState;
import io.skyfallsdk.packet.version.NetPacketMapper;
import io.skyfallsdk.packet.version.v1_16_4.handshake.HandshakeIn;
import io.skyfallsdk.packet.version.v1_16_4.handshake.HandshakeInLegacy;
import io.skyfallsdk.packet.version.v1_16_4.status.StatusInPing;
import io.skyfallsdk.packet.version.v1_16_4.status.StatusInRequest;
import io.skyfallsdk.packet.version.v1_16_4.status.StatusOutPong;
import io.skyfallsdk.packet.version.v1_16_4.status.StatusOutResponse;
import io.skyfallsdk.protocol.ProtocolVersion;
import it.unimi.dsi.fastutil.ints.Int2IntMap;

public class PacketMapper extends NetPacketMapper {

    public PacketMapper() {
        super(ProtocolVersion.v1_16_4, io.skyfallsdk.packet.version.v1_16_3.PacketMapper.class);

        // Handshake
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
