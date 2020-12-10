package io.skyfallsdk.event.server;

import io.skyfallsdk.event.Event;
import io.skyfallsdk.protocol.client.ClientInfo;
import io.skyfallsdk.util.PingResponse;

public class ServerPingEvent implements Event {

    private final ClientInfo info;

    private PingResponse response;

    public ServerPingEvent(ClientInfo info, PingResponse response) {
        this.info = info;

        this.response = response;
    }

    public ClientInfo getClient() {
        return this.info;
    }

    public PingResponse getResponse() {
        return this.response;
    }

    public void setResponse(PingResponse response) {
        this.response = response;
    }
}
