package io.skyfallsdk.packet.version.v1_16_4.play;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.net.NetData;

public class PlayOutDisconnect extends io.skyfallsdk.packet.play.PlayOutDisconnect {

    private final ChatComponent reason;

    public PlayOutDisconnect(ChatComponent reason) {
        super(PlayOutDisconnect.class);

        this.reason = reason;
    }

    @Override
    public ChatComponent getReason() {
        return this.reason;
    }

    @Override
    public void write(ByteBuf buf) {
        NetData.writeChatComponent(buf, this.reason);
    }
}
