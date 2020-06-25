package io.skyfallsdk.world.region;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class RegionFile {

    private static final int VERSION_GZIP = 1;
    private static final int VERSION_DEFLATE = 2;

    // We can use the power instead of 4096 to bitshift (x * 4096 = x << 12)
    private static final int SECTOR_BYTE_POWER = 12;
    private static final int SECTOR_BYTES = 1 << SECTOR_BYTE_POWER;
    // 1 int = 4 bytes (32 bit = 4 * 8 bit)
    private static final int SECTOR_INTS = SECTOR_BYTES >> 2;
    // 1MB / SECTOR_BYTES = 256
    private static final int SECTOR_LIMIT = 256; // Max amount of sectors

    // length + version = 5 bytes (int + byte)
    private static final int CHUNK_HEADER_SIZE = 5;
    // Empty sector to avoid constant instantiation
    private static final byte[] EMPTY_SECTOR = new byte[SECTOR_BYTES];

    // The folder all regions are stored in within the world folder
    private static final String REGION_FOLDER = "region";
    // The file name format for anvil format regions
    private static final String MCA_FORMAT = "r.%s.%s.mca";

    private final int regionX;
    private final int regionZ;
    private final Path path;
    private final int[] sizeOffsetTable;
    private final int[] chunkTimestamps;

    private boolean[] freeSectors;
    private RandomAccessFile file;
    private int sizeDelta;
    private long lastModified;

    public RegionFile(int regionX, int regionZ, Path worldPath) throws IOException {
        this.regionX = regionX;
        this.regionZ = regionZ;
        this.path = worldPath.resolve(REGION_FOLDER).resolve(String.format(MCA_FORMAT, regionX, regionZ));

        this.sizeOffsetTable = new int[SECTOR_INTS];
        this.chunkTimestamps = new int[SECTOR_INTS];

        File asFile = this.getPath().toFile();
        if (asFile.exists()) {
            this.lastModified = asFile.lastModified();
        }

        this.file = new RandomAccessFile(asFile, "rw");

        // Check if file is set up
        if (this.file.length() < SECTOR_BYTES) {
            // Multiply by 2 since we need to write both chunk offset and timestamp info
            int doubleSectorInts = SECTOR_INTS << 1;
            // Write to file
            for (int i = 0; i < doubleSectorInts; i++) {
                this.file.writeInt(0);
            }

            // Increment starting point delta
            this.sizeDelta += (SECTOR_BYTES << 1);
        }

        // Check if file size is multiple of 4kb (size & 4095 should be 0)
        long remainder = this.file.length() & 0xFFF;
        if (remainder != 0) { // Not much more than a simple speedup
            for (long i = 0; i < remainder; i++) {
                this.file.write((byte) 0);
            }
        }

        // Set up free sector mapping
        int sectorCount = (int) (this.file.length() >> SECTOR_BYTE_POWER);
        this.freeSectors = new boolean[sectorCount];

        // Chunk offset + size table
        this.freeSectors[0] = false;
        // Chunk last modified table
        this.freeSectors[1] = false;
        // Set everything to open before we read and verify
        for (int i = 2; i < sectorCount; i++) {
            this.freeSectors[i] = true;
        }

        // Return to starting position
        this.file.seek(0);

        // Read size + offset table
        for (int i = 0; i < SECTOR_INTS; i++) {
            int sizeOffset = this.file.readInt();
            this.sizeOffsetTable[i] = sizeOffset;

            int offset = sizeOffset >> 8; // Last byte is the size
            int size = sizeOffset & 0xFF;

            // Verify that we are within the range of the map for already created sectors
            if (sizeOffset == 0 || offset + size > this.freeSectors.length) {
                continue;
            }

            // Set all sectors as taken
            for (int sectorNum = 0; sectorNum < size; sectorNum++) {
                this.freeSectors[offset + sectorNum] = false;
            }
        }

        // Read last modified table
        for (int i = 0; i < SECTOR_INTS; i++) {
            int lastModified = this.file.readInt();
            this.chunkTimestamps[i] = lastModified;
        }
    }

    /**
     * @return The X coordinate of this region
     */
    public int getRegionX() {
        return this.regionX;
    }

    /**
     * @return The Z coordinate of this region
     */
    public int getRegionZ() {
        return this.regionZ;
    }

    /**
     * @return The path of this file
     */
    public Path getPath() {
        return this.path;
    }

    /**
     * @return The last time this file was modified
     */
    public synchronized long getLastModified() {
        return this.lastModified;
    }

    /**
     * Checks when a chunk was last modified, in seconds
     *
     * @param x The X coordinate of the chunk. Does not have to be rounded to be between 0 and 31, but it is
     *          recommended that you adopt this approach
     * @param z The Z coordinate of the chunk. Does not have to be rounded to be between 0 and 31, but it is
     *          recommended that you adopt this approach
     * @return The time the chunk was last modified, in seconds
     */
    public synchronized int getLastModified(int x, int z) {
        return this.chunkTimestamps[this.getChunkIndex(x, z)];
    }

    /**
     * @return The size change of this file since it was last checked
     */
    public synchronized int getSizeDelta() {
        int returnValue = this.sizeDelta;
        this.sizeDelta = 0;
        return returnValue;
    }

    /**
     * Reads the chunk data for the given chunk coordinates out of this region file
     *
     * @param x The X coordinate of the chunk. Does not have to be rounded to be between 0 and 31, but it is recommended
     *          that you adopt this approach
     * @param z The Z coordinate of the chunk. Does not have to be rounded to be between 0 and 31, but it is recommended
     *          that you adopt this approach
     * @return The {@link InputStream input stream} created from the read data
     * @throws IOException If an I/O error occurs
     */
    public synchronized InputStream readChunk(int x, int z) throws IOException {
        int offsetSize = this.getChunkOffsetSize(x, z);
        if (offsetSize == 0) {
            return null;
        }

        int offset = offsetSize >> 8;
        int size = offsetSize & 0xFF;

        if (offset + size > this.freeSectors.length) {
            return null;
        }

        this.file.seek(offset << SECTOR_BYTE_POWER);
        int length = this.file.readInt();

        if (length > (size << SECTOR_BYTE_POWER)) {
            return null;
        }

        byte version = this.file.readByte();

        byte[] data = new byte[length - 1];
        this.file.read(data);

        InputStream stream = new ByteArrayInputStream(data);

        if (version == VERSION_GZIP) {
            stream = new GZIPInputStream(stream);
        } else if (version == VERSION_DEFLATE) {
            stream = new InflaterInputStream(stream);
        } else {
            // Unknown version
            return null;
        }

        // Default NMS impl wraps in DataInputStream, but NBTInputStream handles this
        return stream;
    }

    /**
     * Opens an output stream for writing a chunk. Allows for multithreading by not locking this entire file while
     * serializing the chunk
     *
     * @param x The X coordinate of the chunk. Does not have to be rounded to be between 0 and 31, but it is
     *          recommended that you adopt this approach
     * @param z The Z coordinate of the chunk. Does not have to be rounded to be between 0 and 31, but it is
     *          recommended that you adopt this approach
     * @return The output stream
     */
    public OutputStream writeChunk(int x, int z) {
        ChunkWriteBuffer buffer = new ChunkWriteBuffer(this, x, z);
        // Default NMS impl wraps in DataOutputStream, but NBTOutputStream handles this
        return new DeflaterOutputStream(buffer);
    }

    // Internals

    /**
     * Writes the given chunk data to the region file
     *
     * @param x      The X coordinate of the chunk. Does not have to be rounded to be between 0 and 31, but it is
     *               recommended that you adopt this approach
     * @param z      The Z coordinate of the chunk. Does not have to be rounded to be between 0 and 31, but it is
     *               recommended that you adopt this approach
     * @param data   The data to write
     * @param length The length of the data
     * @throws IOException If an I/O error occurs
     */
    protected synchronized void writeChunk(int x, int z, byte[] data, int length) throws IOException {
        int offsetSize = this.getChunkOffsetSize(x, z);
        int offset = offsetSize >> 8;
        int sizeAllocated = offsetSize & 0xFF;
        int sizeNeeded = ((length + CHUNK_HEADER_SIZE) >> SECTOR_BYTE_POWER) + 1;

        // Max size of a chunk is 1MB. This does cause a glitch with massive books, which we may
        // wish to look into patching.
        if (sizeNeeded >= SECTOR_LIMIT) {
            return;
        }

        // Check if the chunk already has enough space allocated to simply overwrite the previous sections
        if (offset != 0 && sizeAllocated == sizeNeeded) {
            this.writeSector(offset, data, length);
            this.writeTimestampNow(x, z);
            return;
        }

        // We now need to allocate new sectors. First we unallocate the currently allocated sectors.
        // Make sure there is an amount allocated. In theory sizeAllocated should be 0 if not, but let's make sure
        if (offset != 0) {
            for (int sector = 0; sector < sizeAllocated; sector++) {
                this.freeSectors[offset + sector] = true;
            }
        }

        int freeLength = 0, startingIndex = -1;
        for (int index = 0; index < this.freeSectors.length; index++) {
            if (freeLength != 0) {
                if (this.freeSectors[index]) {
                    freeLength++;
                    continue;
                }

                freeLength = 0;
                continue;
            }

            startingIndex = index;
            freeLength++;

            if (freeLength < sizeNeeded) {
                continue;
            }

            break;
        }

        offset = startingIndex;
        this.writeOffsetSize(x, z, offset, sizeNeeded);
        this.writeTimestampNow(x, z);

        // In this case, we found a space large enough within the already existing file
        if (freeLength >= sizeNeeded) {
            for (int i = 0; i < sizeNeeded; i++) {
                this.freeSectors[offset + i] = false;
            }

            this.writeSector(offset, data, length);
            return;
        }

        // The file is not large enough. We need to expand it.
        this.file.seek(this.file.length());

        // We can use any free sections at the end of the file
        int sectionsToCreate = sizeNeeded - freeLength;

        for (int i = startingIndex; i < this.freeSectors.length; i++) {
            this.freeSectors[i] = false; // Set as unavailable
        }

        // We do not need to do this for added sectors as they default to false
        this.freeSectors = Arrays.copyOf(this.freeSectors, this.freeSectors.length + sectionsToCreate);

        for (int i = 0; i < sectionsToCreate; i++) {
            this.file.write(EMPTY_SECTOR); // Ensure full sectors
        }

        this.sizeDelta += (sectionsToCreate << SECTOR_BYTE_POWER);
        this.writeSector(offset, data, length);
    }

    private synchronized void writeSector(int sectorNumber, byte[] data, int length) throws IOException {
        this.file.seek(sectorNumber << SECTOR_BYTE_POWER);
        this.file.writeInt(length + 1);
        this.file.writeByte(VERSION_DEFLATE);
        this.file.write(data, 0, length);
    }

    private synchronized void writeOffsetSize(int x, int z, int offset, int size) throws IOException {
        int compact = (offset << 8) | size;
        int index = this.getChunkIndex(x, z);

        this.sizeOffsetTable[index] = compact;

        this.file.seek(index << 2); // 4 bytes in an int
        this.file.writeInt(compact);
    }

    private synchronized void writeTimestampNow(int x, int z) throws IOException {
        this.writeTimestamp(x, z, (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
    }

    private synchronized void writeTimestamp(int x, int z, int value) throws IOException {
        int index = this.getChunkIndex(x, z);

        this.chunkTimestamps[index] = value;

        // Timestamps are sector index 1, so we skip first sector
        this.file.seek(SECTOR_BYTES + (index << 2)); // 4 bytes in an int
        this.file.writeInt(value);
    }

    private int getChunkOffsetSize(int x, int z) {
        return this.sizeOffsetTable[this.getChunkIndex(x, z)];
    }

    private int getChunkIndex(int x, int z) {
        return (x % 32) + ((z % 32) << 5);
    }
}
