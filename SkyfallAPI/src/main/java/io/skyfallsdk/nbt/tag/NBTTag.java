package io.skyfallsdk.nbt.tag;

import io.skyfallsdk.nbt.stream.NBTStreamReaderWriter;
import io.skyfallsdk.util.Validator;

import java.util.Objects;

/**
 * A single NBT tag
 *
 * @param <T> The type value of this tag
 */
public abstract class NBTTag<T> implements Comparable<NBTTag<?>>, Cloneable, NBTStreamReaderWriter {

    private final NBTTagType type;
    private final String name;
    private T value;

    /**
     * Creates a new tag without a value
     *
     * @param type The {@link NBTTagType type}
     * @param name The name of the tag
     */
    public NBTTag(NBTTagType type, String name) {
        Validator.isTrue(type != null, "Please provide a tag type!");
        Validator.isTrue(name != null || type == NBTTagType.END, "Please provide a name for the tag!");

        this.type = type;
        this.name = name;
    }

    /**
     * Creates a new tag with a value
     *
     * @param type  The {@link NBTTagType type}
     * @param name  The name of the tag
     * @param value The value
     */
    public NBTTag(NBTTagType type, String name, T value) {
        Validator.isTrue(type != null, "Please provide a tag type!");
        Validator.isTrue(name != null || type == NBTTagType.END, "Please provide a name for the tag!");
        Validator.isTrue(value != null || type == NBTTagType.END, "Please provide a value for the tag!");

        this.type = type;
        this.name = name;
        this.value = value;
    }

    /**
     * @return The {@link NBTTagType type} of tag
     */
    public NBTTagType getType() {
        return this.type;
    }

    /**
     * @return The name of the tag
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The value of the tag
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Updates the value of the tag
     *
     * @param value The new value
     */
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof NBTTag)) {
            return false;
        }

        NBTTag<?> nbtTag = (NBTTag<?>) other;
        return Objects.equals(this.getValue(), nbtTag.getValue())
          && Objects.equals(this.getName(), nbtTag.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getType(), this.getName(), this.getValue());
    }

    @Override
    public String toString() {
        return "NBTTag{" +
          "type=" + this.type +
          ", name='" + this.name + '\'' +
          ", value=" + this.value +
          '}';
    }

    @Override
    public int compareTo(NBTTag<?> other) {
        if (this.equals(other)) {
            return 0;
        }

        // This means the tags have the same name, but a different value (Per the result of #equals)
        if (other.getName().equals(this.getName())) {
            throw new IllegalStateException("Cannot compare two tags with the same name but different values");
        }

        return this.getName().compareTo(other.getName());
    }
}
