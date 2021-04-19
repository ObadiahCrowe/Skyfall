package io.skyfallsdk.net.channel.in;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.skyfallsdk.Server;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.chat.colour.ChatColour;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.packet.*;
import io.skyfallsdk.packet.exception.PacketException;
import io.skyfallsdk.packet.version.NetPacketIn;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.server.ServerState;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PacketDecoder extends ByteToMessageDecoder {

    private static final Map<NetClient, Long> THROTTLED_CONNECTIONS = Maps.newConcurrentMap();
    private static final ChatComponent DISCONNECT_THROTTLED = ChatComponent.from("Your connection has been throttled. Please wait before reconnecting.").setColour(ChatColour.RED);
    private static final ChatComponent DISCONNECT_SHUTDOWN = ChatComponent.from("The server is currently shutting down.").setColour(ChatColour.RED);

    private final boolean isDebugEnabled;
    private final int throttleThreshold;
    private final boolean shouldThrottleConnections;

    private NetClient client;

    public PacketDecoder(boolean isDebugEnabled, int throttleThreshold) {
        this.isDebugEnabled = isDebugEnabled;
        this.throttleThreshold = throttleThreshold;
        this.shouldThrottleConnections = this.throttleThreshold != -1;
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

        if (packet.getState() == PacketState.HANDSHAKE && this.shouldThrottleConnections && !this.client.getAddress().getHostAddress().equals("127.0.0.1")) {
            long currTime = System.currentTimeMillis();
            long time = THROTTLED_CONNECTIONS.compute(this.client, (c, t) -> Objects.requireNonNullElse(t, currTime));

            if ((currTime - time) < 0) {
                this.client.disconnect(DISCONNECT_THROTTLED);
                return;
            }
        }

        if (Server.get().getState() == ServerState.TERMINATING) {
            this.client.disconnect(DISCONNECT_SHUTDOWN);
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
