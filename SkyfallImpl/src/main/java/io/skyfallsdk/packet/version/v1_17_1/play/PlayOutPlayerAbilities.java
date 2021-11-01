package io.skyfallsdk.packet.version.v1_17_1.play;

import io.netty.buffer.ByteBuf;
import io.skyfallsdk.player.SkyfallPlayer;
import io.skyfallsdk.world.option.Gamemode;
import org.jetbrains.annotations.NotNull;

public class PlayOutPlayerAbilities extends io.skyfallsdk.packet.play.PlayOutPlayerAbilities {

    private final SkyfallPlayer player;

    public PlayOutPlayerAbilities(@NotNull SkyfallPlayer player) {
        super(PlayOutPlayerAbilities.class);

        this.player = player;
    }

    @Override
    public boolean isInvulnerable() {
        return this.player.isInvulnerable();
    }

    @Override
    public boolean isFlying() {
        return this.player.isFlying();
    }

    @Override
    public boolean isFlyingAllowed() {
        return this.player.isFlyingAllowed();
    }

    @Override
    public boolean canBreakInstantly() {
        return this.player.getGamemode() == Gamemode.CREATIVE;
    }

    @Override
    public float getFlyingSpeed() {
        return this.player.getFlyingSpeed();
    }

    @Override
    public float getWalkingSpeed() {
        return this.player.getWalkingSpeed();
    }

    @Override
    public void write(ByteBuf buf) {
        byte flags = 0x00;

        flags |= this.player.isInvulnerable() ? 0x01 : 0x00;
        flags |= this.player.isFlying() ? 0x02 : 0x00;
        flags |= this.player.isFlyingAllowed() ? 0x04 : 0x00;
        flags |= this.player.getGamemode() == Gamemode.CREATIVE ? 0x08 : 0x00;

        buf.writeByte(flags);
        buf.writeFloat(this.player.getFlyingSpeed());
        buf.writeFloat(this.player.getWalkingSpeed());
    }
}
