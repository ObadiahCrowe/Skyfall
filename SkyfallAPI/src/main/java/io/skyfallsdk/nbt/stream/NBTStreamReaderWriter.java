package io.skyfallsdk.nbt.stream;

import io.skyfallsdk.nbt.tag.NBTTag;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * A class that can read and write to/from a {@link DataInputStream}
 */
public interface NBTStreamReaderWriter {

    /**
     * Reads the {@link NBTTag tag} payload from the {@link NBTInputStream input stream}
     *
     * @param stream The {@link NBTInputStream input stream}
     * @throws IOException If an exception occurs. Depends on implementation.
     */
    void read(NBTInputStream stream) throws IOException;

    /**
     * Writes the {@link NBTTag tag} to the {@link NBTOutputStream output stream}
     *
     * @param stream The {@link NBTOutputStream stream}
     * @throws IOException If an exception occurs. Depends on implementation.
     */
    void write(NBTOutputStream stream) throws IOException;
}
