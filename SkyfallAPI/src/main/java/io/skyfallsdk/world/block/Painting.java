package io.skyfallsdk.world.block;

public enum Painting {

    // Whilst the protocol id can be inferred from the enum ordinal,
    // it's been done this way in case the protocol changes.

    KEBAB(0, "minecraft:kebab", 0, 0, 16, 16),
    AZTEC(1, "minecraft:aztec", 16, 0, 16, 16),
    ALBAN(2, "minecraft:alban", 32, 0, 16, 16),
    AZTEC_2(3, "minecraft:aztec2", 48, 0, 16, 16),
    BOMB(4, "minecraft:bomb", 64, 0, 16, 16),
    PLANT(5, "minecraft:plant", 80, 0, 16, 16),
    WASTELAND(6, "minecraft:wasteland", 96, 0, 16, 16),
    POOL(7, "minecraft:pool", 0, 32, 32, 16),
    COURBET(8, "minecraft:courbet", 32, 32, 32, 16),
    SEA(9, "minecraft:sea", 64, 32, 32, 16),
    SUNSET(10, "minecraft:sunset", 96, 32, 32, 16),
    CREEBET(11, "minecraft:creebet", 128, 32, 32, 16),
    WANDERER(12, "minecraft:wanderer", 0, 64, 16, 32),
    GRAHAM(13, "minecraft:graham", 16, 64, 16, 32),
    MATCH(14, "minecraft:match", 0, 128, 32, 32),
    BUST(15, "minecraft:bust", 32, 128, 32, 32),
    STAGE(16, "minecraft:stage", 64, 128, 32, 32),
    VOID(17, "minecraft:void", 96, 128, 32, 32),
    SKULL_AND_ROSES(18, "minecraft:skull_and_roses", 128, 128, 32, 32),
    WITHER(19, "minecraft:wither", 160, 128, 32, 32),
    FIGHTERS(20, "minecraft:fighters", 0, 96, 64, 32),
    POINTER(21, "minecraft:pointer", 0, 192, 64, 64),
    PIGSCENE(22, "minecraft:pigscene", 64, 192, 64, 64),
    BURNING_SKULL(23, "minecraft:burning_skull", 128, 192, 64, 64),
    SKELETON(24, "minecraft:skeleton", 192, 64, 64, 48),
    DONKEY_KONG(25, "minecraft:donkey_kong", 192, 112, 64, 48);

    private final int protocolId;
    private final String minecraftId;

    private final int x;
    private final int y;

    private final int width;
    private final int height;

    Painting(int protocolId, String minecraftId, int x, int y, int width, int height) {
        this.protocolId = protocolId;
        this.minecraftId = minecraftId;

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;
    }

    public int getProtocolId() {
        return this.protocolId;
    }

    public String getMinecraftId() {
        return this.minecraftId;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
