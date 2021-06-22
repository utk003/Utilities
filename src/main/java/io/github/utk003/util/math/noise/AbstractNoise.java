package io.github.utk003.util.math.noise;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("NumericOverflow")
public abstract class AbstractNoise implements Noise {
    private static final long DEFAULT_SEED = 0L, APERIODIC_PERIOD = -1L;
    private static final @NotNull long[] EMPTY_LONG_ARRAY = new long[0];

    protected final long SEED;
    private final long PERIOD_D1, PERIOD_D2, PERIOD_D3, PERIOD_D4, DEFAULT_PERIOD;
    private final @NotNull long[] DEFINED_PERIODS;

    public AbstractNoise() {
        this(DEFAULT_SEED, APERIODIC_PERIOD, EMPTY_LONG_ARRAY);
    }
    public AbstractNoise(long seed) {
        this(seed, APERIODIC_PERIOD, EMPTY_LONG_ARRAY);
    }
    public AbstractNoise(long defaultPeriod, @NotNull long[] periods) {
        this(DEFAULT_SEED, defaultPeriod, periods);
    }
    public AbstractNoise(long seed, long defaultPeriod, @NotNull long[] periods) {
        // save period data
        DEFINED_PERIODS = periods;
        DEFAULT_PERIOD = defaultPeriod;

        // cache periods for first 4 dimensions
        PERIOD_D1 = getPeriod(0);
        PERIOD_D2 = getPeriod(1);
        PERIOD_D3 = getPeriod(2);
        PERIOD_D4 = getPeriod(3);

        // randomize seed
        SEED = HASH_MULTIPLIER * (HASH_MULTIPLIER * seed + 17) + 17;
    }

    // dim >= 0
    protected long getPeriod(int dim) {
        return dim < DEFINED_PERIODS.length ? DEFINED_PERIODS[dim] : DEFAULT_PERIOD;
    }

    protected static int floor(float f) {
        return f < 0.0f ? (int) f - 1 : (int) f;
    }
    protected static long floor(double d) {
        return d < 0.0 ? (long) d - 1 : (long) d;
    }

    protected static final long HASH_MULTIPLIER = -7084644459119787965L,
            HASH_MULTIPLIER_SQUARED = HASH_MULTIPLIER * HASH_MULTIPLIER,
            HASH_MULTIPLIER_CUBED = HASH_MULTIPLIER_SQUARED * HASH_MULTIPLIER,
            HASH_MULTIPLIER_FOURTH_POWER = HASH_MULTIPLIER_CUBED * HASH_MULTIPLIER;

    protected int hashKey(long key, int hashBitShift, int maximumHashValue) {
        key ^= key >>> hashBitShift;
        key = (HASH_MULTIPLIER * key) >>> hashBitShift;

        // return final hash value as properly-bounded integer
        return (int) (key % maximumHashValue);
    }

    protected long computeHashKey(long i1, long i2) {
        // normalize indices using dimensional periodicity
        if (PERIOD_D1 > 0) i1 = (i1 % PERIOD_D1 + PERIOD_D1) % PERIOD_D1;
        if (PERIOD_D2 > 0) i2 = (i2 % PERIOD_D2 + PERIOD_D2) % PERIOD_D2;

        // convert indices to hash keys
        long k1 = i1 + SEED, k2 = i2 + SEED;

        k1 ^= k1 >>> 32;
        k2 ^= k2 >>> 32;

        k1 *= HASH_MULTIPLIER;
        k2 *= HASH_MULTIPLIER_SQUARED;

        // compute final hash key
        return k1 ^ k2 + SEED;
    }
    protected long computeHashKey(long i1, long i2, long i3) {
        // normalize indices using dimensional periodicity
        if (PERIOD_D1 > 0) i1 = (i1 % PERIOD_D1 + PERIOD_D1) % PERIOD_D1;
        if (PERIOD_D2 > 0) i2 = (i2 % PERIOD_D2 + PERIOD_D2) % PERIOD_D2;
        if (PERIOD_D3 > 0) i3 = (i3 % PERIOD_D3 + PERIOD_D3) % PERIOD_D3;

        // convert indices to hash keys
        long k1 = i1 + SEED, k2 = i2 + SEED, k3 = i3 + SEED;

        k1 ^= k1 >>> 32;
        k2 ^= k2 >>> 32;
        k3 ^= k3 >>> 32;

        k1 *= HASH_MULTIPLIER;
        k2 *= HASH_MULTIPLIER_SQUARED;
        k3 *= HASH_MULTIPLIER_CUBED;

        // compute final hash key
        return k1 ^ k2 ^ k3 + SEED;
    }
    protected long computeHashKey(long i1, long i2, long i3, long i4) {
        // normalize indices using dimensional periodicity
        if (PERIOD_D1 > 0) i1 = (i1 % PERIOD_D1 + PERIOD_D1) % PERIOD_D1;
        if (PERIOD_D2 > 0) i2 = (i2 % PERIOD_D2 + PERIOD_D2) % PERIOD_D2;
        if (PERIOD_D3 > 0) i3 = (i3 % PERIOD_D3 + PERIOD_D3) % PERIOD_D3;
        if (PERIOD_D4 > 0) i4 = (i4 % PERIOD_D4 + PERIOD_D4) % PERIOD_D4;

        // convert indices to hash keys
        long k1 = i1 + SEED, k2 = i2 + SEED, k3 = i3 + SEED, k4 = i4 + SEED;

        k1 ^= k1 >>> 32;
        k2 ^= k2 >>> 32;
        k3 ^= k3 >>> 32;
        k4 ^= k4 >>> 32;

        k1 *= HASH_MULTIPLIER;
        k2 *= HASH_MULTIPLIER_SQUARED;
        k3 *= HASH_MULTIPLIER_CUBED;
        k4 *= HASH_MULTIPLIER_FOURTH_POWER;

        // compute final hash key
        return k1 ^ k2 ^ k3 ^ k4 + SEED;
    }
    protected void computeHashKeys(@NotNull long[] indices) {
        for (int i = 0; i < indices.length; i++) {
            long key = indices[i];

            // normalize indices using dimensional periodicity
            long period = getPeriod(i);
            if (period > 0) key = (key % period + period) % period;

            // convert indices to hash keys
            key += SEED;
            key ^= key >> 32;
            key *= HASH_MULTIPLIER;

            // save hash key back into array
            indices[i] = key;
        }
    }

    protected static class SeedHasher {
        private SeedHasher(long seed) {
            foldedSeed = ((int) seed ^ (int) (seed >>> 32)) & 1;
            tail = ROOT = new SeedHasher.Node(foldedSeed >>> 16);
        }

        private final int foldedSeed;
        private final @NotNull SeedHasher.Node ROOT;
        private @NotNull SeedHasher.Node tail;
        private int count = 1;

        private static class Node {
            private Node(int v) {
                val = v;
            }

            private final int val;
            private @Nullable SeedHasher.Node next = null;
        }

        public class Interator {
            private @NotNull SeedHasher.Node curr = ROOT;
            public int next() {
                int val = curr.val;
                //noinspection ConstantConditions
                curr = curr.next;
                return val;
            }
            public void reset() {
                curr = ROOT;
            }
        }

        private synchronized void calculate(int upTo) {
            while (count++ < upTo) {
                int next = tail.val + foldedSeed;
                next >>>= 16;
                next = (next * foldedSeed) >>> 16;
                tail = tail.next = new SeedHasher.Node(next);
            }
        }

        public SeedHasher.Interator getInterator(int dims) {
            int upTo = (2 * dims + 3) * (dims - 1);
            if (count < upTo) calculate(upTo);
            return new SeedHasher.Interator();
        }
    }

    private @Nullable SeedHasher HASHER;
    protected @NotNull SeedHasher getSeedHasher() {
        return HASHER == null ? HASHER = new SeedHasher(SEED) : HASHER;
    }
}
