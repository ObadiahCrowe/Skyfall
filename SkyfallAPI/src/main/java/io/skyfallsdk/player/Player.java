package io.skyfallsdk.player;

import io.skyfallsdk.entity.LivingEntity;
import io.skyfallsdk.permission.PermissionHolder;
import io.skyfallsdk.server.CommandSender;

public interface Player extends LivingEntity, CommandSender, PermissionHolder {

}
