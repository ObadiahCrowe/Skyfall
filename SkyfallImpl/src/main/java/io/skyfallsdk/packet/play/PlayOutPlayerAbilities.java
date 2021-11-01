package io.skyfallsdk.packet.play;

import io.skyfallsdk.packet.PacketOut;
import io.skyfallsdk.packet.version.NetPacketOut;

public abstract class PlayOutPlayerAbilities extends NetPacketOut {

    public PlayOutPlayerAbilities(Class<? extends PlayOutPlayerAbilities> packet) {
        super(packet);
    }

    @Override
    public Class<? extends PacketOut> getGeneric() {
        return PlayOutPlayerAbilities.class;
    }

    public abstract boolean isInvulnerable();

    public abstract boolean isFlying();

    public abstract boolean isFlyingAllowed();

    public abstract boolean canBreakInstantly();

    public abstract float getFlyingSpeed();

    public abstract float getWalkingSpeed();
}
