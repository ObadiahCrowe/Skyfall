package io.skyfallsdk.command.util;

import java.util.Date;
import java.util.Objects;

public class Duration {

    private final long millis;

    public Duration(String simple) {
        this(UtilTime.toMillis(simple));
    }

    public Duration(long millis) {
        this.millis = millis;
    }

    public String toSentence() {
        return UtilTime.toSentence(this.millis);
    }

    public long getMillis() {
        return millis;
    }

    public Date toDate() {
        return new Date(System.currentTimeMillis() + this.getMillis());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Duration duration = (Duration) o;
        return millis == duration.millis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(millis);
    }

    @Override
    public String toString() {
        return "Duration{" +
                "millis=" + millis +
                '}';
    }
}
