package io.skyfallsdk.nbt.tag.type;

import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;

import java.io.IOException;

public class TagIntArray extends NBTTag<int[]> {

    public TagIntArray(String name) {
        super(NBTTagType.INT_ARRAY, name);
    }

    public TagIntArray(String name, int[] value) {
        super(NBTTagType.INT_ARRAY, name, value);
    }

    @Override
    public void read(NBTInputStream stream) throws IOException {
        int length = stream.readInt();

        int[] values = new int[length];
        for (int i = 0; i < values.length; i++) {
            values[i] = stream.readInt();
        }

        this.setValue(values);
    }

    @Override
    public void write(NBTOutputStream stream) throws IOException {
        int[] values = this.getValue();

        stream.writeInt(values.length);
        for (int value : values) {
            stream.writeInt(value);
        }
    }
}
