package io.skyfallsdk.net;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.Date;
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

    public static byte[] readByteArray(ByteBuf buf, int length) {
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);

        return bytes;
    }

    public static byte[] readByteArray(ByteBuf buf) {
        return readByteArray(buf, buf.readableBytes());
    }

    public static String readString(ByteBuf buf) {
        int length = readVarInt(buf);
        byte[] data = readByteArray(buf, length);

        return new String(data, StandardCharsets.UTF_8);
    }

    public static Date readDate(ByteBuf buf) {
        return new Date(buf.readLong());
    }
}
