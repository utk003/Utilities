package io.github.utk003.util.math;

import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
@RequiresDocumentation
public abstract class Pow2 {
    private Pow2() {
    }

    // smallest power of 2 greater than n
    public static int upperBound(int n) {
        if (n <= 1) return 1;
        n -= 1;

        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;

        return n + 1;
    }
    // smallest power of 2 greater than n
    public static long upperBound(long n) {
        if (n <= 1) return 1;
        n -= 1;

        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        n |= n >> 32;

        return n + 1;
    }

    // largest power of 2 less than n
    public static int lowerBound(int n) {
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;

        return n ^ (n >> 1);
    }
    // largest power of 2 less than n
    public static long lowerBound(long n) {
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        n |= n >> 32;

        return n ^ (n >> 1);
    }
}
