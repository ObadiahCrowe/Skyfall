package io.skyfallsdk.nbt.tag.type;

import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;

import java.io.IOException;

public class TagLong extends NBTTag<Long> {

    public TagLong(String name) {
        super(NBTTagType.LONG, name);
    }

    public TagLong(String name, long value) {
        super(NBTTagType.LONG, name, value);
    }

    @Override
    public void read(NBTInputStream stream) throws IOException {
        this.setValue(stream.readLong());
    }

    @Override
    public void write(NBTOutputStream stream) throws IOException {
        stream.writeLong(this.getValue());
    }
}
