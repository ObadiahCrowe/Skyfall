package io.skyfallsdk.packet.play;

import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;
import io.skyfallsdk.world.option.Difficulty;
import org.jetbrains.annotations.NotNull;

public abstract class PlayOutServerDifficulty extends NetPacketOut {

    public PlayOutServerDifficulty(Class<? extends PlayOutServerDifficulty> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return PlayOutServerDifficulty.class;
    }

    public abstract @NotNull Difficulty getDifficulty();

    public abstract boolean isDifficultyLocked();
}
