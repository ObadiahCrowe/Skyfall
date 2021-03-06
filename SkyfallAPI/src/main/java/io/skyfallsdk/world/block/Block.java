package io.skyfallsdk.world.block;

import io.skyfallsdk.substance.Substance;
import io.skyfallsdk.world.Position;

public interface Block {

    Position getPosition();

    Substance getSubstance();

    void setSubstance(Substance substance);

    byte getLightLevel();

    void setLightLevel(byte lightLevel);


}
