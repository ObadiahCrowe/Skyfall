package io.skyfallsdk.entity;

public interface EntityLiving extends Entity {

    float getHealth();

    void setHealth(float health);

    float getMaxHealth();

    void setMaxHealth(float maxHealth);
}
