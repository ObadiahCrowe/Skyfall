package io.skyfallsdk.world.block;

import io.skyfallsdk.substance.Substance;
import io.skyfallsdk.world.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Block {

    @NotNull
    Position getPosition();

    @Nullable
    Substance getSubstance();

    void setSubstance(@Nullable Substance substance);

    byte getLightLevel();

    void setLightLevel(byte lightLevel);


}
