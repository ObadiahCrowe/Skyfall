package io.skyfallsdk.permission;

public abstract class Permissible<T extends PermissionHolder> {

    private final Class<T> type;

    protected Permissible(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return this.type;
    }

    public abstract boolean hasPermission(PermissionHolder requester, PermissibleAction action);
}
