package io.skyfallsdk.packet;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import io.skyfallsdk.Server;
import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.packet.exception.PacketException;
import io.skyfallsdk.packet.version.NetPacketMapper;
import io.skyfallsdk.protocol.ProtocolVersion;
import io.skyfallsdk.util.Validator;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import org.reflections.Reflections;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;


public class PacketRegistry {

    private static final Int2ReferenceMap<Class<? extends Packet>> ID_TO_PACKET = new Int2ReferenceOpenHashMap<>();
    private static final Reference2IntMap<Class<? extends Packet>> PACKET_TO_ID = new Reference2IntOpenHashMap<>();

    private static final Int2ReferenceMap<ConstructorAccess<? extends Packet>> ID_TO_CONSTRUCTOR = new Int2ReferenceOpenHashMap<>();
    private static final Int2ReferenceMap<NetPacketMapper> VERSION_TO_MAPPER = new Int2ReferenceOpenHashMap<>();

    private static final int VERSION_SHIFT = 29;
    private static final int DESTINATION_SHIFT = 27;
    private static final int STATE_SHIFT = 24;

    private static final boolean IS_DEBUGGING;

    static {
        Server.get().getLogger().info("Preparing registration of PacketMappers..");
        Reflections reflections = new Reflections("io.skyfallsdk.packet.version");
        Set<Class<? extends NetPacketMapper>> mappers = reflections.getSubTypesOf(NetPacketMapper.class);

        for (Class<? extends NetPacketMapper> mapper : mappers) {
            try {
                NetPacketMapper instance = mapper.getConstructor().newInstance();
                if (!Server.get().getSupportedVersions().contains(instance.getFrom())) {
                    continue;
                }

                VERSION_TO_MAPPER.put(instance.getFrom().ordinal(), instance);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                //e.printStackTrace();
                Server.get().getLogger().error("An error occurred whilst registering a PacketMapper. ", e);
            }
        }

        IS_DEBUGGING = ((SkyfallServer) Server.get()).getConfig().isDebugEnabled();

        // Albeit slower, but looks cleaner on the console, plus it's solely at startup.
        if (IS_DEBUGGING) {
            for (int i : VERSION_TO_MAPPER.int2ReferenceEntrySet().stream().map(Int2ReferenceMap.Entry::getIntKey).sorted(IntComparators.OPPOSITE_COMPARATOR).collect(Collectors.toList())) {
                Server.get().getLogger().info("Registered a PacketMapper for: " + ProtocolVersion.values()[i].getName());
            }
        }

        Server.get().getLogger().info("Registered PacketMappers successfully!");
    }

    private PacketRegistry() {}

    @SuppressWarnings("unchecked")
    public static <T extends Packet> T newInstance(Class<T> packetClass) {
        return (T) ID_TO_CONSTRUCTOR.get(PACKET_TO_ID.getInt(packetClass)).newInstance();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Packet> T newInstance(ProtocolVersion version, PacketState state, PacketDestination destination, int packetId) {
        if (IS_DEBUGGING) {
            Server.get().getLogger().debug("Attempting to find packet with the following criteria: " + version + ", " + state + ", " + destination + ", 0x" + Integer.toHexString(packetId));
        }

        int id = shift(version, state, destination, packetId);

        if (IS_DEBUGGING) {
            boolean contains = ID_TO_PACKET.containsKey(id);

            Server.get().getLogger().debug("Created packet data id: " + id);
            Server.get().getLogger().debug("Data id maps to packet? " + contains);
            if (contains) {
                Server.get().getLogger().debug("Found packet class: " + ID_TO_PACKET.get(id).getCanonicalName());
            }
        }

        if (!ID_TO_PACKET.containsKey(id)) {
            throw new PacketException(packetId, "Unknown packet.");
        }

        return (T) ID_TO_CONSTRUCTOR.get(id).newInstance();
    }

    public static <T extends Packet> T register(Class<T> packetClass, ProtocolVersion version, PacketState state, PacketDestination destination, int packetId) {
        return register(packetClass, version, state, destination, packetId, ConstructorAccess.get(packetClass));
    }

    public static <T extends Packet> T register(Class<T> packetClass, ProtocolVersion version, PacketState state,
                                                PacketDestination destination, int packetId, @Nullable ConstructorAccess<T> constructor) {
        int id = shift(version, state, destination, packetId);

        ID_TO_PACKET.put(id, packetClass);
        PACKET_TO_ID.put(packetClass, id);

        try {
            Validator.isEqualTo(packetId, getId(packetClass));
            Validator.isEqualTo(version, getProtocolVersion(packetClass));
            Validator.isEqualTo(state, getState(packetClass));
            Validator.isEqualTo(destination, getDestination(packetClass));
        } catch (Validator.ValidationException e) {
            Server.get().getLogger().fatal("Encoded packet data mismatch during registration. This indicates a severe issue. Please contact a developer immediately.");
            Server.get().getLogger().fatal(e);
            System.exit(0);
            return null;
        }

        if (destination == PacketDestination.IN) {
            ID_TO_CONSTRUCTOR.put(id, ConstructorAccess.get(packetClass));
        } else {
            if (constructor == null) {
                throw new IllegalArgumentException("Constructor cannot be null for outgoing packet.");
            }

            ID_TO_CONSTRUCTOR.put(id, constructor);
        }

        return constructor.newInstance();
    }

    private static int shift(ProtocolVersion version, PacketState state, PacketDestination destination, int packetId) {
        int id = packetId;

        id |= version.ordinal() << VERSION_SHIFT;
        id |= destination.ordinal() << DESTINATION_SHIFT;
        id |= state.ordinal() << STATE_SHIFT;

        return id;
    }

    public static int getData(Class<? extends Packet> packet) {
        return PACKET_TO_ID.getInt(packet);
    }

    public static int getId(Class<? extends Packet> packet) {
        return PACKET_TO_ID.getInt(packet) & 0x4FFFF;
    }

    public static PacketDestination getDestination(Class<? extends Packet> packet) {
        return PacketDestination.values()[PACKET_TO_ID.getInt(packet) >> DESTINATION_SHIFT & 0x1];
    }

    public static PacketState getState(Class<? extends Packet> packet) {
        return PacketState.values()[PACKET_TO_ID.getInt(packet) >> STATE_SHIFT & 0x7];
    }

    public static ProtocolVersion getProtocolVersion(Class<? extends Packet> packet) {
        return ProtocolVersion.values()[PACKET_TO_ID.getInt(packet) >> VERSION_SHIFT & 0x1F];
    }
}
