package io.skyfallsdk.net;

import io.netty.channel.epoll.Epoll;
import io.skyfallsdk.Server;
import io.skyfallsdk.net.server.NetServerEpoll;
import io.skyfallsdk.net.server.NetServerNio;

public abstract class NetServer {

    private static NetServer instance;

    private final String address;
    private final int port;

    protected NetServer(String address, int port) {
        this.address = address;
        this.port = port;
    }

    protected String getAddress() {
        return this.address;
    }

    protected int getPort() {
        return this.port;
    }

    public abstract void init();

    public abstract void shutdown() throws InterruptedException;

    public static NetServer init(String address, int port) {
        if (instance != null) {
            throw new IllegalStateException("Network server is already initialised!");
        }

        boolean hasEpoll = Epoll.isAvailable();
        if (hasEpoll) {
            Server.get().getLogger().info("Epoll is available.. using.");
        }

        instance = hasEpoll ? new NetServerEpoll(address, port) : new NetServerNio(address, port);
        instance.init();

        return instance;
    }
}
