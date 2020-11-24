package io.skyfallsdk.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.type.TagCompound;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

public class NetData {

    public static UUID readUuid(ByteBuf buf) {
        return new UUID(buf.readLong(), buf.readLong());
    }

    public static void writeUuid(ByteBuf buf, UUID uuid) {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }

    public static int readVarInt(ByteBuf buf) {
        int numRead = 0;
        int result = 0;

        byte read;
        do {
            read = buf.readByte();

            result |= (read & 0x7F) << numRead++ * 7;

            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big.");
            }
        } while ((read & 0x80) == 0x80);

        return result;
    }

    public static void writeVarInt(ByteBuf buf, int value) {
        do {
            byte temp = (byte) (value & 0x7F);
            value >>>= 7;

            if (value != 0) {
                temp |= 0x80;
            }

            buf.writeByte(temp);
        } while (value != 0);
    }

    public static long readVarLong(ByteBuf buf) {
        int numRead = 0;
        int result = 0;

        byte read;
        do {
            read = buf.readByte();

            result |= (read & 0x7F) << numRead++ * 7;

            if (numRead > 10) {
                throw new RuntimeException("VarInt is too big.");
            }
        } while ((read & 0x80) == 0x80);

        return result;
    }

    public static void writeVarLong(ByteBuf buf, long value) {
        do {
            byte temp = (byte) (value & 0x7F);
            value >>>= 7;

            if (value != 0) {
                temp |= 0x80;
            }

            buf.writeByte(temp);
        } while (value != 0);
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

    public static void writeString(ByteBuf buf, String value) {
        writeVarInt(buf, value.length());
        buf.writeBytes(value.getBytes(StandardCharsets.UTF_8));
    }

    public static ChatComponent readChatComponent(ByteBuf buf) {
        return ChatComponent.fromJson(readString(buf));
    }

    public static void writeChatComponent(ByteBuf buf, ChatComponent component) {
        writeString(buf, component.toString());
    }

    public static NBTTag<?> readNBT(ByteBuf buf) {
        try {
            return new NBTInputStream(new ByteBufInputStream(buf)).readTag();
        } catch (IOException e) {
            throw new DecoderException("Could not decode NBT data from ByteBuf!", e);
        }
    }

    public static void writeNBT(ByteBuf buf, NBTTag<?> tag) {
        try {
            tag.write(new NBTOutputStream(new ByteBufOutputStream(buf)));
        } catch (IOException e) {
            throw new EncoderException("Could not encode NBT data to ByteBuf!", e);
        }
    }
}
