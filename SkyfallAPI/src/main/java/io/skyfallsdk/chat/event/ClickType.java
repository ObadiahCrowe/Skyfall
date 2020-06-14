package io.skyfallsdk.chat.event;

public enum ClickType {

    /**
     * Displays the open url dialog to a player.
     */
    OPEN_URL("open_url"),

    /**
     * Runs a specific command found on the server.
     */
    RUN_COMMAND("run_command"),

    /**
     * Fills the player's chat bar with a command suggestion.
     */
    SUGGEST_COMMAND("suggest_command"),

    /**
     * Changes the page of a book.
     */
    CHANGE_PAGE("change_page");

    private final String key;

    ClickType(String key) {
        this.key = key;
    }

    /**
     * @return The Minecraft protocol-name of this type.
     */
    public String getKey() {
        return this.key;
    }
}
