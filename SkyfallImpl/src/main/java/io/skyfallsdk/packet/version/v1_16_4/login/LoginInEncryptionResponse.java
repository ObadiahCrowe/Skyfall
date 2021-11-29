package io.skyfallsdk.packet.version.v1_16_4.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.net.crypto.NetCrypt;
import io.skyfallsdk.packet.version.NetPacketIn;

import java.util.Arrays;

public class LoginInEncryptionResponse extends NetPacketIn implements io.skyfallsdk.packet.login.LoginInEncryptionResponse {

    private byte[] sharedSecret;
    private byte[] verifyToken;

    public LoginInEncryptionResponse() {
        super(LoginInEncryptionResponse.class);
    }

    @Override
    public int getSharedSecretLength() {
        return this.sharedSecret.length;
    }

    @Override
    public byte[] getSharedSecret() {
        return this.sharedSecret;
    }

    @Override
    public int getVerifyTokenLength() {
        return this.verifyToken.length;
    }

    @Override
    public byte[] getVerifyToken() {
        return this.verifyToken;
    }

    @Override
    public void read(ByteBuf buf, NetClient connection) {
        int secretSize = NetData.readVarInt(buf);
        this.sharedSecret = NetData.readByteArray(buf, secretSize);

        int verifySize = NetData.readVarInt(buf);
        this.verifyToken = NetData.readByteArray(buf, verifySize);


    }
}
