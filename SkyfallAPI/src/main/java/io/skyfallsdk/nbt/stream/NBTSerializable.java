package io.skyfallsdk.nbt.stream;

import io.skyfallsdk.nbt.tag.type.TagCompound;
import org.jetbrains.annotations.NotNull;

public interface NBTSerializable {

    void write(@NotNull TagCompound compound);

    void read(@NotNull TagCompound compound);
}
