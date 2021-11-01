package io.skyfallsdk.world;

import org.jetbrains.annotations.NotNull;

public interface WorldBorder {

    @NotNull
    World getWorld();

    @NotNull
    Position getCenter();

    void setCenter(@NotNull Position center);

    double getDamage();

    void setDamage(double damage);

    double getDamageBuffer();

    void setDamageBuffer(double damageBuffer);

    double getDiameterChangeSpeed();

    void setDiameterChangeSpeed(double speed);

    double getDiameter();

    default void setDiameter(double diameter) {
        this.setDiameter(diameter, false);
    }

    void setDiameter(double diameter, boolean immediateChange);

    double getWarningDistance();

    void setWarningDistance(double warningDistance);

    double getWarningDelay();

    void setWarningDelay(double warningDelay);
}
