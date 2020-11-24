package io.skyfallsdk.net.server;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
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

    private ChannelFuture future;

    public NetServerEpoll(String address, int port) {
        super(address, port);

        this.bossGroup = new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Boss #%d")
          .setUncaughtExceptionHandler((t, e) -> Server.get().getLogger().error(e)).setDaemon(false).build());
        this.workerGroup = new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Worker #%d")
          .setUncaughtExceptionHandler((t, e) -> Server.get().getLogger().error(e)).setDaemon(false).build());
    }

    @Override
    public void init() {
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(this.bossGroup, this.workerGroup)
          .channel(EpollServerSocketChannel.class)
          .childHandler(new ChannelHandler())
          .option(ChannelOption.SO_BACKLOG, 128)
          .childOption(ChannelOption.SO_KEEPALIVE, true);

        this.future = bootstrap.bind(this.getAddress(), this.getPort());
    }

    @Override
    public void shutdown() throws InterruptedException {
        this.future.channel().closeFuture();

        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }
}
