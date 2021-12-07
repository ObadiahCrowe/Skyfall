package io.skyfallsdk.world.block.entity.meta.type;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.chat.colour.ChatColour;
import io.skyfallsdk.world.block.entity.meta.BlockEntityMetadata;
import io.skyfallsdk.world.block.entity.type.BlockEntityBanner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface BlockEntityBannerMetadata extends BlockEntityMetadata<BlockEntityBanner> {

    @Nullable
    ChatComponent getCustomName();

    void setCustomName(@Nullable ChatComponent customName);

    @NotNull
    Map<@NotNull Pattern, @NotNull ChatColour> getPatterns();

    enum Pattern {

        BASE,
        BOTTOM_STRIPE,
        TOP_STRIPE,
        LEFT_STRIPE,
        RIGHT_STRIPE,
        CENTER_VERTICAL_STRIPE,
        CENTER_HORIZONTAL_STRIPE,
        DOWN_RIGHT_STRIPE,
        DOWN_LEFT_STRIPE,
        SMALL_VERTICAL_STRIPES,
        DIAGONAL_CROSS,
        SQUARE_CROSS,
        LEFT_OF_DIAGONAL,
        RIGHT_OF_DIAGONAL,
        LEFT_OF_UPSIDE_DOWN_DIAGONAL,
        RIGHT_OF_UPSIDE_DOWN_DIAGONAL,
        LEFT_VERTICAL_HALF,
        RIGHT_VERTICAL_HALF,
        TOP_HORIZONTAL_HALF,
        BOTTOM_HORIZONTAL_HALF,
        BOTTOM_LEFT_CORNER,
        BOTTOM_RIGHT_CORNER,
        TOP_LEFT_CORNER,
        TOP_RIGHT_CORNER,
        BOTTOM_TRIANGLE,
        TOP_TRIANGLE;

        public static @Nullable Pattern getByCode(@NotNull String patternCode) {
            return null;
        }
    }
}
