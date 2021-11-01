package io.skyfallsdk.player;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.entity.EntityType;
import io.skyfallsdk.entity.SkyfallEntity;
import io.skyfallsdk.inventory.type.entity.EntityInventory;
import io.skyfallsdk.net.NetClient;
import io.skyfallsdk.world.option.Gamemode;
import org.jetbrains.annotations.NotNull;

public class SkyfallPlayer extends SkyfallEntity implements Player {
    @Override
    public EntityType getType() {
        return null;
    }

    @Override
    public EntityInventory getInventory() {
        return null;
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
        return null;
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
