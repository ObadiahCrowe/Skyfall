package io.skyfallsdk.nbt.tag.type;

import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;

import java.io.IOException;

public class TagShort extends NBTTag<Short> {

    public TagShort(String name) {
        super(NBTTagType.SHORT, name);
    }

    public TagShort(String name, short value) {
        super(NBTTagType.SHORT, name, value);
    }

    @Override
    public void read(NBTInputStream stream) throws IOException {
        this.setValue(stream.readShort());
    }

    @Override
    public void write(NBTOutputStream stream) throws IOException {
        stream.writeShort(this.getValue());
    }
}
