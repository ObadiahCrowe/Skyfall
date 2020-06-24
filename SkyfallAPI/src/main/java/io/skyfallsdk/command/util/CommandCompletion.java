package io.skyfallsdk.command.util;

import com.google.common.util.concurrent.AtomicDouble;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class CommandCompletion {

    public static List<String> createRange(int minInclusive, int maxInclusive) {
        return IntStream.range(minInclusive, maxInclusive + 1).mapToObj(Integer::toString).collect(Collectors.toList());
    }

    public static List<String> createRange(double minInclusive, double maxInclusive, int steps) {
        AtomicDouble atomicDouble = new AtomicDouble(maxInclusive);
        double step = (maxInclusive - minInclusive) / steps;
        return DoubleStream.generate(() -> atomicDouble.getAndAdd(step)).limit(steps)
                .mapToObj(Double::toString)
                .collect(Collectors.toList());
    }

    public static Collection<String> provideNone() {
        return Collections.emptyList();
    }

    public static Collection<String> provideAllOnlinePlayers() {
        return null;
    }
}
