package io.skyfallsdk.permission.permissible;

import io.skyfallsdk.permission.Permissible;
import io.skyfallsdk.permission.PermissibleAction;
import io.skyfallsdk.permission.PermissionHolder;
import io.skyfallsdk.server.CommandSender;

public class ServerPermissible extends Permissible<CommandSender> {

    public ServerPermissible() {
        super(CommandSender.class);
    }

    @Override
    public boolean hasPermission(PermissionHolder requester, PermissibleAction action) {
        return true;
    }
}
