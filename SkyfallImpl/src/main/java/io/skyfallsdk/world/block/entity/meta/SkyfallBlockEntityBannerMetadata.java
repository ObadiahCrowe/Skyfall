package io.skyfallsdk.world.block.entity.meta;

import com.google.common.collect.Maps;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.chat.colour.ChatColour;
import io.skyfallsdk.world.block.entity.SkyfallBlockEntityBanner;
import io.skyfallsdk.world.block.entity.meta.type.BlockEntityBannerMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class SkyfallBlockEntityBannerMetadata extends AbstractBlockEntityMetadata<SkyfallBlockEntityBanner> implements BlockEntityBannerMetadata {

    private ChatComponent customName;
    private final Map<Pattern, ChatColour> patterns;

    public SkyfallBlockEntityBannerMetadata(@NotNull SkyfallBlockEntityBanner entity, @Nullable ChatComponent customName) {
        super(entity);

        this.customName = customName;
        this.patterns = Maps.newHashMap();
    }

    @Override
    public @Nullable ChatComponent getCustomName() {
        return this.customName;
    }

    @Override
    public synchronized void setCustomName(@Nullable ChatComponent customName) {
        this.customName = customName;
    }

    @Override
    public @NotNull Map<@NotNull Pattern, @NotNull ChatColour> getPatterns() {
        return this.patterns;
    }
}
