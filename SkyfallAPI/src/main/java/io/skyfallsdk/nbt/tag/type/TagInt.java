package io.skyfallsdk.nbt.tag.type;

import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;

import java.io.IOException;

public class TagInt extends NBTTag<Integer> {

    public TagInt(String name) {
        super(NBTTagType.INT, name);
    }

    public TagInt(String name, int value) {
        super(NBTTagType.INT, name, value);
    }

    @Override
    public void read(NBTInputStream stream) throws IOException {
        this.setValue(stream.readInt());
    }

    @Override
    public void write(NBTOutputStream stream) throws IOException {
        stream.writeInt(this.getValue());
    }
}
