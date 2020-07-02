package io.skyfallsdk.net.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.skyfallsdk.Server;
import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.net.NetServer;
import io.skyfallsdk.net.channel.ChannelHandler;

public class NetServerEpoll extends NetServer {

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public NetServerEpoll(String address, int port) {
        super(address, port);

        int threads = ((SkyfallServer) Server.get()).getPerfConfig().getNettyThreads();

        this.bossGroup = new EpollEventLoopGroup(threads >> 2);
        this.workerGroup = new EpollEventLoopGroup(threads >> 2);
    }

    @Override
    public void init() {
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(this.bossGroup, this.workerGroup)
          .channel(EpollServerSocketChannel.class)
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
