package io.skyfallsdk.world.meta;

import io.skyfallsdk.concurrent.tick.*;
import io.skyfallsdk.util.Validator;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SkyfallWeather implements Weather, Tickable<DefaultTickSpec> {

    private final ReentrantReadWriteLock lock;

    private WeatherState currentState;

    private int clearDuration;
    private int rainingDuration;
    private int thunderingDuration;

    public SkyfallWeather(@NotNull WeatherState state, int clearDuration, int rainingDuration, int thunderingDuration) {
        this.lock = new ReentrantReadWriteLock(true);

        Validator.notNull(state);
        this.currentState = state;

        this.clearDuration = clearDuration;
        this.rainingDuration = rainingDuration;
        this.thunderingDuration = thunderingDuration;
    }

    @Override
    public boolean isClear() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.currentState == WeatherState.CLEAR;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setClear(boolean clear) {
        Lock lock = this.lock.writeLock();

        try {
            lock.lock();

            this.currentState = clear ? WeatherState.CLEAR : WeatherState.RAINING;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isRaining() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.currentState == WeatherState.RAINING;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setRaining(boolean raining) {
        Lock lock = this.lock.writeLock();

        try {
            lock.lock();

            this.currentState = raining ? WeatherState.RAINING : WeatherState.CLEAR;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isThundering() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.currentState == WeatherState.THUNDERING;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setThundering(boolean thundering) {
        Lock lock = this.lock.writeLock();

        try {
            lock.lock();

            this.currentState = thundering ? WeatherState.THUNDERING : WeatherState.CLEAR;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getClearDuration() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.clearDuration;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getRainingDuration() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.rainingDuration;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getThunderDuration() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.thunderingDuration;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public @NotNull WeatherState getCurrentWeather() {
        Lock lock = this.lock.readLock();

        try {
            lock.lock();

            return this.currentState;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void onTick() {
        Lock lock = this.lock.writeLock();

        try {
            lock.lock();

            switch (this.currentState) {
                case CLEAR:
                    this.clearDuration -= 20L;
                    break;
                case RAINING:
                    this.rainingDuration -= 20L;
                    break;
                case THUNDERING:
                    this.thunderingDuration -= 20L;
                    break;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public TickRegistry<DefaultTickSpec> getRegistry() {
        return ServerTickRegistry.getTickRegistry(DefaultTickSpec.WORLD);
    }

    @Override
    public TickStage getStage() {
        return TickStage.POST;
    }
}
