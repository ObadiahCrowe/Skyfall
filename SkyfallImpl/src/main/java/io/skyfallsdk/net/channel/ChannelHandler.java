package io.skyfallsdk.net.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.skyfallsdk.Server;
import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.net.channel.in.PacketDecoder;
import io.skyfallsdk.net.channel.in.PacketSplitter;

public class ChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast("splitter", new PacketSplitter(((SkyfallServer) Server.get())));
        pipeline.addLast("decoder", new PacketDecoder());

/*        pipeline.addLast("prepender", null);
        pipeline.addLast("encoder", null);*/
    }
}
