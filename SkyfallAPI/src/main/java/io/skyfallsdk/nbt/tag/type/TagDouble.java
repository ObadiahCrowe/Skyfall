package io.skyfallsdk.nbt.tag.type;

import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;

import java.io.IOException;

public class TagDouble extends NBTTag<Double> {

    public TagDouble(String name) {
        super(NBTTagType.DOUBLE, name);
    }

    public TagDouble(String name, double value) {
        super(NBTTagType.DOUBLE, name, value);
    }

    @Override
    public void read(NBTInputStream stream) throws IOException {
        this.setValue(stream.readDouble());
    }

    @Override
    public void write(NBTOutputStream stream) throws IOException {
        stream.writeDouble(this.getValue());
    }
}
