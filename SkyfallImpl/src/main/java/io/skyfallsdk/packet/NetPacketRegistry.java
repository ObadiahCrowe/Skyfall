package io.skyfallsdk.packet;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import io.skyfallsdk.Server;
import io.skyfallsdk.packet.version.NetPacketMapper;
import io.skyfallsdk.protocol.ProtocolVersion;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

public class NetPacketRegistry implements PacketRegistry {

    private static final Int2ReferenceMap<Class<? extends Packet>> ID_TO_PACKET = new Int2ReferenceOpenHashMap<>();
    private static final Reference2IntMap<Class<? extends Packet>> PACKET_TO_ID = new Reference2IntOpenHashMap<>();

    private static final Int2ReferenceMap<ConstructorAccess<? extends Packet>> ID_TO_CONSTRUCTOR = new Int2ReferenceOpenHashMap<>();
    private static final Int2ReferenceMap<NetPacketMapper> VERSION_TO_MAPPER = new Int2ReferenceOpenHashMap<>();

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

        // Albeit slower, but looks cleaner on the console, plus it's solely at startup.
        for (int i : VERSION_TO_MAPPER.int2ReferenceEntrySet().stream().map(Int2ReferenceMap.Entry::getIntKey).sorted(IntComparators.OPPOSITE_COMPARATOR).collect(Collectors.toList())) {
            Server.get().getLogger().info("Registered a PacketMapper for: " + ProtocolVersion.values()[i].getName());
        }
    }

    @Override
    public int getId(Class<? extends Packet> packet) {
        return 0;
    }

    @Override
    public PacketState getState(Class<? extends Packet> packet) {
        return null;
    }
}
