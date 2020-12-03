package io.skyfallsdk.packet.login;

import io.skyfallsdk.packet.PacketIn;

public interface LoginInEncryptionResponse extends PacketIn {

    int getSharedSecretLength();

    byte[] getSharedSecret();

    int getVerifyTokenLength();

    byte[] getVerifyToken();
}
