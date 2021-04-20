package io.skyfallsdk.world;

import java.util.concurrent.atomic.AtomicLongArray;

public class NibbleArray {

    private static final int REQUIRED_SIZE = 2048;

    public static final NibbleArray EMPTY_ARRAY = new NibbleArray(new long[REQUIRED_SIZE]);

    private final AtomicLongArray backingArray;

    public NibbleArray(long[] array) {
        if (array.length != REQUIRED_SIZE) {
            throw new IllegalArgumentException("NibbleArray's must be " + REQUIRED_SIZE + " bytes, not: " + array.length);
        }

        this.backingArray = new AtomicLongArray(array);

    }
}
