package io.skyfallsdk.nbt.stream;

import io.skyfallsdk.nbt.tag.NBTHeader;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;
import io.skyfallsdk.util.Pair;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * An input stream which can read NBT files
 */
public class NBTInputStream extends DataInputStream {

    /**
     * Creates a new input stream reader. Make sure that this is not compressed, and that if the file you are reading
     * is compressed, wrap your input stream in a {@link GZIPInputStream}
     *
     * @param in The uncompressed input stream
     */
    public NBTInputStream(InputStream in) {
        super(in);
    }

    /**
     * Reads the next tag
     *
     * @return The read tag
     * @throws IOException If an I/O error occurs
     */
    public NBTTag<?> readTag() throws IOException {
        Pair<NBTTagType, String> header = NBTHeader.readHeader(this);
        NBTTag<?> tag = header.getType1().newEmptyInstance(header.getType2());

        try {
            tag.read(this);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return tag;
        }

        return tag;
    }
}
