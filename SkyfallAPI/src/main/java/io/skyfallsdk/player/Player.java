package io.skyfallsdk.player;

import io.skyfallsdk.entity.EntityLiving;
import io.skyfallsdk.permission.PermissionHolder;
import io.skyfallsdk.protocol.client.ClientInfo;
import io.skyfallsdk.server.CommandSender;

import java.util.UUID;

public interface Player extends EntityLiving, CommandSender, PermissionHolder {

    default String getUsername() {
        return this.getClient().getUsername();
    }

    @Override
    default UUID getUuid() {
        return this.getClient().getUuid();
    }

    ClientInfo getClient();

    PlayerProperties getProperties();
}
