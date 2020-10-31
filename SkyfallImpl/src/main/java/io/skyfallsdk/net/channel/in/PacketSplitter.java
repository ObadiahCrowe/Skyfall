package io.skyfallsdk.net.channel.in;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.skyfallsdk.net.NetData;

import java.util.List;

public class PacketSplitter extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        buf.markReaderIndex();

        for (int i = 0; i < 3; ++i) {
            if (!buf.isReadable()) {
                buf.resetReaderIndex();
                return;
            }

            byte b = buf.readByte();

            if (b >= 0) {
                int packetSize = NetData.readVarInt(buf);

                if (buf.readableBytes() < packetSize) {
                    buf.resetReaderIndex();
                    return;
                }

                list.add(buf.readBytes(packetSize));
                return;
            }
        }

        throw new CorruptedFrameException("Length is wider than 21-bit");
    }
}
