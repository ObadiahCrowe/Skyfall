package io.skyfallsdk.packet;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.google.common.reflect.ClassPath;
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

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

public class PacketRegistry {

    private static final Int2ReferenceMap<Class<? extends Packet>> ID_TO_PACKET = new Int2ReferenceOpenHashMap<>();
    private static final Reference2IntMap<Class<? extends Packet>> PACKET_TO_ID = new Reference2IntOpenHashMap<>();

    private static final Int2ReferenceMap<ConstructorAccess<? extends Packet>> ID_TO_CONSTRUCTOR = new Int2ReferenceOpenHashMap<>();
    private static final Int2ReferenceMap<NetPacketMapper> VERSION_TO_MAPPER = new Int2ReferenceOpenHashMap<>();

    private static final int VERSION_SHIFT = 26;
    private static final int DESTINATION_SHIFT = 19;
    private static final int STATE_SHIFT = 17;

    private static final boolean IS_DEBUGGING;

    static {
        Server.get().getLogger().info("Preparing registration of PacketMappers..");

        try {
            ClassPath.from(PacketRegistry.class.getClassLoader()).getTopLevelClassesRecursive("io.skyfallsdk.packet.version").forEach(info -> {
                Class<?> raw = info.load();

                if (NetPacketMapper.class.isAssignableFrom(raw) && raw != NetPacketMapper.class) {
                    try {
                        NetPacketMapper instance = (NetPacketMapper) raw.getConstructor().newInstance();
                        if (!Server.get().getSupportedVersions().contains(instance.getFrom())) {
                            return;
                        }

                        VERSION_TO_MAPPER.put(instance.getFrom().ordinal(), instance);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                        Server.get().getLogger().error("An error occurred whilst registering a PacketMapper. ", e);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Server.get().getLogger().error("An error occurred whilst registering a PacketMapper. ", e);
        }

        IS_DEBUGGING = ((SkyfallServer) Server.get()).getConfig().isDebugEnabled();

        // Albeit slower, but looks cleaner on the console, plus it's solely at startup.
        if (IS_DEBUGGING) {
            for (int i : VERSION_TO_MAPPER.int2ReferenceEntrySet().stream().map(Int2ReferenceMap.Entry::getIntKey).sorted(IntComparators.OPPOSITE_COMPARATOR).collect(Collectors.toList())) {
                Server.get().getLogger().debug("Registered a PacketMapper for: " + ProtocolVersion.values()[i].getName());
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
            Server.get().getLogger().debug("Attempting to find packet with the following criteria: " + version + ", " +
              state + ", " + destination + ", 0x" + Integer.toHexString(packetId));
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
        if (IS_DEBUGGING) {
            Server.get().getLogger().info("Registering packet data for: " + packetClass.getCanonicalName() + " - (" +
              version.name() + ", " + state.name() + ", " + destination.name() + ", 0x" + Integer.toHexString(packetId) + ")");
        }

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
            Server.get().getLogger().fatal("Caught packet: " + packetClass.getCanonicalName() +
              "\n Expected Id: " + packetId + " - Got: " + getId(packetClass) +
              "\n Expected Version: " + version + " - Got: " + getProtocolVersion(packetClass) +
              "\n Expected State: " + state + " - Got: " + getState(packetClass) +
              "\n Expected Destination: " + destination + " - Got: " + getDestination(packetClass));
            e.printStackTrace();
            System.exit(0);
            return null;
        }

        if (destination == PacketDestination.IN) {
            Validator.notNull(constructor);

            ID_TO_CONSTRUCTOR.put(id, constructor);
            return constructor.newInstance();
        }

/*        try {
            ClassPool classPool = ClassPool.getDefault();

            CtClass netPacketClass = ClassPool.getDefault().getCtClass(packetClass.getCanonicalName());
            netPacketClass.addConstructor(CtNewConstructor.defaultConstructor(netPacketClass));


            CtClass superClass = netPacketClass.getSuperclass();
            CtClass newPacket = classPool.makeClass(packetClass.getCanonicalName() + "2");
            CtConstructor newConstructor = CtNewConstructor.make("public " + newPacket.getName() + "() {}", newPacket);

            newPacket.addConstructor(newConstructor);
            newPacket.setSuperclass(netPacketClass);


            NetPacketOut packetOut = (NetPacketOut) newPacket.toClass().newInstance();

            System.out.println("generic1: " + packetOut.getGeneric().getCanonicalName());

            Class<?> rawPacket = ClassPool.getDefault().toClass(netPacketClass);

            System.out.println("got raw: " + rawPacket.getCanonicalName());

            Class<? extends NetPacketOut> clazz = (Class<? extends NetPacketOut>) netPacketClass.toClass();

            System.out.println(clazz.getCanonicalName());
            NetPacketOut packet = clazz.getConstructor().newInstance();

            System.out.println("generic: " + packet.getGeneric().getCanonicalName());

        } catch (CannotCompileException | NotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }*/

        return null;
        // TODO: 09/12/2020 Sort this out for outgoing packets

/*        else {
            if (constructor == null) {
                throw new IllegalArgumentException("Constructor cannot be null for outgoing packet.");
            }

            ID_TO_CONSTRUCTOR.put(id, constructor);
        }

        return constructor.newInstance();*/
    }

    private static int shift(ProtocolVersion version, PacketState state, PacketDestination destination, int packetId) {
        int id = packetId;

        id |= state.ordinal() << STATE_SHIFT;
        id |= destination.ordinal() << DESTINATION_SHIFT;
        id |= version.ordinal() << VERSION_SHIFT;

        return id;
    }

    public static int getData(Class<? extends Packet> packet) {
        return PACKET_TO_ID.getInt(packet);
    }

    public static int getId(Class<? extends Packet> packet) {
        return PACKET_TO_ID.getInt(packet) & 0xFFFF;
    }

    public static PacketDestination getDestination(Class<? extends Packet> packet) {
        return PacketDestination.values()[(PACKET_TO_ID.getInt(packet) >> DESTINATION_SHIFT) & 0x1];
    }

    public static PacketState getState(Class<? extends Packet> packet) {
        return PacketState.values()[(PACKET_TO_ID.getInt(packet) >> STATE_SHIFT) & 0x03];
    }

    public static ProtocolVersion getProtocolVersion(Class<? extends Packet> packet) {
        return ProtocolVersion.values()[PACKET_TO_ID.getInt(packet) >>> VERSION_SHIFT];
    }
}
