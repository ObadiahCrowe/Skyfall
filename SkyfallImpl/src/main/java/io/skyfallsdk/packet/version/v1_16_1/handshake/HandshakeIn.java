package io.skyfallsdk.packet.version.v1_16_1.handshake;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.packet.PacketState;
import io.skyfallsdk.packet.version.NetPacketIn;
import io.skyfallsdk.protocol.ProtocolVersion;

public class HandshakeIn extends NetPacketIn implements io.skyfallsdk.packet.handshake.HandshakeIn {

    private int protocolVersion;
    private String serverAddress;
    private short port;
    private PacketState nextState;

    public HandshakeIn() {
        super(HandshakeIn.class);
    }

    @Override
    public void read(ByteBuf buf, NetClient connection) {
        this.protocolVersion = NetData.readVarInt(buf);
        this.serverAddress = NetData.readString(buf);
        this.port = buf.readShort();
        this.nextState = PacketState.values()[NetData.readVarInt(buf)];
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return ProtocolVersion.getByVersion((short) this.protocolVersion);
    }

    @Override
    public String getServerAddress() {
        return this.serverAddress;
    }

    @Override
    public short getPort() {
        return this.port;
    }

    @Override
    public PacketState getNextState() {
        return this.nextState;
    }
}
