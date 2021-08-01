package io.github.utk003.util.math.noise;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ScheduledForRelease(inVersion = "v3.0.0")
@RequiresDocumentation
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

    protected static final long HASH_MULTIPLIER = -7084644459119787965L;

    protected int hashComputedKey(long hashKey, int hashBitShift, int maximumHashValue) {
        hashKey ^= hashKey >>> hashBitShift;
        hashKey = (HASH_MULTIPLIER * hashKey) >>> hashBitShift;

        // return final hash value as properly-bounded integer
        return (int) (hashKey % maximumHashValue);
    }

    private long hashKeyComputationHelper(long key, long period) {
        // normalize indices using dimensional periodicity
        if (period > 0) key = (key % period + period) % period;

        // convert indices to hash keys
        key += SEED;
        key ^= key >>> 32;
        key *= HASH_MULTIPLIER;

        // compute final hash key
        return key;
    }

    protected long computeHashKey(long i1) {
        // get hash key intermediate
        long k1 = hashKeyComputationHelper(i1, PERIOD_D1);

        // compute final hash key
        return k1 + SEED;
    }
    protected long computeHashKey(long i1, long i2) {
        // get hash key intermediates
        long k1 = hashKeyComputationHelper(i1, PERIOD_D1);
        long k2 = hashKeyComputationHelper(i2, PERIOD_D2);

        // compute final hash key
        return k1 ^ k2 + SEED;
    }
    protected long computeHashKey(long i1, long i2, long i3) {
        // get hash key intermediates
        long k1 = hashKeyComputationHelper(i1, PERIOD_D1);
        long k2 = hashKeyComputationHelper(i2, PERIOD_D2);
        long k3 = hashKeyComputationHelper(i3, PERIOD_D3);

        // compute final hash key
        return k1 ^ k2 ^ k3 + SEED;
    }
    protected long computeHashKey(long i1, long i2, long i3, long i4) {
        // get hash key intermediates
        long k1 = hashKeyComputationHelper(i1, PERIOD_D1);
        long k2 = hashKeyComputationHelper(i2, PERIOD_D2);
        long k3 = hashKeyComputationHelper(i3, PERIOD_D3);
        long k4 = hashKeyComputationHelper(i4, PERIOD_D4);

        // compute final hash key
        return k1 ^ k2 ^ k3 ^ k4 + SEED;
    }
    protected void computeHashKeys(@NotNull long[] indices) {
        for (int i = 0; i < indices.length; i++)
            indices[i] = hashKeyComputationHelper(indices[i], getPeriod(i));
    }

    protected static class SeedHasher {
        private SeedHasher(long seed) {
            foldedSeed = ((int) seed ^ (int) (seed >>> 32)) & 1;
            tail = ROOT = new Node(foldedSeed);
        }

        private final int foldedSeed;
        private final @NotNull Node ROOT;
        private @NotNull Node tail;
        private int count = 1;

        private static class Node {
            private static final int MASK = (1 << 16) - 1;
            private Node(int v) {
                val = (v & MASK) ^ (v >>> 16);
            }

            private final int val;
            private @Nullable Node next = null;
        }

        public class Interator {
            private @NotNull Node curr = ROOT;
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
                int next = tail.val + (tail.val << 16);
                next = (next ^ foldedSeed) * foldedSeed;
                tail = tail.next = new Node(next);
            }
        }

        public Interator getInterator(int dims) {
            int upTo = (2 * dims + 3) * (dims - 1);
            if (count < upTo) calculate(upTo);
            return new Interator();
        }
    }

    private @Nullable SeedHasher HASHER;
    protected @NotNull SeedHasher getSeedHasher() {
        return HASHER == null ? HASHER = new SeedHasher(SEED) : HASHER;
    }
}
