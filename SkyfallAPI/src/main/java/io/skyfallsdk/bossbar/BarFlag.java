package io.skyfallsdk.bossbar;

public enum BarFlag {

    /**
     * Darkens the sky when this BossBar flag is active.
     */
    DARK_SKY(0x1),

    /**
     * Plays the end music.
     */
    DRAGON_BAR(0x2),

    /**
     * Creates fog.
     */
    CREATE_FOG(0x04);

    private final byte data;

    BarFlag(int data) {
        this.data = (byte) data;
    }

    /**
     * @return The encodable data
     */
    public byte getData() {
        return this.data;
    }
}
