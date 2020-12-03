package io.skyfallsdk.net.channel.out;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.skyfallsdk.Server;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.packet.exception.PacketException;
import io.skyfallsdk.packet.version.NetPacketOut;

public class PacketEncoder extends MessageToByteEncoder<NetPacketOut> {

    private final boolean isDebugEnabled;

    public PacketEncoder(boolean isDebugEnabled) {
        this.isDebugEnabled = isDebugEnabled;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NetPacketOut packet, ByteBuf buf) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();

        try {
            if (this.isDebugEnabled) {
                Server.get().getLogger().debug("Preparing to send packet: " + packet.getClass().getCanonicalName() + " with packet id: 0x" + Integer.toHexString(packet.getId()));
            }

            NetData.writeVarInt(buffer, packet.getId());
            packet.write(buffer);

            ByteBuf out = ctx.alloc().buffer();
            try {
                NetData.writeVarInt(out, buffer.readableBytes());
                out.writeBytes(buffer);

                buf.writeBytes(out);
            } finally {
                out.release();
            }
        } catch (Exception e) {
            throw new PacketException(packet, e.getMessage());
        } finally {
            buffer.release();
        }
    }
}
