package io.skyfallsdk.net;

import io.netty.channel.epoll.Epoll;
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

        instance = Epoll.isAvailable() ? new NetServerEpoll(address, port) : new NetServerNio(address, port);

        return instance;
    }
}
