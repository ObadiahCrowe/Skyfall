package io.skyfallsdk.nbt.tag;

import com.google.common.base.Charsets;
import io.skyfallsdk.util.Pair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Quick utility methods for reading/writing tag headers for NBT file format
 */
public class NBTHeader {

    /**
     * Reads the {@link NBTTagType type} and name from the head of the stream
     *
     * @param stream The {@link DataInputStream input stream}
     * @return A {@link Pair} containing the {@link NBTTagType type}, and the name. Name can be {@code null} if the
     * type is {@link NBTTagType#END}.
     * @throws IOException If the stream has been closed and the contained input stream does not support reading after
     *                     close, or another I/O error occurs.
     */
    public static Pair<NBTTagType, String> readHeader(DataInputStream stream) throws IOException {
        int typeId = stream.readByte();
        NBTTagType type = NBTTagType.fromTypeId(typeId);

        if (type == NBTTagType.END) {
            return new Pair<>(type, null);
        }

        int strLength = stream.readShort();
        byte[] strBytes = new byte[strLength];
        String name = new String(strBytes, Charsets.UTF_8);
        return new Pair<>(type, name);
    }

    /**
     * Writes the {@link NBTTagType type} and name of the tag to the stream
     *
     * @param stream The {@link DataOutputStream output stream}
     * @param type   The {@link NBTTagType type}
     * @param name   The name of the tag
     * @throws IOException If an I/O error occurs
     */
    public static void writeHeader(DataOutputStream stream, NBTTagType type, String name) throws IOException {
        stream.writeByte(type.getTypeId());

        if (name == null) {
            if (type == NBTTagType.END) {
                return;
            }

            throw new IllegalArgumentException("Missing name!");
        }

        byte[] strBytes = name.getBytes(Charsets.UTF_8);
        stream.writeShort(strBytes.length);
        stream.write(strBytes);
    }
}
