package io.skyfallsdk.nbt.file;

import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.type.TagCompound;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Utilities for reading and writing NBT files
 */
public final class NBTFile {

    /**
     * Reads a compressed file into a {@link TagCompound}
     *
     * @param path       The {@link Path path} of the file
     * @param compressed If the file should be compressed or not
     * @return The read file as a {@link TagCompound}
     * @throws IOException If an I/O error occurs
     */
    public static TagCompound read(Path path, boolean compressed) throws IOException {
        NBTInputStream nbtInputStream = newInputStream(path, compressed);
        TagCompound compound = (TagCompound) nbtInputStream.readTag();

        nbtInputStream.close();

        return compound;
    }

    /**
     * Writes the given {@link TagCompound} to the given file
     *
     * @param path       The {@link Path path} of the file
     * @param compressed If the file should be compressed or not
     * @param compound   The {@link TagCompound compound}
     * @throws IOException If an I/O error occurs
     */
    public static void write(Path path, boolean compressed, TagCompound compound) throws IOException {
        NBTOutputStream nbtOutputStream = newOutputStream(path, compressed);
        nbtOutputStream.writeTag(compound);

        nbtOutputStream.close();
    }

    /**
     * Creates a new {@link NBTInputStream} pointed to the given file
     *
     * @param path       The {@link Path path} of the file
     * @param compressed If the file should be compressed or not
     * @return The created {@link NBTInputStream}
     * @throws IOException If an I/O error occurs
     */
    public static NBTInputStream newInputStream(Path path, boolean compressed) throws IOException {
        InputStream stream = new BufferedInputStream(new FileInputStream(path.toFile()));
        if (compressed) {
            stream = new GZIPInputStream(stream);
        }

        return new NBTInputStream(stream);
    }

    /**
     * Creates a new {@link NBTOutputStream} pointed to the given file
     *
     * @param path       The {@link Path path} of the file
     * @param compressed If the file should be compressed or not
     * @return The created {@link NBTOutputStream}
     * @throws IOException If an I/O error occurs
     */
    public static NBTOutputStream newOutputStream(Path path, boolean compressed) throws IOException {
        OutputStream stream = new BufferedOutputStream(new FileOutputStream(path.toFile()));
        if (compressed) {
            stream = new GZIPOutputStream(stream);
        }

        return new NBTOutputStream(stream);
    }

    // Avoid unwanted instantiation
    private NBTFile() {
    }
}
