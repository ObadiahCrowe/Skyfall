package io.skyfallsdk.packet.version.v1_16_4.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.net.NetData;

public class LoginOutSetCompression extends io.skyfallsdk.packet.login.LoginOutSetCompression {

    private final int threshold;

    public LoginOutSetCompression(int threshold) {
        super(LoginOutSetCompression.class);

        this.threshold = threshold;
    }

    @Override
    public int getThreshold() {
        return this.threshold;
    }

    @Override
    public void write(ByteBuf buf) {
        NetData.writeVarInt(buf, this.threshold);
    }
}
