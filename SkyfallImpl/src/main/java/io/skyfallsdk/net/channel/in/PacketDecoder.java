package io.skyfallsdk.net.channel.in;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.skyfallsdk.net.NetData;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        if (buf.readableBytes() > 0) {
            int packetId = NetData.readVarInt(buf);

            System.out.println("caught packet id: 0x" + Integer.toHexString(packetId));
        }
    }
}
