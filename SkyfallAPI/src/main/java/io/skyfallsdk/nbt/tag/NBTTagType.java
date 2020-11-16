package io.skyfallsdk.nbt.tag;

import io.skyfallsdk.nbt.tag.type.*;

import java.util.List;

/**
 * An enum containing all the different types of {@link NBTTag NBT tags}
 */
public enum NBTTagType {

    END(TagEnd.class),
    BYTE(TagByte.class),
    SHORT(TagShort.class),
    INT(TagInt.class),
    LONG(TagLong.class),
    FLOAT(TagFloat.class),
    DOUBLE(TagDouble.class),
    BYTE_ARRAY(TagByteArray.class),
    STRING(TagString.class),
    LIST(TagList.class),
    COMPOUND(TagCompound.class),
    INT_ARRAY(TagIntArray.class),
    LONG_ARRAY(TagLongArray.class);

    // Cache here to avoid #values() call constantly
    private static final NBTTagType[] VALUES = NBTTagType.values();

    private final Class<? extends NBTTag> tagClass;

    /**
     * Creates a new tag type of the given class
     *
     * @param tagClass The class
     */
    NBTTagType(Class<? extends NBTTag> tagClass) {
        this.tagClass = tagClass;
    }

    /**
     * @return The class of this tag type
     */
    public Class<? extends NBTTag> getTagClass() {
        return this.tagClass;
    }

    /**
     * @return The type ID of this tag type
     */
    public int getTypeId() {
        return this.ordinal();
    }

    /**
     * Creates a new instance of this tag type with the given value
     *
     * @param name  The name of the tag
     * @param value The value of the tag
     * @param <G>   The type of the value
     * @param <T>   The type of this tag (Equal to {@link #getTagClass()})
     * @return The created instance
     */
    @SuppressWarnings("unchecked")
    public <G, T extends NBTTag<G>> T newInstance(String name, G value) {
        switch (this) {
            case END:
                return (T) new TagEnd();
            case BYTE:
                return (T) new TagByte(name, (Byte) value);
            case SHORT:
                return (T) new TagShort(name, (Short) value);
            case INT:
                return (T) new TagInt(name, (Integer) value);
            case LONG:
                return (T) new TagLong(name, (Long) value);
            case FLOAT:
                return (T) new TagFloat(name, (Float) value);
            case DOUBLE:
                return (T) new TagDouble(name, (Double) value);
            case BYTE_ARRAY:
                return (T) new TagByteArray(name, (byte[]) value);
            case STRING:
                return (T) new TagString(name, (String) value);
            case LIST:
                return (T) new TagList<>(name, (List<NBTTag>) value);
            case COMPOUND:
                return (T) new TagCompound(name, (List<NBTTag<?>>) value);
            case INT_ARRAY:
                return (T) new TagIntArray(name, (int[]) value);
            case LONG_ARRAY:
                return (T) new TagLongArray(name, (long[]) value);
            default:
                throw new UnsupportedOperationException("Missing case for NBTTagType." + this.name() + "!");
        }
    }

    /**
     * Creates a new empty instance of this tag type
     *
     * @param name The name of the tag
     * @param <G>  The type of the value
     * @param <T>  The type of this tag (Equal to {@link #getTagClass()})
     * @return The created instance
     */
    @SuppressWarnings("unchecked")
    public <G, T extends NBTTag<G>> T newEmptyInstance(String name) {
        switch (this) {
            case END:
                return (T) new TagEnd();
            case BYTE:
                return (T) new TagByte(name);
            case SHORT:
                return (T) new TagShort(name);
            case INT:
                return (T) new TagInt(name);
            case LONG:
                return (T) new TagLong(name);
            case FLOAT:
                return (T) new TagFloat(name);
            case DOUBLE:
                return (T) new TagDouble(name);
            case BYTE_ARRAY:
                return (T) new TagByteArray(name);
            case STRING:
                return (T) new TagString(name);
            case LIST:
                return (T) new TagList<>(name);
            case COMPOUND:
                return (T) new TagCompound(name);
            case INT_ARRAY:
                return (T) new TagIntArray(name);
            case LONG_ARRAY:
                return (T) new TagLongArray(name);
            default:
                throw new UnsupportedOperationException("Missing case for NBTTagType." + this.name() + "!");
        }
    }

    /**
     * Finds the {@link NBTTagType type} associated with the given tag ID
     *
     * @param id The id
     * @return The found {@link NBTTagType}
     * @throws IndexOutOfBoundsException If index is not within acceptable parameters
     */
    public static NBTTagType fromTypeId(int id) {
        if (id < 0 || id >= VALUES.length) {
            throw new IndexOutOfBoundsException();
        }

        return VALUES[id];
    }
}
