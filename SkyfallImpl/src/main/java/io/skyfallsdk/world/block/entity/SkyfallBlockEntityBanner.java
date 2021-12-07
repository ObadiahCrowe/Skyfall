package io.skyfallsdk.world.block.entity;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.chat.colour.ChatColour;
import io.skyfallsdk.nbt.tag.type.TagCompound;
import io.skyfallsdk.nbt.tag.type.TagInt;
import io.skyfallsdk.nbt.tag.type.TagList;
import io.skyfallsdk.world.block.entity.meta.SkyfallBlockEntityBannerMetadata;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntityBannerMetadata;
import io.skyfallsdk.world.block.entity.type.BlockEntityBanner;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkyfallBlockEntityBanner extends AbstractBlockEntity<BlockEntityBannerMetadata> implements BlockEntityBanner {

    public SkyfallBlockEntityBanner(@NotNull BlockEntityType type, @NotNull TagCompound entityTag) {
        super(type, entityTag);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected BlockEntityBannerMetadata parseEntityTag(@NotNull TagCompound entityTag) {
        String customNameJson = (String) entityTag.get("CustomName").getValue();
        ChatComponent customName = customNameJson == null ? ChatComponent.empty() : ChatComponent.fromJson(customNameJson);
        List<TagCompound> rawPatterns = ((TagList<TagCompound>) entityTag.get("Patterns")).getValue();

        SkyfallBlockEntityBannerMetadata metadata = new SkyfallBlockEntityBannerMetadata(this, customName);
        for (TagCompound compound : rawPatterns) {
            ChatColour colour = ChatColour.getByCode((char) ((int) compound.get("Color").getValue()));
            BlockEntityBannerMetadata.Pattern pattern = BlockEntityBannerMetadata.Pattern.getByCode((String) compound.get("Pattern").getValue());

            if (colour == null || pattern == null) {
                continue;
            }

            metadata.getPatterns().put(pattern, colour);
        }

        return metadata;
    }
}
