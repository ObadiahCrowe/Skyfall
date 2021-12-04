package io.skyfallsdk.world.block.entity;

import com.google.common.collect.Maps;
import io.skyfallsdk.world.block.entity.type.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public enum BlockEntityType {

    BANNER("minecraft:banner", BlockEntityBanner.class),
    BARREL("minecraft:barrel", BlockEntityBarrel.class),
    BEACON("minecraft:beacon", BlockEntityBeacon.class),
    BED("minecraft:bed", BlockEntityBed.class),
    BEEHIVE("minecraft:beehive", BlockEntityBeehive.class),
    BELL("minecraft:bell", BlockEntityBell.class),
    BLAST_FURNACE("minecraft:blast_furnace", BlockEntityBlastFurnace.class),
    BREWING_STAND("minecraft:brewing_stand", BlockEntityBrewingStand.class),
    CAMPFIRE("minecraft:campfire", BlockEntityCampfire.class),
    CHEST("minecraft:chest", BlockEntityChest.class),
    COMPARATOR("minecraft:comparator", BlockEntityComparator.class),
    COMMAND_BLOCK("minecraft:command_block", BlockEntityCommandBlock.class),
    CONDUIT("minecraft:conduit", BlockEntityConduit.class),
    DAYLIGHT_DETECTOR("minecraft:daylight_detector", BlockEntityDaylightDetector.class),
    DISPENSER("minecraft:dispenser", BlockEntityDispenser.class),
    DROPPER("minecraft:dropper", BlockEntityDropper.class),
    ENCHANTING_TABLE("minecraft:enchanting_table", BlockEntityEnchantingTable.class),
    ENDER_CHEST("minecraft:ender_chest", BlockEntityEnderChest.class),
    END_GATEWAY("minecraft:end_gateway", BlockEntityEndGateway.class),
    END_PORTAL("minecraft:end_portal", BlockEntityEndPortal.class),
    FURNACE("minecraft:furnace", BlockEntityFurnace.class),
    HOPPER("minecraft:hopper", BlockEntityHopper.class),
    JIGSAW("minecraft:jigsaw", BlockEntityJigsaw.class),
    JUKEBOX("minecraft:jukebox", BlockEntityJukebox.class),
    LECTERN("minecraft:lectern", BlockEntityLectern.class),
    SPAWNER("minecraft:spawner", BlockEntitySpawner.class),
    PISTON("minecraft:piston", BlockEntityPiston.class),
    SHULKER_BOX("minecraft:shulker_box", BlockEntityShulkerBox.class),
    SIGN("minecraft:sign", BlockEntitySign.class),
    SKULL("minecraft:skull", BlockEntitySkull.class),
    SMOKER("minecraft:smoker", BlockEntitySmoker.class),
    SOUL_CAMPFIRE("minecraft:soul_campfire", BlockEntitySoulCampfire.class),
    STRUCTURE_BLOCK("minecraft:structure_block", BlockEntityStructure.class),
    TRAPPED_CHEST("minecraft:trapped_chest", BlockEntityTrappedChest.class);

    private static final Map<String, BlockEntityType> NAMESPACE_TO_TYPE = Maps.newHashMap();

    static {
        for (BlockEntityType type : BlockEntityType.values()) {
            NAMESPACE_TO_TYPE.put(type.getId(), type);
        }
    }

    private final String id;
    private final Class<? extends BlockEntity> entityClass;

    <T extends BlockEntity<?>> BlockEntityType(@NotNull String id, @NotNull Class<T> entityClass) {
        this.id = id;
        this.entityClass = entityClass;
    }

    @NotNull
    public String getId() {
        return this.id;
    }

    @NotNull
    public Class<? extends BlockEntity> getEntityClass() {
        return this.entityClass;
    }

    public static @Nullable BlockEntityType getByNamespaced(@NotNull String namespacedId) {
        return NAMESPACE_TO_TYPE.getOrDefault(namespacedId, null);
    }
}
