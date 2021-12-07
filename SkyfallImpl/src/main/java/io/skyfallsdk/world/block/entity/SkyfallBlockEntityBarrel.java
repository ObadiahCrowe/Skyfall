package io.skyfallsdk.world.block.entity;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.nbt.tag.type.TagList;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntityBarrelMetadata;
import io.skyfallsdk.world.block.entity.type.BlockEntityBarrel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkyfallBlockEntityBarrel extends AbstractBlockEntity<BlockEntityBarrelMetadata> implements BlockEntityBarrel {

    public SkyfallBlockEntityBarrel(@NotNull BlockEntityType type, @NotNull TagCompound entityTag) {
        super(type, entityTag);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected BlockEntityBarrelMetadata parseEntityTag(@NotNull TagCompound entityTag) {
        String customNameJson = (String) entityTag.get("CustomName").getValue();
        ChatComponent customName = customNameJson == null ? ChatComponent.empty() : ChatComponent.fromJson(customNameJson);
        List<TagCompound> items = ((TagList<TagCompound>) entityTag.get("Items")).getValue();

        if (entityTag.containsKey("Lock")) {
            String lock = (String) entityTag.get("Lock").getValue();
        }

        if (entityTag.containsKey("LootTable")) {
            String lootTable = (String) entityTag.get("LootTable").getValue();
        }

        if (entityTag.containsKey("LootTableSeed")) {
            long lootTableSeed = (long) entityTag.get("LootTableSeed").getValue();
        }

        return null;
    }
}
