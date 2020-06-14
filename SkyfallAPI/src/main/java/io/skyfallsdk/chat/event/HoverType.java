package io.skyfallsdk.chat.event;

public enum HoverType {

    /**
     * Shows a ChatComponent above the text.
     */
    SHOW_TEXT("show_text"),

    /**
     * Shows an item above the text.
     */
    SHOW_ITEM("show_item"),

    /**
     * Shows an entity above the text.
     */
    SHOW_ENTITY("show_entity");

    private final String key;

    HoverType(String key) {
        this.key = key;
    }

    /**
     * @return The Minecraft protocol-name of this type.
     */
    public String getKey() {
        return this.key;
    }
}
