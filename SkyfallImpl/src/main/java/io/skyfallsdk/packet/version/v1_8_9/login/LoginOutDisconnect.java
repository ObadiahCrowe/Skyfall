package io.skyfallsdk.packet.version.v1_8_9.login;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.net.NetData;
import io.skyfallsdk.packet.PacketOut;

public class LoginOutDisconnect extends io.skyfallsdk.packet.login.LoginOutDisconnect {

    private final ChatComponent reason;

    public LoginOutDisconnect(ChatComponent reason) {
        super(LoginOutDisconnect.class);

        this.reason = reason;
    }

    @Override
    public ChatComponent getReason() {
        return this.reason;
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return io.skyfallsdk.packet.login.LoginOutDisconnect.class;
    }

    @Override
    public void write(ByteBuf buf) {
        NetData.writeString(buf, this.reason.toString());
    }
}
