package io.skyfallsdk.net;

import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

public interface NetSerializable {

    void write(@NotNull ByteBuf buf);

    void read(@NotNull ByteBuf buf);
}
