package io.skyfallsdk.player;

import io.skyfallsdk.entity.EntityLiving;
import io.skyfallsdk.entity.EntityType;
import io.skyfallsdk.inventory.type.entity.EntityInventory;
import io.skyfallsdk.inventory.type.entity.PlayerInventory;
import io.skyfallsdk.permission.PermissionHolder;
import io.skyfallsdk.protocol.client.ClientInfo;
import io.skyfallsdk.command.CommandSender;
import io.skyfallsdk.world.option.Gamemode;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface Player extends EntityLiving, CommandSender, PermissionHolder {

    default String getUsername() {
        return this.getClient().getUsername();
    }

    @Override
    default UUID getUuid() {
        return this.getClient().getUuid();
    }

    @Override
    default EntityType getType() {
        return EntityType.PLAYER;
    }

    @NotNull
    ClientInfo getClient();

    @NotNull
    Gamemode getGamemode();

    @NotNull
    PlayerProperties getProperties();

    @Override
    PlayerInventory getInventory();

    boolean isInvulnerable();

    void setInvulnerable(boolean invulnerable);

    boolean isFlying();

    void setFlying(boolean fly);

    boolean isFlyingAllowed();

    void setFlyingAllowed(boolean allow);

    float getFlyingSpeed();

    void setFlyingSpeed(float speed);

    float getWalkingSpeed();

    void setWalkingSpeed(float speed);
}
