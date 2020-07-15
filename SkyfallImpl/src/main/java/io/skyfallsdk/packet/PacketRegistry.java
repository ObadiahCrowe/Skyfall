package io.skyfallsdk.packet;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;

public class PacketRegistry {

    private static final Int2ReferenceMap<Class<? extends Packet>> ID_TO_PACKET = new Int2ReferenceOpenHashMap<>();
    private static final Reference2IntMap<Class<? extends Packet>> PACKET_TO_ID = new Reference2IntOpenHashMap<>();

    private static final Int2ReferenceMap<ConstructorAccess<? extends Packet>> ID_TO_CONSTRUCTOR = new Int2ReferenceOpenHashMap<>();


}
