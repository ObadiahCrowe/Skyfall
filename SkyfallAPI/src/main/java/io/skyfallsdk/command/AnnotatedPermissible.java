package io.skyfallsdk.command;

import io.skyfallsdk.command.options.Permission;
import io.skyfallsdk.permission.Permissible;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.server.CommandSender;
import io.skyfallsdk.server.ServerCommandSender;

import java.lang.reflect.Method;

public abstract class AnnotatedPermissible {

    private final Class<? extends Permissible> permissionHandler;

    public AnnotatedPermissible(Method method) {
        Permission permission = method.getAnnotation(Permission.class);
        if (permission != null) {
            this.permissionHandler = permission.permission();
        } else {
            this.permissionHandler = Rank.DEFAULT;
        }
    }

    public AnnotatedPermissible(Class<?> targetClass) {
        Permission permission = targetClass.getAnnotation(Permission.class);
        if (permission != null) {
            this.permissionHandler = permission.value();
        } else {
            this.permissionHandler = Rank.DEFAULT;
        }
    }

    public AnnotatedPermissible(Class<? extends Permissible> permissionHandler, boolean betaUnlocked) {
        this.permissionHandler = permissionHandler;
    }

    public Rank getPermissionHandler() {
        return permissionHandler;
    }

    public abstract boolean isPlayerOnly();

    public boolean hasAccess(CommandSender sender) {
        if (sender instanceof ServerCommandSender) {
            return !this.isPlayerOnly();
        }

        // We don't really want anything but console/player running our commands
        if (!(sender instanceof Player)) {
            return false;
        }

        if (CoreCommand.NO_COMMANDS.contains(((Player) sender).getUuid())) {
            return false;
        }

        if (this.getPermissionHandler() == null || this.getPermissionHandler() == Rank.DEFAULT) {
            return true;
        }

        return rank.getRank().isAtLeast(this.permissionHandler);
    }
}
