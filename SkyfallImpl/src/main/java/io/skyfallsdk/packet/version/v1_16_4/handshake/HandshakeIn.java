package io.skyfallsdk.packet.version.v1_16_4.handshake;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.Server;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.packet.PacketState;
import io.skyfallsdk.packet.version.NetPacketIn;
import io.skyfallsdk.protocol.ProtocolVersion;

import java.util.stream.Collectors;

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
        this.nextState = NetData.readVarInt(buf) == 1 ? PacketState.STATUS : PacketState.LOGIN;

        connection.setState(this.nextState);
        if (!Server.get().getSupportedVersions().contains(this.getProtocolVersion())) {
            connection.disconnect("Invalid version. Please connect on one of the following versions:" +
              Server.get().getSupportedVersions().stream().map(ver ->  ", " + ver.getName()).collect(Collectors.joining()).replaceFirst(",", ""));
        }
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
