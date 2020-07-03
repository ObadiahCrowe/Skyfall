package io.skyfallsdk.world.chunk;

import io.skyfallsdk.nbt.tag.NBTTagType;
import io.skyfallsdk.nbt.tag.type.TagLongArray;

/**
 * Simple utility for accessing a height map
 */
public class HeightMap {

    private String name;
    private long[] map;

    public HeightMap(TagLongArray map) {
        this(map.getName(), map.getValue());
    }

    public HeightMap(String name, long[] map) {
        this.name = name;
        this.map = map;
    }

    public String getName() {
        return this.name;
    }

    public long getValue(int x, int z) {
        return this.map[this.getIndex(x, z)];
    }

    public void setValue(int x, int z, long newValue) {
        this.map[this.getIndex(x, z)] = newValue;
    }

    public TagLongArray toNBT() {
        return NBTTagType.LONG_ARRAY.newInstance(this.getName(), this.map);
    }

    // Internals

    private int getIndex(int x, int z) {
        return (z << 4) | x;
    }
}
