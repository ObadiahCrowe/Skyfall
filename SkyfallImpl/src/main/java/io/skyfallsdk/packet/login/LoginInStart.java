package io.skyfallsdk.packet.login;

import io.skyfallsdk.packet.PacketIn;

public interface LoginInStart extends PacketIn {

    String getUsername();
}
