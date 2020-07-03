package io.skyfallsdk.command.options;

import io.skyfallsdk.permission.Permissible;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionRequirement {

    Class<? extends Permissible> value();
}
