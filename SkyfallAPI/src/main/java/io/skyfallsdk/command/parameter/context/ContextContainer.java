package io.skyfallsdk.command.parameter.context;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ContextContainer<T> {

    private final Class<T> tClass;
    private T value;
    private long expiry;

    public ContextContainer(Class<T> tClass) {
        this.tClass = tClass;
        this.expiry = -1;
    }

    public void instantiate() {
        if (this.getValue() != null && !this.isExpired()) {
            return;
        }

        try {
            this.value = this.tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public long getExpiry() {
        return this.expiry;
    }

    public void setAndExpireIn(T value, TimeUnit unit, long amount) {
        this.setValue(value);
        this.expireIn(unit, amount);
    }

    public void expireIn(TimeUnit unit, long amount) {
        this.expiry = System.currentTimeMillis() + unit.toMillis(amount);
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    public boolean isExpired() {
        return this.getExpiry() > 0 && System.currentTimeMillis() < this.getExpiry();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ContextContainer)) {
            return false;
        }

        ContextContainer that = (ContextContainer) o;
        return this.expiry == that.expiry &&
          Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value, this.expiry);
    }

    @Override
    public String toString() {
        return "CommandContextContainer{" +
          "value=" + value +
          ", expiry=" + expiry +
          '}';
    }
}