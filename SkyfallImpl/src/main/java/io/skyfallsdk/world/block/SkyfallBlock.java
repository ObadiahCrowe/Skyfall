package io.skyfallsdk.world.block;

import io.skyfallsdk.substance.Substance;
import io.skyfallsdk.world.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkyfallBlock implements Block {
    @Override
    public @NotNull Position getPosition() {
        return null;
    }

    @Override
    public @NotNull Substance getSubstance() {
        return null;
    }

    @Override
    public void setSubstance(@NotNull Substance substance) {

    }

    @Override
    public byte getLightLevel() {
        return 0;
    }

    @Override
    public void setLightLevel(byte lightLevel) {

    }
}
