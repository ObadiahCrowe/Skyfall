package io.skyfallsdk.net.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.skyfallsdk.packet.PacketOut;

public class OutEncoder extends MessageToByteEncoder<PacketOut> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, PacketOut packetOut, ByteBuf byteBuf) throws Exception {

    }
}
