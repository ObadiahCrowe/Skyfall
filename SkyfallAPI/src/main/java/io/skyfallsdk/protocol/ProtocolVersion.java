package io.skyfallsdk.protocol;

import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import it.unimi.dsi.fastutil.shorts.Short2IntOpenHashMap;

import java.util.Arrays;

public enum ProtocolVersion {

    v1_18("1.18", 757, 2860),

    v1_17_1("1.17.1", 756, 2730),
    v1_17("1.17", 755, 2724),

    v1_16_5("1.16.5", 754, 2586),
    v1_16_4("1.16.4", 754, 2584),
    v1_16_3("1.16.3", 753, 2580),
    v1_16_2("1.16.2", 738, 2578),
    v1_16_1("1.16.1", 736, 2567),
    v1_16("1.16", 735, 2566),

    v1_15_2("1.15.2", 578, 2230),
    v1_15_1("1.15.1", 575, 2227),
    v1_15("1.15", 573, 2225),

    v1_14_4("1.14.4", 498, 1976),
    v1_14_3("1.14.3", 490, 1968),
    v1_14_2("1.14.2", 485, 1963),
    v1_14_1("1.14.1", 480, 1957),
    v1_14("1.14", 477, 1952),

    v1_13_2("1.13.2", 404, 1631),
    v1_13_1("1.13.1", 401, 1628),
    v1_13("1.13", 393, 1519),

    v1_12_2("1.12.2", 340, 1343),
    v1_12_1("1.12.1", 338, 1241),
    v1_12("1.12", 335, 1139),

    v1_11_2("1.11.2", 316, 922),
    v1_11_1("1.11.1", 316, 921),
    v1_11("1.11", 315, 819),

    v1_10_2("1.10.2", 210, 512),
    v1_10_1("1.10.1", 210, 511),
    v1_10("1.10", 210, 510),

    v1_9_4("1.9.4", 110, 184),
    v1_9_3("1.9.3", 110, 183),
    v1_9_2("1.9.2", 109, 176),
    v1_9_1("1.9.1", 108, 175),
    v1_9("1.9", 107, 169),

    v1_8_9("1.8.9", 47, 0),

    UNKNOWN("Unknown", 0, 0);

    private static final Short2IntMap PROTOCOL_TO_VERSION = new Short2IntOpenHashMap(ProtocolVersion.values().length);

    static {
        for (ProtocolVersion version : ProtocolVersion.values()) {
            PROTOCOL_TO_VERSION.put(version.getVersion(), version.ordinal());
        }
    }

    private final String name;
    private final short ver;
    private final int dataVersion;

    ProtocolVersion(String name, int ver, int dataVersion) {
        this.name = name;
        this.ver = (short) ver;
        this.dataVersion = dataVersion;
    }

    public String getName() {
        return this.name;
    }

    public short getVersion() {
        return this.ver;
    }

    public int getDataVersion() {
        return this.dataVersion;
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
