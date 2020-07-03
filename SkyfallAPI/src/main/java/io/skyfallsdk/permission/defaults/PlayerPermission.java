package io.skyfallsdk.permission.defaults;

import io.skyfallsdk.permission.PermissibleAction;

public enum PlayerPermission implements PermissibleAction {

    COMMAND_HELP("Display's the help page on all server commands."),

    ;

    private final String desc;

    PlayerPermission(String desc) {
        this.desc = desc;
    }

    @Override
    public String getDescription() {
        return this.desc;
    }
}
