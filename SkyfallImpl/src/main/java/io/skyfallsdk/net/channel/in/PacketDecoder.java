package io.skyfallsdk.net.channel.in;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.skyfallsdk.Server;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.packet.*;
import io.skyfallsdk.packet.version.NetPacketIn;
import io.skyfallsdk.protocol.ProtocolVersion;
import org.apache.logging.log4j.core.net.Protocol;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    private NetClient client;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.client = NetClient.get(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (this.client != null) {
            this.client.setState(PacketState.HANDSHAKE);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        if (buf.readableBytes() != 0) {
            int packetId = NetData.readVarInt(buf);

            System.out.println("caught packet id: 0x" + Integer.toHexString(packetId));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Server.get().getLogger().error(cause);
    }
}
