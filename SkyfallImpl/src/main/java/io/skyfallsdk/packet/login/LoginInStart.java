package io.skyfallsdk.packet.login;

import io.skyfallsdk.packet.PacketIn;

public interface LoginInStart extends PacketIn {

    static String serverId = "";

    String getUsername();
}
