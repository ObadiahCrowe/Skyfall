package io.skyfallsdk.net.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new InDecoder());
        socketChannel.pipeline().addLast(new OutEncoder());
    }
}
