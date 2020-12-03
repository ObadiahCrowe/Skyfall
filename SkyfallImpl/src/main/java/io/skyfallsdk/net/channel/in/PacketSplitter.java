package io.skyfallsdk.net.channel.in;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.net.NetData;

import java.util.List;

public class PacketSplitter extends ByteToMessageDecoder {

    private final SkyfallServer server;

    public PacketSplitter(SkyfallServer server) {
        this.server = server;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        buf.markReaderIndex();

        for (int i = 0; i < 3; ++i) {
            if (!buf.isReadable()) {
                buf.resetReaderIndex();
                return;
            }

            final byte b = buf.readByte();
            if (b >= 0) {
                buf.resetReaderIndex();

                int packetSize = NetData.readVarInt(buf);
                if (packetSize >= this.server.getPerformanceConfig().getMaxPacketSize()) {
                    NetClient client = NetClient.get(ctx);
                    this.server.getLogger().warn("A client has attempted to surpass the packet size limit. " + (client == null ?
                      "At IP address: " + ctx.channel().remoteAddress().toString() : "Player: " + client.getAddress().toString())); // TODO: 02/12/2020 Switch to player
                    ctx.close();
                }

                if (buf.readableBytes() < packetSize) {
                    buf.resetReaderIndex();
                    return;
                }

                list.add(buf.readRetainedSlice(packetSize));
                return;
            }
        }

        throw new CorruptedFrameException("Length is wider than 21-bit");
    }
}
