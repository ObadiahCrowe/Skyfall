package io.skyfallsdk.bossbar;

import io.skyfallsdk.chat.ChatComponent;
import io.skyfallsdk.player.Player;

import javax.annotation.concurrent.ThreadSafe;
import java.util.UUID;

@ThreadSafe
public class SkyfallBossBar implements BossBar {

    private volatile ChatComponent title;


    @Override
    public void sendToPlayer(Player player) {

    }

    @Override
    public void removeFromPlayer(Player player) {

    }

    @Override
    public void updateTitle(ChatComponent title) {

    }

    @Override
    public void updateHealth(float health) {

    }

    @Override
    public void updateStyle(BarDivision division) {

    }

    @Override
    public void updateFlags(BarFlag... flags) {

    }

    @Override
    public void remove() {

    }

    @Override
    public UUID getUuid() {
        return null;
    }

    @Override
    public ChatComponent getTitle() {
        return this.title;
    }

    @Override
    public float getHealth() {
        return 0;
    }

    @Override
    public BarColour getColour() {
        return null;
    }

    @Override
    public BarDivision getDivision() {
        return null;
    }

    @Override
    public boolean willDarkenSky() {
        return false;
    }

    @Override
    public boolean isDragonBar() {
        return false;
    }
}
