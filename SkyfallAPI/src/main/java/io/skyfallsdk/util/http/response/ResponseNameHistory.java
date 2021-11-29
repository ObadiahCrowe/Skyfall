package io.skyfallsdk.util.http.response;

import org.jetbrains.annotations.NotNull;

public class ResponseNameHistory {

    private final String name;
    private final long changedToAt;

    public ResponseNameHistory(@NotNull String name, long changedAt) {
        this.name = name;
        this.changedToAt = changedAt;
    }

    public ResponseNameHistory(@NotNull String name) {
        this.name = name;
        this.changedToAt = 0;
    }

    public @NotNull String getName() {
        return this.name;
    }

    public long getChangedAt() {
        return this.changedToAt;
    }

    public boolean isCurrentName() {
        return this.changedToAt == 0;
    }

    @Override
    public String toString() {
        return "ResponseNameHistory{" +
          "name='" + name + '\'' +
          ", changedToAt=" + changedToAt +
          '}';
    }
}
