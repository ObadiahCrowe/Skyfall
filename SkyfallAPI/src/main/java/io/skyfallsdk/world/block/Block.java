package io.skyfallsdk.world.block;

import io.skyfallsdk.substance.Substance;
import io.skyfallsdk.world.Position;
import org.jetbrains.annotations.NotNull;

public interface Block {

    @NotNull
    Position getPosition();

    @NotNull
    Substance getSubstance();

    void setSubstance(@NotNull Substance substance);

    byte getLightLevel();

    void setLightLevel(byte lightLevel);


}
