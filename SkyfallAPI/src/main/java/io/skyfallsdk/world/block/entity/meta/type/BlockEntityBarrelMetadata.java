package io.skyfallsdk.world.block.entity.meta.type;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.item.Item;
import io.skyfallsdk.world.block.entity.meta.BlockEntityMetadata;
import io.skyfallsdk.world.block.entity.type.BlockEntityBanner;
import io.skyfallsdk.world.block.entity.type.BlockEntityBarrel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface BlockEntityBarrelMetadata extends BlockEntityMetadata<BlockEntityBarrel> {

    @Nullable
    ChatComponent getCustomName();

    void setCustomName(@Nullable ChatComponent customName);

    @NotNull
    List<@NotNull Item> getItems();

    @NotNull
    Optional<@Nullable String> getLock();

    void setLock(@Nullable String lock);

    @NotNull
    Optional<@Nullable String> getLootTableKey();

    void setLootTableKey(@Nullable String lootTableKey);

    @NotNull
    Optional<@Nullable Long> getLootTableSeed();

    void setLootTableSeed(long seed);
}
