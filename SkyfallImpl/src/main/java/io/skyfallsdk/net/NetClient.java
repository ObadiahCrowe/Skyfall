package io.skyfallsdk.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.packet.Packet;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.protocol.client.ClientInfo;
import io.skyfallsdk.protocol.client.ClientType;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class NetClient implements ClientInfo {

    private final Channel channel;
    private final InetAddress address;
    private final ProtocolVersion version;

    public NetClient(ChannelHandlerContext context, ProtocolVersion version) {
        this.channel = context.channel();
        this.address = ((InetSocketAddress) this.channel.remoteAddress()).getAddress();
        this.version = version;
    }

    public ProtocolVersion getVersion() {
        return this.version;
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
    public void sendPacket(Packet packet) {
        //
    }

    @Override
    public void disconnect(ChatComponent reason) {

    }
}
