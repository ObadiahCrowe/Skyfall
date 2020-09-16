package io.skyfallsdk.permission.permissible;

import io.skyfallsdk.permission.Permissible;
import io.skyfallsdk.permission.PermissibleAction;
import io.skyfallsdk.permission.PermissionHolder;
import io.skyfallsdk.player.Player;

public class PlayerPermissible extends Permissible<Player> {

    public PlayerPermissible() {
        super(Player.class);
    }

    @Override
    public boolean hasPermission(PermissionHolder requester, PermissibleAction action) {
        return false;
    }
}
