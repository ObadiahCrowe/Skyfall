package io.skyfallsdk.nbt.tag.type;

import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;

import java.io.IOException;

public class TagByte extends NBTTag<Byte> {

    public TagByte(String name) {
        super(NBTTagType.BYTE, name);
    }

    public TagByte(String name, byte value) {
        super(NBTTagType.BYTE, name, value);
    }

    @Override
    public void read(NBTInputStream stream) throws IOException {
        this.setValue(stream.readByte());
    }

    @Override
    public void write(NBTOutputStream stream) throws IOException {
        stream.writeByte(this.getValue());
    }
}
