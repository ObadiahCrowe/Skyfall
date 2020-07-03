package io.skyfallsdk.permission.permissible;

import io.skyfallsdk.permission.Permissible;
import io.skyfallsdk.permission.PermissibleAction;
import io.skyfallsdk.player.Player;

public class PlayerPermissible extends Permissible<Player> {

    @Override
    public boolean hasPermission(Player requester, PermissibleAction action) {
        return requester.hasPermission(action);
    }
}
