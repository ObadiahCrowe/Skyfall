package io.skyfallsdk.world;

import io.skyfallsdk.concurrent.PoolSpec;
import io.skyfallsdk.concurrent.ThreadPool;
import io.skyfallsdk.util.Validator;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SkyfallWorldBorder implements WorldBorder {

    private final ReadWriteLock lock;

    private final SkyfallWorld world;

    private Position center;
    private double damage;
    private double damageBuffer;

    private double diameterChangeSpeed;
    private double diameter;

    private double warningDistance;
    private double warningDelay;

    private ScheduledFuture<?> changeDiameter;

    public SkyfallWorldBorder(SkyfallWorld world, Position center, double damage, double damageBuffer, double diameterChangeSpeed,
                              double diameter, double warningDistance, double warningDelay) {
        this.lock = new ReentrantReadWriteLock(true);

        this.world = world;

        this.center = center;
        this.damage = damage;
        this.damageBuffer = damageBuffer;

        this.diameterChangeSpeed = diameterChangeSpeed;
        this.diameter = diameter;

        this.warningDistance = warningDistance;
        this.warningDelay =  warningDelay;

        this.changeDiameter = null;
    }

    @Override
    public @NotNull World getWorld() {
        return this.world;
    }

    @Override
    public @NotNull Position getCenter() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.center;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setCenter(@NotNull Position center) {
        Validator.notNull(center);

        Lock lock = this.lock.writeLock();
        try {
            lock.lock();

            this.center = center;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public double getDamage() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.damage;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setDamage(double damage) {
        Lock lock = this.lock.writeLock();

        try {
            lock.lock();

            this.damage = damage;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public double getDamageBuffer() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.damageBuffer;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setDamageBuffer(double damageBuffer) {
        Lock lock = this.lock.writeLock();

        try {
            lock.lock();

            this.damageBuffer = damageBuffer;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public double getDiameterChangeSpeed() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.diameterChangeSpeed;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setDiameterChangeSpeed(double speed) {
        Lock lock = this.lock.writeLock();

        try {
            lock.lock();

            this.diameterChangeSpeed = speed;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public double getDiameter() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.diameter;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setDiameter(double diameter, boolean immediateChange) {
        Lock lock = this.lock.writeLock();
        final double previousDiameter = this.diameter;

        try {
            lock.lock();

            this.diameter = diameter;
        } finally {
            lock.unlock();

            if (!immediateChange) {
                if (this.changeDiameter != null) {
                    this.changeDiameter.cancel(true);
                }

                this.changeDiameter = ThreadPool.createForSpec(PoolSpec.WORLD).scheduleAtFixedRate(() -> {
                    // TODO: 18/10/2021 Set border per tick
                    if (this.diameter != previousDiameter) {

                    }
                }, 0, 1, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public double getWarningDistance() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.warningDistance;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setWarningDistance(double warningDistance) {
        Lock lock = this.lock.writeLock();

        try {
            lock.lock();

            this.warningDistance = warningDistance;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public double getWarningDelay() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.warningDelay;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setWarningDelay(double warningDelay) {
        Lock lock = this.lock.writeLock();

        try {
            lock.lock();

            this.warningDelay = warningDelay;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "SkyfallWorldBorder{" +
          "lock=" + lock +
          ", world=" + world +
          ", center=" + center +
          ", damage=" + damage +
          ", damageBuffer=" + damageBuffer +
          ", diameterChangeSpeed=" + diameterChangeSpeed +
          ", diameter=" + diameter +
          ", warningDistance=" + warningDistance +
          ", warningDelay=" + warningDelay +
          ", changeDiameter=" + changeDiameter +
          '}';
    }
}
