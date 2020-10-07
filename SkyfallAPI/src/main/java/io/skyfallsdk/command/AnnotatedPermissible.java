package io.skyfallsdk.command;

import io.skyfallsdk.command.options.Permission;
import io.skyfallsdk.player.Player;
import io.skyfallsdk.server.CommandSender;
import io.skyfallsdk.server.ServerCommandSender;

import java.lang.reflect.Method;

public abstract class AnnotatedPermissible {

    private final String permission;

    public AnnotatedPermissible(Method method) {
        Permission permission = method.getAnnotation(Permission.class);
        if (permission != null) {
            this.permission = permission.value();
        } else {
            this.permission = "";
        }
    }

    public AnnotatedPermissible(Class<?> targetClass) {
        Permission permission = targetClass.getAnnotation(Permission.class);
        if (permission != null) {
            this.permission = permission.value();
        } else {
            this.permission = "";
        }
    }

    public String getPermission() {
        return this.permission;
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

        if (Command.NO_COMMANDS.contains(((Player) sender).getUuid())) {
            return false;
        }

        if (this.getPermission() == null || this.getPermission().isEmpty()) {
            return true;
        }

        return sender.hasPermission(this.permission);
    }
}
