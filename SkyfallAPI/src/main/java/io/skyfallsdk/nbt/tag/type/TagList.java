package io.skyfallsdk.nbt.tag.type;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;

import java.io.IOException;
import java.util.List;

public class TagList<T extends NBTTag> extends NBTTag<List<T>> {

    private NBTTagType listType;

    public TagList(String name) {
        super(NBTTagType.LIST, name);
    }

    public TagList(String name, List<T> value) {
        super(NBTTagType.LIST, name, value);

        T firstVal = Iterables.getFirst(value, null);
        if (firstVal == null) {
            throw new IllegalArgumentException("Empty list given! Unable to infer type!");
        }

        this.listType = firstVal.getType();
    }

    /**
     * @return The type of tag contained in this list
     */
    public NBTTagType getListType() {
        return this.listType;
    }

    /**
     * Adds a new value to the list. Not type safe.
     *
     * @param value The new value to add
     */
    public void addValue(Object value) {
        List list = this.getValue();
        list.add(this.getListType().newInstance("List" + list.size(), value));
    }

    @Override
    public void read(NBTInputStream stream) throws IOException {
        List list = Lists.newArrayList();

        byte tagId = stream.readByte();
        int size = stream.readInt();

        this.listType = NBTTagType.fromTypeId(tagId);
        for (int i = 0; i < size; i++) {
            NBTTag tag = this.getListType().newEmptyInstance("List" + i);
            tag.read(stream); // Read payload directly
            list.add(tag);
        }

        this.setValue(list);
    }

    @Override
    public void write(NBTOutputStream stream) throws IOException {
        List<T> list = this.getValue();

        stream.writeByte(this.listType.getTypeId());
        stream.writeInt(list.size());

        for (T tag : list) {
            tag.write(stream); // Write payload directly
        }
    }
}
