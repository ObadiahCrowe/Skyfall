package io.skyfallsdk.net.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.skyfallsdk.Server;
import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.net.channel.in.PacketDecoder;
import io.skyfallsdk.net.channel.in.PacketSplitter;
import io.skyfallsdk.net.channel.out.PacketEncoder;
import io.skyfallsdk.net.channel.out.PacketPrepender;

public class ChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        SkyfallServer server = (SkyfallServer) Server.get();

        pipeline.addLast("splitter", new PacketSplitter(server));
        pipeline.addLast("decoder", new PacketDecoder());

        pipeline.addLast("prepender", new PacketPrepender());
        pipeline.addLast("encoder", new PacketEncoder(server.getConfig().isDebugEnabled()));
    }
}
