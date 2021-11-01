package io.skyfallsdk.packet.version.v1_17_1.play;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.world.option.Gamemode;
import io.skyfallsdk.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlayOutJoinGame extends io.skyfallsdk.packet.play.PlayOutJoinGame {

    public PlayOutJoinGame() {
        super(PlayOutJoinGame.class);
    }

    @Override
    public void write(ByteBuf buf) {

    }

    @Override
    public int getEntityId() {
        return 0;
    }

    @Override
    public boolean isHardcore() {
        return false;
    }

    @Override
    public @NotNull Gamemode getGamemode() {
        return null;
    }

    @Override
    public @Nullable Gamemode getPreviousGamemode() {
        return null;
    }

    @Override
    public @NotNull List<@NotNull World> getWorlds() {
        return null;
    }

    @Override
    public @NotNull World getSpawnWorld() {
        return null;
    }

    @Override
    public boolean useReducedDebugInfo() {
        return false;
    }

    @Override
    public boolean enableRespawnScreen() {
        return false;
    }

    @Override
    public boolean isDebugWorld() {
        return false;
    }

    @Override
    public boolean isFlatWorld() {
        return false;
    }
}
