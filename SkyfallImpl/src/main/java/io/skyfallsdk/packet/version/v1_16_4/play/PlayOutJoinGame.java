package io.skyfallsdk.packet.version.v1_16_4.play;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.skyfallsdk.Server;
import io.skyfallsdk.player.SkyfallPlayer;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.option.Gamemode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// TODO: 29/11/2021 Redo
public class PlayOutJoinGame extends io.skyfallsdk.packet.play.PlayOutJoinGame {

    private final SkyfallPlayer player;

    public PlayOutJoinGame(@NotNull SkyfallPlayer player) {
        super(PlayOutJoinGame.class);

        this.player = player;
    }

    @Override
    public int getEntityId() {
        return this.player.getId();
    }

    @Override
    public boolean isHardcore() {
        return false;
    }

    @Override
    public @NotNull Gamemode getGamemode() {
        return this.player.getGamemode();
    }

    @Override
    public @Nullable Gamemode getPreviousGamemode() {
        return this.player.getGamemode();
    }

    @Override
    public @NotNull List<@NotNull World> getWorlds() {
        return Lists.newArrayList(Server.get().getWorlds());
    }

    @Override
    public @NotNull World getSpawnWorld() {
        return this.getWorlds().get(0);
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

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(this.getEntityId());
        buf.writeBoolean(this.isHardcore());

        //buf.wri
    }
}
