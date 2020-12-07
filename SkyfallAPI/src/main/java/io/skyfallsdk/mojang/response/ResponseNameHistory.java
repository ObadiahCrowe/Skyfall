package io.skyfallsdk.mojang.response;

public class ResponseNameHistory {

    private final String name;
    private final long changedToAt;

    public ResponseNameHistory(String name, long changedAt) {
        this.name = name;
        this.changedToAt = changedAt;
    }

    public String getName() {
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
