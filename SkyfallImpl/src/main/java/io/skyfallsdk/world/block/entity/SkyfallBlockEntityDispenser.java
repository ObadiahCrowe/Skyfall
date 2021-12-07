package io.skyfallsdk.world.block.entity;

import com.google.gson.Gson;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.item.Item;
import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.nbt.tag.type.TagList;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntityDispenserMetadata;
import io.skyfallsdk.world.block.entity.type.BlockEntityDispenser;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkyfallBlockEntityDispenser extends AbstractBlockEntity<BlockEntityDispenserMetadata> implements BlockEntityDispenser {

    public SkyfallBlockEntityDispenser(@NotNull BlockEntityType type, @NotNull TagCompound entityTag) {
        super(type, entityTag);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected BlockEntityDispenserMetadata parseEntityTag(@NotNull TagCompound entityTag) {
        System.out.println("Reading dispenser");
        ChatComponent customName = null;

        if (entityTag.containsKey("CustomName")) {
            String customNameJson = (String) entityTag.get("CustomName").getValue();
            customName = customNameJson == null ? ChatComponent.empty() : ChatComponent.fromJson(customNameJson);
        }

        if (entityTag.containsKey("Items")) {
            List<TagCompound> items = ((TagList<TagCompound>) entityTag.get("Items")).getValue();
            for (TagCompound item : items) {
                byte slot = (byte) item.get("Slot").getValue();

                System.out.println("got slot: " + slot);
                Item i = new Item(item);

                System.out.println("item in slot " + slot + ": " + new Gson().toJson(i));
            }
        }

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
