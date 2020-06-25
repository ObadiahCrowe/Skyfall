package io.skyfallsdk.bossbar;

public enum BarAction {

    /**
     * Adds the BossBar to a player.
     */
    ADD,

    /**
     * Removes the BossBar from a player.
     */
    REMOVE,

    /**
     * Updates the BossBar's health value.
     */
    UPDATE_HEALTH,

    /**
     * Updates the BossBar's title value.
     */
    UPDATE_TITLE,

    /**
     * Updates the BossBar's style value (Amount of notches).
     */
    UPDATE_STYLE,

    /**
     * Updates the BossBar's flags.
     */
    UPDATE_FLAGS;
}
