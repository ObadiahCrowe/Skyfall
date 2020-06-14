package io.skyfallsdk.nbt.stream;

import io.skyfallsdk.nbt.tag.NBTHeader;
import io.skyfallsdk.nbt.tag.NBTTag;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * An output stream which can write NBT tags to files
 */
public class NBTOutputStream extends DataOutputStream {

    /**
     * Creates a new NBT output stream. Make sure the output stream is wrapped in a {@link GZIPOutputStream} if you
     * desire compression. This class does not handle any form of compression on its own.
     *
     * @param out The output stream to write to
     */
    public NBTOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * Writes the tag to the stream
     *
     * @param tag The {@link NBTTag tag}
     * @throws IOException If an I/O error occurs
     */
    public void writeTag(NBTTag<?> tag) throws IOException {
        NBTHeader.writeHeader(this, tag.getType(), tag.getName());

        tag.write(this);
    }
}
