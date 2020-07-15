package io.skyfallsdk.command.options;

import io.skyfallsdk.permission.PermissibleAction;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    Class<? extends Enum<? extends PermissibleAction>> value();

    String name();

    PermissionRequirement permission();
}
