package io.skyfallsdk.permission;

public interface PermissionHolder {

    void addPermission(PermissibleAction permission);

    void removePermission(PermissibleAction permission);

    boolean hasPermission(PermissibleAction permission);
}
