package io.skyfallsdk.packet.version.v1_8_9;

import io.skyfallsdk.packet.PacketRegistry;
import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketDestination;
import io.skyfallsdk.packet.PacketState;
import io.skyfallsdk.packet.version.NetPacketMapper;
import io.skyfallsdk.packet.version.v1_8_9.handshake.HandshakeIn;
import io.skyfallsdk.packet.version.v1_8_9.handshake.HandshakeInLegacy;
import io.skyfallsdk.packet.version.v1_8_9.login.*;
import io.skyfallsdk.packet.version.v1_8_9.status.StatusInPing;
import io.skyfallsdk.packet.version.v1_8_9.status.StatusInRequest;
import io.skyfallsdk.packet.version.v1_8_9.status.StatusOutPong;
import io.skyfallsdk.packet.version.v1_8_9.status.StatusOutResponse;
import io.skyfallsdk.protocol.ProtocolVersion;
import it.unimi.dsi.fastutil.ints.Int2IntMap;

public class PacketMapper extends NetPacketMapper {

    public PacketMapper() {
        super(ProtocolVersion.v1_8_9, null);

        // Handshake
        this.register(HandshakeIn.class, PacketState.HANDSHAKE, PacketDestination.IN, 0x00);
        this.register(HandshakeInLegacy.class, PacketState.HANDSHAKE, PacketDestination.IN, 0xFE);

        // Status
        this.register(StatusInRequest.class, PacketState.STATUS, PacketDestination.IN, 0x00);
        this.register(StatusInPing.class, PacketState.STATUS, PacketDestination.IN, 0x01);
        this.register(StatusOutResponse.class, PacketState.STATUS, PacketDestination.OUT, 0x00);
        this.register(StatusOutPong.class, PacketState.STATUS, PacketDestination.OUT, 0x01);

        // Login
        this.register(LoginInStart.class, PacketState.LOGIN, PacketDestination.IN, 0x00);
        this.register(LoginInEncryptionResponse.class, PacketState.LOGIN, PacketDestination.IN, 0x01);
        this.register(LoginOutDisconnect.class, PacketState.LOGIN, PacketDestination.OUT, 0x00);
        this.register(LoginOutEncryptionRequest.class, PacketState.LOGIN, PacketDestination.OUT, 0x01);
        this.register(LoginOutLoginSuccess.class, PacketState.LOGIN, PacketDestination.OUT, 0x02);
        this.register(LoginOutSetCompression.class, PacketState.LOGIN, PacketDestination.OUT, 0x03);

        // Play
        //
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
