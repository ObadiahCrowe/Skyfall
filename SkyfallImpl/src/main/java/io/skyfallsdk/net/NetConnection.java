package io.skyfallsdk.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.skyfallsdk.protocol.ProtocolVersion;

public class NetConnection {

    private final Channel channel;
    private final ProtocolVersion version;

    public NetConnection(ChannelHandlerContext context, ProtocolVersion version) {
        this.channel = context.channel();
        this.version = version;
    }

    public ProtocolVersion getVersion() {
        return this.version;
    }
}
