package io.skyfallsdk.chat;

import it.unimi.dsi.fastutil.chars.Char2IntArrayMap;
import it.unimi.dsi.fastutil.chars.Char2IntMap;

public enum ChatFormat {

    /**
     * Indicates that the ChatComponent is bold.
     */
    BOLD('l'),

    /**
     * Indicates that the ChatComponent is italicised.
     */
    ITALIC('o'),

    /**
     * Indicates that the ChatComponent is underlined.
     */
    UNDERLINED('n'),

    /**
     * Indicates that the ChatComponent is struckthrough.
     */
    STRIKETHROUGH('m'),

    /**
     * Indicates that the ChatComponent is obfuscated. Alternative name is 'magic'.
     */
    OBFUSCATED('k'),

    /**
     * Resets the ChatComponent's format.
     */
    RESET('r');

    private static final Char2IntMap CHAR_TO_FORMAT = new Char2IntArrayMap(ChatFormat.values().length);

    static {
        for (ChatFormat format : ChatFormat.values()) {
            CHAR_TO_FORMAT.put(format.code, format.ordinal());
        }
    }

    private final char code;

    ChatFormat(char code) {
        this.code = code;
    }

    /**
     * @return The Minecraft colour code of this format.
     */
    public char getCode() {
        return this.code;
    }

    /**
     * Obtains a ChatFormat type by the corresponding Minecraft colour code.
     *
     * @param code Colour code.
     *
     * @return The corresponding ChatFormat.
     */
    public static ChatFormat getByCode(char code) {
        int index = CHAR_TO_FORMAT.getOrDefault(code, -1);

        return index == -1 ? null : ChatFormat.values()[index];
    }
}
