package io.skyfallsdk.player;

import io.skyfallsdk.Server;
import io.skyfallsdk.SkyfallServer;
import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.config.PerformanceConfig;
import io.skyfallsdk.entity.EntityType;
import io.skyfallsdk.entity.SkyfallEntity;
import io.skyfallsdk.inventory.SkyfallPlayerInventory;
import io.skyfallsdk.inventory.type.entity.EntityInventory;
import io.skyfallsdk.inventory.type.entity.PlayerInventory;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.world.Position;
import io.skyfallsdk.world.SkyfallWorld;
import io.skyfallsdk.world.World;
import io.skyfallsdk.world.option.Gamemode;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SkyfallPlayer extends SkyfallEntity implements Player {

    private final NetClient client;
    private final PlayerProperties properties;

    private final SkyfallPlayerInventory inventory;

    private Position position;

    public SkyfallPlayer(@NotNull NetClient client, @NotNull PlayerProperties properties) {
        this.client = client;
        this.properties = properties;

        this.inventory = new SkyfallPlayerInventory(this);
    }

    public void spawn() {
        Server.get().getWorldLoader().get("world").thenAccept(potential -> {
            SkyfallWorld first = (SkyfallWorld) potential.orElseGet(() -> Server.get().getWorldLoader().getLoadedWorlds().toArray(World[]::new)[0]);
            if (first == null) {
                throw new IllegalStateException("No worlds have been loaded! Cannot spawn an entity.");
            }

            System.out.println("found first: " + first.getName());

            this.position = new Position(first, this.position.getX(), this.position.getY(), this.position.getZ());

            first.addToWorld(this);

            int chunkX = this.position.getChunkX();
            int chunkZ = this.position.getChunkZ();

            PerformanceConfig config = ((SkyfallServer) Server.get()).getPerformanceConfig();

            int radius = (int) Math.max(7, Math.sqrt(config.getInitialChunkCache()));

            for (int x = chunkX - radius; x < chunkX + radius; x++) {
                for (int z = chunkZ - radius; z < chunkZ + radius; z++) {
                    first.getChunk(chunkX, chunkZ, true).thenApply(chunk -> {

                        return chunk;
                    });
                }
            }
        });
    }

    @Override
    public float getHealth() {
        return 0;
    }

    @Override
    public void setHealth(float health) {

    }

    @Override
    public float getMaxHealth() {
        return 0;
    }

    @Override
    public void setMaxHealth(float maxHealth) {

    }

    @Override
    public void addPermission(@NotNull String permission) {

    }

    @Override
    public void removePermission(@NotNull String permission) {

    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return false;
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public void setOp(boolean op) {

    }

    @Override
    public @NotNull NetClient getClient() {
        return this.client;
    }

    @Override
    public @NotNull Gamemode getGamemode() {
        return null;
    }

    @Override
    public @NotNull PlayerProperties getProperties() {
        return null;
    }

    @Override
    public SkyfallPlayerInventory getInventory() {
        return this.inventory;
    }

    @Override
    public boolean isInvulnerable() {
        return false;
    }

    @Override
    public void setInvulnerable(boolean invulnerable) {

    }

    @Override
    public boolean isFlying() {
        return false;
    }

    @Override
    public void setFlying(boolean fly) {

    }

    @Override
    public boolean isFlyingAllowed() {
        return false;
    }

    @Override
    public void setFlyingAllowed(boolean allow) {

    }

    @Override
    public float getFlyingSpeed() {
        return 0;
    }

    @Override
    public void setFlyingSpeed(float speed) {

    }

    @Override
    public float getWalkingSpeed() {
        return 0;
    }

    @Override
    public void setWalkingSpeed(float speed) {

    }

    @Override
    public void sendMessage(@NotNull ChatComponent component) {

    }

    @Override
    public void executeCommand(@NotNull String command) {

    }
}
