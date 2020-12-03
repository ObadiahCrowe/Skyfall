package io.skyfallsdk.net.channel.out;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.packet.version.NetPacketOut;

public class PacketPrepender extends MessageToByteEncoder<NetPacketOut> {

    @Override
    protected void encode(ChannelHandlerContext ctx, NetPacketOut packet, ByteBuf buf) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();

        packet.write(buffer);

        int packetSize = buffer.readableBytes();
        int headerSize = NetData.getVarIntSize(packetSize);

        if (headerSize > 3) {
            throw new IllegalStateException("Cannot fit " + headerSize + " into 3.");
        }

        buf.ensureWritable(packetSize + headerSize);
        NetData.writeVarInt(buf, packetSize);
        buf.writeBytes(buffer, buffer.readerIndex(), packetSize);
    }
}
