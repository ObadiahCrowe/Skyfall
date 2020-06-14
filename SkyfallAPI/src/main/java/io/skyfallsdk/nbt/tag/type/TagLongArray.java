package io.skyfallsdk.nbt.tag.type;

import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;

import java.io.IOException;

public class TagLongArray extends NBTTag<long[]> {

    public TagLongArray(String name) {
        super(NBTTagType.LONG_ARRAY, name);
    }

    public TagLongArray(String name, long[] value) {
        super(NBTTagType.LONG_ARRAY, name, value);
    }

    @Override
    public void read(NBTInputStream stream) throws IOException {
        int size = stream.readInt();

        long[] values = new long[size];
        for (int i = 0; i < size; i++) {
            values[i] = stream.readLong();
        }

        this.setValue(values);
    }

    @Override
    public void write(NBTOutputStream stream) throws IOException {
        long[] values = this.getValue();

        stream.writeInt(values.length);
        for (long value : values) {
            stream.writeLong(value);
        }
    }
}
