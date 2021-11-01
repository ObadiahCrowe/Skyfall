package io.skyfallsdk.nbt.tag.type;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.skyfallsdk.nbt.stream.NBTInputStream;
import io.skyfallsdk.nbt.stream.NBTOutputStream;
import io.skyfallsdk.nbt.tag.NBTTag;
import io.skyfallsdk.nbt.tag.NBTTagType;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TagCompound extends NBTTag<List<NBTTag<?>>> implements Map<String, NBTTag<?>>, Iterable<NBTTag<?>> {

    private Map<String, NBTTag<?>> backingMap;

    public TagCompound(String name) {
        super(NBTTagType.COMPOUND, name);
    }

    public TagCompound(String name, List<NBTTag<?>> value) {
        super(NBTTagType.COMPOUND, name, value);

        this.backingMap = Maps.newConcurrentMap();
        for (NBTTag<?> nbtTag : value) {
            this.backingMap.put(nbtTag.getName(), nbtTag);
        }
    }

    @Override
    public void read(NBTInputStream stream) throws IOException {
        List<NBTTag<?>> list = Lists.newArrayList();

        while (true) {
            NBTTag<?> tag = stream.readTag();
            if (tag instanceof TagEnd) {
                break;
            }

            list.add(tag);
        }

        this.setValue(list);
    }

    @Override
    public void write(NBTOutputStream stream) throws IOException {
        for (NBTTag<?> nbtTag : this.getValue()) {
            stream.writeTag(nbtTag);
        }

        stream.writeTag(NBTTagType.END.newEmptyInstance(null));
    }

    @Override
    public void setValue(List<NBTTag<?>> value) {
        super.setValue(value);

        this.backingMap = Maps.newConcurrentMap();
        for (NBTTag<?> nbtTag : value) {
            this.backingMap.put(nbtTag.getName(), nbtTag);
        }
    }

    @Override
    public int size() {
        return this.backingMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.backingMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.backingMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.backingMap.containsValue(value);
    }

    @Override
    public NBTTag<?> get(Object key) {
        return this.backingMap.get(key);
    }

    @Override
    public NBTTag<?> put(String key, NBTTag<?> value) {
        return this.backingMap.put(key, value);
    }

    @Override
    public NBTTag<?> remove(Object key) {
        return this.backingMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends NBTTag<?>> m) {
        this.backingMap.putAll(m);
    }

    @Override
    public void clear() {
        this.backingMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.backingMap.keySet();
    }

    @Override
    public Collection<NBTTag<?>> values() {
        return this.backingMap.values();
    }

    @Override
    public Set<Entry<String, NBTTag<?>>> entrySet() {
        return this.backingMap.entrySet();
    }

    @Override
    public Iterator<NBTTag<?>> iterator() {
        return this.backingMap.values().iterator();
    }

    @Override
    public String toString() {
        return "TagCompound{" +
          "backingMap=" + backingMap +
          '}';
    }
}
