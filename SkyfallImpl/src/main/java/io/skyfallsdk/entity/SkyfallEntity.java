package io.skyfallsdk.entity;

import com.google.gson.Gson;
import io.skyfallsdk.concurrent.tick.TickStage;
import io.skyfallsdk.nbt.tag.type.*;
import io.skyfallsdk.world.Position;
import io.skyfallsdk.world.SkyfallWorld;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.vector.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class SkyfallEntity implements Entity {

    private static final AtomicInteger ENTITY_ID = new AtomicInteger(0);

    private final int id;
    private final UUID uuid;

    public SkyfallEntity() {
        this.id = ENTITY_ID.incrementAndGet();
        this.uuid = UUID.randomUUID();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public World getWorld() {
        return null;
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public void setPosition(Position position) {

    }

    @Override
    public Vector getVelocity() {
        return null;
    }

    @Override
    public void setVelocity(Vector velocity) {

    }

    @Override
    public void onTick() {

    }

    @Override
    public TickStage getStage() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public static @NotNull SkyfallEntity fromTagCompound(@NotNull SkyfallWorld world, @NotNull TagCompound compound) {
        //System.out.println(new Gson().toJson(compound));

        short air = (short) compound.get("Air").getValue();

        if (compound.containsKey("CustomName")) {
            String customName = (String) compound.get("CustomName").getValue();
        }

        if (compound.containsKey("CustomNameVisible")) {
            boolean customNameVisible = (boolean) compound.get("CustomNameVisible").getValue();
        }

        float fallDistance = (float) compound.get("FallDistance").getValue();
        short fireTicks = (short) compound.get("Fire").getValue();
        boolean glowing = (boolean) compound.get("Glowing").getValue();
        System.out.println(6);
        boolean hasVisualFire = (boolean) compound.get("HasVisualFire").getValue();
        System.out.println(7);
        String entityId = (String) compound.get("Id").getValue();
        System.out.println(8);
        boolean invulnerable = (boolean) compound.get("Invulnerable").getValue();
        System.out.println(9);
        List<TagDouble> motion = ((TagList<TagDouble>) compound.get("Motion")).getValue();
        boolean noGravity = (boolean) compound.get("NoGravity").getValue();
        boolean onGround = (boolean) compound.get("OnGround").getValue();
        List<TagCompound> passengers = ((TagList<TagCompound>) compound.get("Passengers")).getValue();
        int portalCooldown = (int) compound.get("PortalCooldown").getValue();
        List<TagDouble> position = ((TagList<TagDouble>) compound.get("Pos")).getValue();
        List<TagFloat> rotation = ((TagList<TagFloat>) compound.get("Rotation")).getValue();

        if (compound.containsKey("Silent")) {
            boolean silent = (boolean) compound.get("Silent").getValue();
        }

        List<TagString> scoreboardTags = ((TagList<TagString>) compound.get("Tags")).getValue();
        int ticksFrozen = (int) compound.get("TicksFrozen").getValue();
        List<TagInt> uuid = ((TagList<TagInt>) compound.get("UUID")).getValue();

        System.out.println(uuid.toString());

        return null;
    }
}
