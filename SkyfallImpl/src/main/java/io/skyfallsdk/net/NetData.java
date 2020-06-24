package io.skyfallsdk.net;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class NetData {

    public static UUID readUuid(ByteBuf buf) {
        return new UUID(buf.readLong(), buf.readLong());
    }

    public static int readVarInt(ByteBuf buf) {
        int numRead = 0;
        int result = 0;

        byte read;
        do {
            read = buf.readByte();

            numRead |= (read & 127) << result++ * 7;
            if (result > 5) {
                throw new RuntimeException("VarInt is too big. ");
            }
        } while ((read & 128) == 128);

        return numRead;
    }
}
