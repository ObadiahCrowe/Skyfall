package io.skyfallsdk.packet.login;

import io.skyfallsdk.packet.PacketIn;

public interface LoginInPluginResponse extends PacketIn {

    int getMessageId();

    boolean wasSuccessful();

    byte[] getData();
}
