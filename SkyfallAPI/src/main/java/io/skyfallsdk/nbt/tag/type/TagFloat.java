package io.skyfallsdk.nbt.tag.type;

import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;

import java.io.IOException;

public class TagFloat extends NBTTag<Float> {

    public TagFloat(String name) {
        super(NBTTagType.FLOAT, name);
    }

    public TagFloat(String name, float value) {
        super(NBTTagType.FLOAT, name, value);
    }

    @Override
    public void read(NBTInputStream stream) throws IOException {
        this.setValue(stream.readFloat());
    }

    @Override
    public void write(NBTOutputStream stream) throws IOException {
        stream.writeFloat(this.getValue());
    }
}
