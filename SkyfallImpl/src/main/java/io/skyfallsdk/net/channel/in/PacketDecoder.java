package io.skyfallsdk.net.channel.in;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.skyfallsdk.Server;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.packet.*;
import io.skyfallsdk.packet.exception.PacketException;
import io.skyfallsdk.packet.version.NetPacketIn;
import io.skyfallsdk.protocol.ProtocolVersion;
import org.apache.logging.log4j.core.net.Protocol;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    private final boolean isDebugEnabled;

    private NetClient client;

    public PacketDecoder(boolean isDebugEnabled) {
        this.isDebugEnabled = isDebugEnabled;
    }

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
        if (buf.readableBytes() == 0) {
            return;
        }

        int packetId = NetData.readVarInt(buf);
        NetPacketIn packet = PacketRegistry.newInstance(this.client.getVersion() == ProtocolVersion.UNKNOWN ? ProtocolVersion.values()[0] :
          this.client.getVersion(), this.client.getState(), PacketDestination.IN, packetId);

        if (packet == null) {
            Server.get().getLogger().error("Could not construct packet with id: 0x" + Integer.toHexString(packetId) + "!");
            return;
        }

        if (buf.readableBytes() == 0) {
            return;
        }

        if (this.isDebugEnabled) {
            Server.get().getLogger().debug("Preparing to read packet: " + packet.getClass().getCanonicalName() + " with packet id: 0x" + Integer.toHexString(packetId));
        }

        packet.read(buf, this.client);

        if (buf.readableBytes() > 0) {
            throw new PacketException(packetId, "Received packet: " + packet.getClass().getCanonicalName() + ", was larger than expected. Received " + buf.readableBytes() + " extra bytes.");
        } else {
            list.add(packet);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Server.get().getLogger().error(cause);
    }
}
