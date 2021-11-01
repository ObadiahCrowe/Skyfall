package io.skyfallsdk.world.meta;

import org.jetbrains.annotations.NotNull;

public interface Weather {

    boolean isClear();

    void setClear(boolean clear);

    boolean isRaining();

    void setRaining(boolean raining);

    boolean isThundering();

    void setThundering(boolean thundering);

    int getClearDuration();

    int getRainingDuration();

    int getThunderDuration();

    @NotNull
    WeatherState getCurrentWeather();
}
