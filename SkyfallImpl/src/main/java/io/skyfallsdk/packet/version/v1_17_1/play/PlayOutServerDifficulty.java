package io.skyfallsdk.packet.version.v1_17_1.play;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.world.option.Difficulty;
import org.jetbrains.annotations.NotNull;

public class PlayOutServerDifficulty extends io.skyfallsdk.packet.play.PlayOutServerDifficulty {

    private final Difficulty difficulty;
    private final boolean isLocked;

    public PlayOutServerDifficulty(@NotNull Difficulty difficulty, boolean isLocked) {
        super(PlayOutServerDifficulty.class);

        this.difficulty = difficulty;
        this.isLocked = isLocked;
    }

    @Override
    public @NotNull Difficulty getDifficulty() {
        return this.difficulty;
    }

    @Override
    public boolean isDifficultyLocked() {
        return this.isLocked;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(this.difficulty.ordinal());
        buf.writeBoolean(this.isLocked);
    }
}
