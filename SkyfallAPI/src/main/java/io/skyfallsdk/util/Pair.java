package io.skyfallsdk.util;

import java.util.Objects;

public final class Pair<A, B> {

    private final A type1;
    private final B type2;

    public Pair(A type1, B type2) {
        this.type1 = type1;
        this.type2 = type2;
    }

    public A getType1() {
        return this.type1;
    }

    public B getType2() {
        return this.type2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(type1, pair.type1) &&
          Objects.equals(type2, pair.type2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type1, type2);
    }

    @Override
    public String toString() {
        return "Pair{" +
          "type1=" + type1 +
          ", type2=" + type2 +
          '}';
    }
}
