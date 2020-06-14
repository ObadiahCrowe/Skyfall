package io.skyfallsdk.nbt.tag.type;

import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;

import java.io.IOException;

public class TagByteArray extends NBTTag<byte[]> {

    public TagByteArray(String name) {
        super(NBTTagType.BYTE_ARRAY, name);
    }

    public TagByteArray(String name, byte[] value) {
        super(NBTTagType.BYTE_ARRAY, name, value);
    }

    @Override
    public void read(NBTInputStream stream) throws IOException {
        int size = stream.readInt();
        byte[] bytes = new byte[size];
        stream.readFully(bytes);
        this.setValue(bytes);
    }

    @Override
    public void write(NBTOutputStream stream) throws IOException {
        byte[] value = this.getValue();
        stream.writeInt(value.length);
        stream.write(value);
    }
}
