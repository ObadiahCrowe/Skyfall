package io.skyfallsdk.packet.play;

import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;
import io.skyfallsdk.world.option.Gamemode;
import io.skyfallsdk.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class PlayOutJoinGame extends NetPacketOut {

    public PlayOutJoinGame(Class<? extends PlayOutJoinGame> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return PlayOutJoinGame.class;
    }

    public abstract int getEntityId();

    public abstract boolean isHardcore();

    public abstract @NotNull Gamemode getGamemode();

    public abstract @Nullable Gamemode getPreviousGamemode();

    public abstract @NotNull List<@NotNull World> getWorlds();

    public abstract @NotNull World getSpawnWorld();

    public abstract boolean useReducedDebugInfo();

    public abstract boolean enableRespawnScreen();

    public abstract boolean isDebugWorld();

    public abstract boolean isFlatWorld();
}
