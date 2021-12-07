package io.skyfallsdk.world.block.entity.meta.type;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.potion.PotionEffect;
import io.skyfallsdk.world.block.entity.meta.BlockEntityMetadata;
import io.skyfallsdk.world.block.entity.type.BlockEntityBanner;
import io.skyfallsdk.world.block.entity.type.BlockEntityBeacon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface BlockEntityBeaconMetadata extends BlockEntityMetadata<BlockEntityBeacon> {

    @Nullable
    ChatComponent getCustomName();

    void setCustomName(@Nullable ChatComponent customName);

    @NotNull
    Optional<@Nullable String> getLock();

    void setLock(@Nullable String lock);

    int getAvailableLevels();

    void setAvailableLevels(int levels);

    @Nullable
    PotionEffect getPrimaryEffect();

    void setPrimaryEffect(@Nullable PotionEffect primaryEffect);

    @Nullable
    PotionEffect getSecondaryEffect();

    void setSecondaryEffect(@Nullable PotionEffect secondaryEffect);
}
