package io.skyfallsdk.protocol;

import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import it.unimi.dsi.fastutil.shorts.Short2IntOpenHashMap;

import java.util.Arrays;

public enum ProtocolVersion {

    v1_16_3("1.16.3", 753),
    v1_16_2("1.16.2", 738),
    v1_16_1("1.16.1", 736),
    v1_16("1.16", 735),

    v1_15_2("1.15.2", 578),
    v1_15_1("1.15.1", 575),
    v1_15("1.15", 573),

    v1_14_4("1.14.4", 498),
    v1_14_3("1.14.3", 490),
    v1_14_2("1.14.2", 485),
    v1_14_1("1.14.1", 480),
    v1_14("1.14", 477),

    v1_13_2("1.13.2", 404),
    v1_13_1("1.13.1", 401),
    v1_13("1.13", 393),

    v1_12_2("1.12.2", 340),
    v1_12_1("1.12.1", 338),
    v1_12("1.12", 335),

    v1_11_2("1.11.2", 316),
    v1_11_1("1.11.1", 316),
    v1_11("1.11", 315),

    v1_10_2("1.10.2", 210),
    v1_10_1("1.10.1", 210),
    v1_10("1.10", 210),

    v1_9_4("1.9.4", 110),
    v1_9_3("1.9.3", 110),
    v1_9_2("1.9.2", 109),
    v1_9_1("1.9.1", 108),
    v1_9("1.9", 107),

    v1_8_9("1.8.9", 47),

    UNKNOWN("Unknown", 0);

    private static final Short2IntMap PROTOCOL_TO_VERSION = new Short2IntOpenHashMap(ProtocolVersion.values().length);

    static {
        for (ProtocolVersion version : ProtocolVersion.values()) {
            PROTOCOL_TO_VERSION.put(version.getVersion(), version.ordinal());
        }
    }

    private final String name;
    private final short ver;

    ProtocolVersion(String name, int ver) {
        this.name = name;
        this.ver = (short) ver;
    }

    public String getName() {
        return this.name;
    }

    public short getVersion() {
        return this.ver;
    }

    public static ProtocolVersion getByName(String name) {
        return Arrays.stream(ProtocolVersion.values())
          .filter(version -> version.getName().equalsIgnoreCase(name))
          .findFirst()
          .orElse(ProtocolVersion.UNKNOWN);
    }

    public static ProtocolVersion getByVersion(short protocolVersion) {
        return ProtocolVersion.values()[PROTOCOL_TO_VERSION.getOrDefault(protocolVersion, 0)];
    }
}
