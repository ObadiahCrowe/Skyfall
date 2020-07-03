package io.skyfallsdk.permission.permissible;

import io.skyfallsdk.permission.Permissible;
import io.skyfallsdk.permission.PermissibleAction;
import io.skyfallsdk.server.CommandSender;

public class ServerPermissible extends Permissible<CommandSender> {

    @Override
    public boolean hasPermission(CommandSender requester, PermissibleAction action) {
        return true;
    }
}
