package io.skyfallsdk.net;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.packet.PacketState;
import io.skyfallsdk.packet.version.NetPacketOut;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.protocol.client.ClientInfo;
import io.skyfallsdk.protocol.client.ClientType;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;

public class NetClient implements ClientInfo {

    private static final Map<SocketAddress, NetClient> CLIENTS = Maps.newConcurrentMap();

    private final Channel channel;
    private final InetAddress address;

    private PacketState state;
    private ProtocolVersion version;

    private NetClient(ChannelHandlerContext context) {
        this.channel = context.channel();
        this.address = ((InetSocketAddress) this.channel.remoteAddress()).getAddress();

        this.state = PacketState.HANDSHAKE;
        this.version = ProtocolVersion.UNKNOWN;
    }

    public PacketState getState() {
        synchronized (this.address) {
            return this.state;
        }
    }

    public void setState(PacketState state) {
        synchronized (this.address) {
            this.state = state;
        }
    }

    public void setVersion(ProtocolVersion version) {
        synchronized (this.address) {
            this.version = version;
        }
    }

    public void sendPacket(NetPacketOut packet) {
        this.channel.writeAndFlush(packet);
    }

    @Override
    public ProtocolVersion getVersion() {
        synchronized (this.address) {
            return this.version;
        }
    }

    @Override
    public InetAddress getAddress() {
        return this.address;
    }

    @Override
    public ClientType getType() {
        return ClientType.UNKNOWN; // TODO: 13/11/2020 Implement at some point.
    }

    @Override
    public void disconnect(ChatComponent reason) {

    }

    public static NetClient get(ChannelHandlerContext ctx) {
        return CLIENTS.computeIfAbsent(ctx.channel().remoteAddress(), address -> new NetClient(ctx));
    }
}
