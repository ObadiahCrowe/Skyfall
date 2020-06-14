package io.skyfallsdk.nbt.tag.type;

import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;

import java.io.IOException;

public class TagEnd extends NBTTag<Object> {

    public TagEnd() {
        super(NBTTagType.END, null, null);
    }

    @Override
    public void read(NBTInputStream stream) throws IOException {
    }

    @Override
    public void write(NBTOutputStream stream) throws IOException {
    }
}
