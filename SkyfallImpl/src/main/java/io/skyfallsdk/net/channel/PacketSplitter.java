package io.skyfallsdk.net.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.skyfallsdk.packet.version.NetPacketOut;

public class PacketSplitter extends MessageToByteEncoder<NetPacketOut> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NetPacketOut packetOut, ByteBuf byteBuf) throws Exception {

    }
}
