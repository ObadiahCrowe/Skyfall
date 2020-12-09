package io.skyfallsdk.packet.version.v1_16_4.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetData;

public class LoginOutEncryptionRequest extends io.skyfallsdk.packet.login.LoginOutEncryptionRequest {

    private final String serverId;
    private final byte[] publicKey;
    private final byte[] verifyToken;

    public LoginOutEncryptionRequest(String serverId, byte[] publicKey, byte[] verifyToken) {
        super(LoginOutEncryptionRequest.class);

        this.serverId = serverId;
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }

    @Override
    public String getServerId() {
        return this.serverId;
    }

    @Override
    public int getPublicKeyLength() {
        return this.publicKey.length;
    }

    @Override
    public byte[] getPublicKey() {
        return this.publicKey;
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
    public void write(ByteBuf buf) {
        NetData.writeString(buf, this.serverId);
        NetData.writeVarInt(buf, this.publicKey.length);
        buf.writeBytes(this.publicKey);
        NetData.writeVarInt(buf, this.verifyToken.length);
        buf.writeBytes(this.verifyToken);
    }
}
