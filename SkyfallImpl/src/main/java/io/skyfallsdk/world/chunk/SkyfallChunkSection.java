package io.skyfallsdk.world.chunk;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.skyfallsdk.entity.Entity;
import io.skyfallsdk.entity.SkyfallEntity;
import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.net.NetSerializable;
import org.jetbrains.annotations.NotNull;

public class SkyfallChunkSection implements ChunkSection, NetSerializable {

    private final SkyfallEntity[] entities;

    public SkyfallChunkSection(@NotNull TagCompound compound) {
        System.out.println(new Gson().toJson(compound));

        this.entities = new SkyfallEntity[0];
    }

    @Override
    public Entity[] getEntities() {
        return this.entities;
    }

    public void write(@NotNull TagCompound compound) {

    }

    public void read(@NotNull TagCompound compound) {

    }

    @Override
    public void write(@NotNull ByteBuf buf) {

    }

    @Override
    public void read(@NotNull ByteBuf buf) {

    }
}
