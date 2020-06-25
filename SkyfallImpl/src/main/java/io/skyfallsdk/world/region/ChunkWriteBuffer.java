package io.skyfallsdk.world.region;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A buffer for writing a chunk to a byte array.
 */
public class ChunkWriteBuffer extends ByteArrayOutputStream {

    private final RegionFile file;
    private final int x;
    private final int z;

    /**
     * Creates a new chunk write buffer
     *
     * @param file The {@link RegionFile file} to write to
     * @param x    The x coordinate of the chunk
     * @param z    The z coordinate of the chunk
     */
    ChunkWriteBuffer(RegionFile file, int x, int z) {
        super(4096); // Init to 4KB
        this.file = file;
        this.x = x;
        this.z = z;
    }

    /**
     * @return The {@link RegionFile file} we are writing to
     */
    public RegionFile getFile() {
        return this.file;
    }

    /**
     * @return The X coordinate of this chunk
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return The Z coordinate of this chunk
     */
    public int getZ() {
        return this.z;
    }

    @Override
    public void close() throws IOException {
        this.file.writeChunk(this.getX(), this.getZ(), this.buf, this.count);

        super.close();
    }
}
