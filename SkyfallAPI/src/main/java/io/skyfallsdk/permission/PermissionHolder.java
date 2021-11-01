package io.skyfallsdk.permission;

import org.jetbrains.annotations.NotNull;

public interface PermissionHolder {

    void addPermission(@NotNull String permission);

    void removePermission(@NotNull String permission);

    boolean hasPermission(@NotNull String permission);

    boolean isOp();

    void setOp(boolean op);
}
