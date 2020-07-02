package io.skyfallsdk.net.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.skyfallsdk.Server;
import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.net.NetServer;
import io.skyfallsdk.net.channel.ChannelHandler;

public class NetServerNio extends NetServer {

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public NetServerNio(String address, int port) {
        super(address, port);

        int threads = ((SkyfallServer) Server.get()).getPerfConfig().getNettyThreads();

        this.bossGroup = new NioEventLoopGroup(threads >> 2);
        this.workerGroup = new NioEventLoopGroup(threads >> 2);
    }

    @Override
    public void init() {
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(this.bossGroup, this.workerGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new ChannelHandler())
          .option(ChannelOption.SO_BACKLOG, 128)
          .option(ChannelOption.TCP_NODELAY, true)
          .childOption(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.bind(this.getAddress(), this.getPort());
    }

    @Override
    public void shutdown() {
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }
}
