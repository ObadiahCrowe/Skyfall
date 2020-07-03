package io.skyfallsdk.permission;

public abstract class Permissible<T extends PermissionHolder> {

    protected Permissible() {
        //
    }

    public abstract boolean hasPermission(T requester, PermissibleAction action);
}
