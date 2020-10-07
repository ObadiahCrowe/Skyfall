package io.skyfallsdk.packet.version.v1_8_9.handshake;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetConnection;
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
    public void read(ByteBuf buf, NetConnection connection) {
        this.protocolVersion = NetData.readVarInt(buf);
        this.serverAddress = NetData.readString(buf);
        this.port = buf.readShort();
        this.nextState = NetData.readVarInt(buf) == 1 ? PacketState.STATUS : PacketState.LOGIN;
    }

    @Override
    public int getProtocolVersionRaw() {
        return this.protocolVersion;
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
