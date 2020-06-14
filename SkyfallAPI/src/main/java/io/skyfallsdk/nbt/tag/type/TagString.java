package io.skyfallsdk.nbt.tag.type;

import com.google.common.base.Charsets;
import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;

import java.io.IOException;

public class TagString extends NBTTag<String> {

    public TagString(String name) {
        super(NBTTagType.STRING, name);
    }

    public TagString(String name, String value) {
        super(NBTTagType.STRING, name, value);
    }

    @Override
    public void read(NBTInputStream stream) throws IOException {
        int length = stream.readShort();
        byte[] bytes = new byte[length];

        stream.readFully(bytes);

        this.setValue(new String(bytes, Charsets.UTF_8));
    }

    @Override
    public void write(NBTOutputStream stream) throws IOException {
        byte[] bytes = this.getValue().getBytes(Charsets.UTF_8);

        stream.writeShort(bytes.length);
        stream.write(bytes);
    }
}
