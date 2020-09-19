package io.skyfallsdk.permission;

public interface PermissionHolder {

    void addPermission(String permission);

    void removePermission(String permission);

    boolean hasPermission(String permission);
}
