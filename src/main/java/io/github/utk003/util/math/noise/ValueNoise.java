package io.github.utk003.util.math.noise;

import io.github.utk003.util.math.interp.Interpolatable;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ScheduledForRelease(inVersion = "v3.0.0")
@RequiresDocumentation
public final class ValueNoise extends AbstractNoise implements Noise, Interpolatable {
    public ValueNoise() {
        super();
    }
    public ValueNoise(long seed) {
        super(seed);
    }
    public ValueNoise(long defaultPeriod, @NotNull long[] periods) {
        super(defaultPeriod, periods);
    }
    public ValueNoise(long seed, long defaultPeriod, @NotNull long[] periods) {
        super(seed, defaultPeriod, periods);
    }

    private static final int LOG_RANDOM_NUMBERS_ARRAY_SIZE = 8, RANDOM_NUMBERS_ARRAY_SIZE = 1 << LOG_RANDOM_NUMBERS_ARRAY_SIZE;
    private static final @NotNull float[] RANDOM_FLOATS = new float[RANDOM_NUMBERS_ARRAY_SIZE];
    private static final @NotNull double[] RANDOM_DOUBLES = new double[RANDOM_NUMBERS_ARRAY_SIZE];
    static {
        double max = RANDOM_NUMBERS_ARRAY_SIZE - 1.0;
        for (int i = 0; i < RANDOM_NUMBERS_ARRAY_SIZE; i++) {
            double value = 2.0 * i / max - 1.0;

            RANDOM_FLOATS[i] = (float) value;
            RANDOM_DOUBLES[i] = value;
        }
    }

    private static final int RANDOM_NUMBERS_HASH_SHIFT = 64 - LOG_RANDOM_NUMBERS_ARRAY_SIZE;
    private int computeRandomIndex(long i1) {
        long key = computeHashKey(i1);
        return hashComputedKey(key, RANDOM_NUMBERS_HASH_SHIFT, RANDOM_NUMBERS_ARRAY_SIZE);
    }
    private int computeRandomIndex(long i1, long i2) {
        long key = computeHashKey(i1, i2);
        return hashComputedKey(key, RANDOM_NUMBERS_HASH_SHIFT, RANDOM_NUMBERS_ARRAY_SIZE);
    }
    private int computeRandomIndex(long i1, long i2, long i3) {
        long key = computeHashKey(i1, i2, i3);
        return hashComputedKey(key, RANDOM_NUMBERS_HASH_SHIFT, RANDOM_NUMBERS_ARRAY_SIZE);
    }
    private int computeRandomIndex(long i1, long i2, long i3, long i4) {
        long key = computeHashKey(i1, i2, i3, i4);
        return hashComputedKey(key, RANDOM_NUMBERS_HASH_SHIFT, RANDOM_NUMBERS_ARRAY_SIZE);
    }
    private int computeRandomIndex(@NotNull long[] indices) {
        computeHashKeys(indices);
        long hashKey = 0;
        for (long key : indices)
            hashKey ^= key;
        return hashComputedKey(hashKey + SEED, RANDOM_NUMBERS_HASH_SHIFT, LOG_RANDOM_NUMBERS_ARRAY_SIZE);
    }

    private @NotNull InterpolationType interpolationType = InterpolationType.DEFAULT;
    @Override
    public @NotNull InterpolationType getInterpolationType() {
        return interpolationType;
    }
    @Override
    @Contract("_ -> this")
    public @NotNull ValueNoise setInterpolationType(@NotNull InterpolationType interpolationType) {
        this.interpolationType = interpolationType;
        return this;
    }

    private float interpolate(float e0, float e1, float weight) {
        return interpolationType.interpolate(e0, e1, weight);
    }
    private double interpolate(double e0, double e1, double weight) {
        return interpolationType.interpolate(e0, e1, weight);
    }

    @Override
    public float get(float x) {
        // x0 = floor, x1 = ciel
        int x0 = floor(x), x1 = x0 + 1;

        // hash corners for indices
        int ind0 = computeRandomIndex(x0), ind1 = computeRandomIndex(x1);

        // get random numbers
        float r0 = RANDOM_FLOATS[ind0], r1 = RANDOM_FLOATS[ind1];

        // get interpolation weight
        float wx = x - x0;

        // interpolate & return result
        return interpolate(r0, r1, wx); // x varies
    }
    @Override
    public double get(double x) {
        // x0 = floor, x1 = ciel
        long x0 = floor(x), x1 = x0 + 1;

        // hash corners for indices
        int ind0 = computeRandomIndex(x0), ind1 = computeRandomIndex(x1);

        // get random numbers
        double r0 = RANDOM_DOUBLES[ind0], r1 = RANDOM_DOUBLES[ind1];

        // get interpolation weight
        double wx = x - x0;

        // interpolate & return result
        return interpolate(r0, r1, wx); // x varies
    }

    @Override
    public float get(float x, float y) {
        // x0 = floor, x1 = ciel
        int x0 = floor(x), x1 = x0 + 1;
        int y0 = floor(y), y1 = y0 + 1;

        // hash corners for indices
        int ind00 = computeRandomIndex(x0, y0),
                ind01 = computeRandomIndex(x0, y1),
                ind10 = computeRandomIndex(x1, y0),
                ind11 = computeRandomIndex(x1, y1);

        // get random numbers
        float r00 = RANDOM_FLOATS[ind00],
                r01 = RANDOM_FLOATS[ind01],
                r10 = RANDOM_FLOATS[ind10],
                r11 = RANDOM_FLOATS[ind11];

        // get interpolation weights
        float wx = x - x0, wy = y - y0;

        // interpolate & return result
        float ix0 = interpolate(r00, r10, wx), // y = 0 fixed, x varies
                ix1 = interpolate(r01, r11, wx); // y = 1 fixed, x varies

        return interpolate(ix0, ix1, wy); // x = wx fixed, y varies
    }
    @Override
    public double get(double x, double y) {
        // x0 = floor, x1 = ciel
        long x0 = floor(x), x1 = x0 + 1;
        long y0 = floor(y), y1 = y0 + 1;

        // hash corners for indices
        int ind00 = computeRandomIndex(x0, y0),
                ind01 = computeRandomIndex(x0, y1),
                ind10 = computeRandomIndex(x1, y0),
                ind11 = computeRandomIndex(x1, y1);

        // get random values
        double r00 = RANDOM_DOUBLES[ind00],
                r01 = RANDOM_DOUBLES[ind01],
                r10 = RANDOM_DOUBLES[ind10],
                r11 = RANDOM_DOUBLES[ind11];

        // get interpolation weights
        double wx = x - x0, wy = y - y0;

        // interpolate & return result
        double ix0 = interpolate(r00, r10, wx), // y = 0 fixed, x varies
                ix1 = interpolate(r01, r11, wx); // y = 1 fixed, x varies

        return interpolate(ix0, ix1, wy); // x = wx fixed, y varies
    }

    @Override
    public float get(float x, float y, float z) {
        // x0 = floor, x1 = ciel
        int x0 = floor(x), x1 = x0 + 1;
        int y0 = floor(y), y1 = y0 + 1;
        int z0 = floor(z), z1 = z0 + 1;

        // hash corners for indices
        int ind000 = computeRandomIndex(x0, y0, z0),
                ind001 = computeRandomIndex(x0, y0, z1),
                ind010 = computeRandomIndex(x0, y1, z0),
                ind011 = computeRandomIndex(x0, y1, z1),
                ind100 = computeRandomIndex(x1, y0, z0),
                ind101 = computeRandomIndex(x1, y0, z1),
                ind110 = computeRandomIndex(x1, y1, z0),
                ind111 = computeRandomIndex(x1, y1, z1);

        // get random values
        float r000 = RANDOM_FLOATS[ind000],
                r001 = RANDOM_FLOATS[ind001],
                r010 = RANDOM_FLOATS[ind010],
                r011 = RANDOM_FLOATS[ind011],
                r100 = RANDOM_FLOATS[ind100],
                r101 = RANDOM_FLOATS[ind101],
                r110 = RANDOM_FLOATS[ind110],
                r111 = RANDOM_FLOATS[ind111];

        // get interpolation weights
        float wx = x - x0, wy = y - y0, wz = z - z0;

        // interpolate & return result
        float ix00 = interpolate(r000, r100, wx), // y = 0 & z = 0 fixed, x varies
                ix01 = interpolate(r001, r101, wx), // y = 0 & z = 1 fixed, x varies
                ix10 = interpolate(r010, r110, wx), // y = 1 & z = 0 fixed, x varies
                ix11 = interpolate(r011, r111, wx); // y = 1 & z = 1 fixed, x varies

        float ixy0 = interpolate(ix00, ix10, wy), // x = wx & z = 0 fixed, y varies
                ixy1 = interpolate(ix01, ix11, wy); // x = wx & z = 1 fixed, y varies

        return interpolate(ixy0, ixy1, wz); // x = wx & y = wy fixed, z varies
    }
    @Override
    public double get(double x, double y, double z) {
        // x0 = floor, x1 = ciel
        long x0 = floor(x), x1 = x0 + 1;
        long y0 = floor(y), y1 = y0 + 1;
        long z0 = floor(z), z1 = z0 + 1;

        // hash corners for indices
        int ind000 = computeRandomIndex(x0, y0, z0),
                ind001 = computeRandomIndex(x0, y0, z1),
                ind010 = computeRandomIndex(x0, y1, z0),
                ind011 = computeRandomIndex(x0, y1, z1),
                ind100 = computeRandomIndex(x1, y0, z0),
                ind101 = computeRandomIndex(x1, y0, z1),
                ind110 = computeRandomIndex(x1, y1, z0),
                ind111 = computeRandomIndex(x1, y1, z1);

        // get random values
        double r000 = RANDOM_DOUBLES[ind000],
                r001 = RANDOM_DOUBLES[ind001],
                r010 = RANDOM_DOUBLES[ind010],
                r011 = RANDOM_DOUBLES[ind011],
                r100 = RANDOM_DOUBLES[ind100],
                r101 = RANDOM_DOUBLES[ind101],
                r110 = RANDOM_DOUBLES[ind110],
                r111 = RANDOM_DOUBLES[ind111];

        // get interpolation weights
        double wx = x - x0, wy = y - y0, wz = z - z0;

        // interpolate & return result
        double ix00 = interpolate(r000, r100, wx), // y = 0 & z = 0 fixed, x varies
                ix01 = interpolate(r001, r101, wx), // y = 0 & z = 1 fixed, x varies
                ix10 = interpolate(r010, r110, wx), // y = 1 & z = 0 fixed, x varies
                ix11 = interpolate(r011, r111, wx); // y = 1 & z = 1 fixed, x varies

        double ixy0 = interpolate(ix00, ix10, wy), // x = wx & z = 0 fixed, y varies
                ixy1 = interpolate(ix01, ix11, wy); // x = wx & z = 1 fixed, y varies

        return interpolate(ixy0, ixy1, wz); // x = wx & y = wy fixed, z varies
    }

    @Override
    public float get(float x, float y, float z, float w) {
        // x0 = floor, x1 = ciel
        int x0 = floor(x), x1 = x0 + 1;
        int y0 = floor(y), y1 = y0 + 1;
        int z0 = floor(z), z1 = z0 + 1;
        int w0 = floor(w), w1 = w0 + 1;

        // hash corners for indices
        int ind0000 = computeRandomIndex(x0, y0, z0, w0),
                ind0001 = computeRandomIndex(x0, y0, z0, w1),
                ind0010 = computeRandomIndex(x0, y0, z1, w0),
                ind0011 = computeRandomIndex(x0, y0, z1, w1),
                ind0100 = computeRandomIndex(x0, y1, z0, w0),
                ind0101 = computeRandomIndex(x0, y1, z0, w1),
                ind0110 = computeRandomIndex(x0, y1, z1, w0),
                ind0111 = computeRandomIndex(x0, y1, z1, w1),
                ind1000 = computeRandomIndex(x1, y0, z0, w0),
                ind1001 = computeRandomIndex(x1, y0, z0, w1),
                ind1010 = computeRandomIndex(x1, y0, z1, w0),
                ind1011 = computeRandomIndex(x1, y0, z1, w1),
                ind1100 = computeRandomIndex(x1, y1, z0, w0),
                ind1101 = computeRandomIndex(x1, y1, z0, w1),
                ind1110 = computeRandomIndex(x1, y1, z1, w0),
                ind1111 = computeRandomIndex(x1, y1, z1, w1);

        // get random values
        float r0000 = RANDOM_FLOATS[ind0000],
                r0001 = RANDOM_FLOATS[ind0001],
                r0010 = RANDOM_FLOATS[ind0010],
                r0011 = RANDOM_FLOATS[ind0011],
                r0100 = RANDOM_FLOATS[ind0100],
                r0101 = RANDOM_FLOATS[ind0101],
                r0110 = RANDOM_FLOATS[ind0110],
                r0111 = RANDOM_FLOATS[ind0111],
                r1000 = RANDOM_FLOATS[ind1000],
                r1001 = RANDOM_FLOATS[ind1001],
                r1010 = RANDOM_FLOATS[ind1010],
                r1011 = RANDOM_FLOATS[ind1011],
                r1100 = RANDOM_FLOATS[ind1100],
                r1101 = RANDOM_FLOATS[ind1101],
                r1110 = RANDOM_FLOATS[ind1110],
                r1111 = RANDOM_FLOATS[ind1111];

        // get interpolation weights
        float wx = x - x0, wy = y - y0, wz = z - z0, ww = w - w0;

        // interpolate & return result
        float ix000 = interpolate(r0000, r1000, wx), // y = 0 & z = 0 & w = 0 fixed, x varies
                ix001 = interpolate(r0001, r1001, wx), // y = 0 & z = 0 & w = 1 fixed, x varies
                ix010 = interpolate(r0010, r1010, wx), // y = 0 & z = 1 & w = 0 fixed, x varies
                ix011 = interpolate(r0011, r1011, wx), // y = 0 & z = 1 & w = 1 fixed, x varies
                ix100 = interpolate(r0100, r1100, wx), // y = 1 & z = 0 & w = 0 fixed, x varies
                ix101 = interpolate(r0101, r1101, wx), // y = 1 & z = 0 & w = 1 fixed, x varies
                ix110 = interpolate(r0110, r1110, wx), // y = 1 & z = 1 & w = 0 fixed, x varies
                ix111 = interpolate(r0111, r1111, wx); // y = 1 & z = 1 & w = 1 fixed, x varies

        float ixy00 = interpolate(ix000, ix100, wy), // x = wx & z = 0 & w = 0 fixed, y varies
                ixy01 = interpolate(ix001, ix101, wy), // x = wx & z = 0 & w = 1 fixed, y varies
                ixy10 = interpolate(ix010, ix110, wy), // x = wx & z = 1 & w = 0 fixed, y varies
                ixy11 = interpolate(ix011, ix111, wy); // x = wx & z = 1 & w = 1 fixed, y varies

        float ixyz0 = interpolate(ixy00, ixy10, wz), // x = wx & y = wy & w = 0 fixed, z varies
                ixyz1 = interpolate(ixy01, ixy11, wz); // x = wx & y = wy & w = 1 fixed, z varies

        return interpolate(ixyz0, ixyz1, ww); // x = wx & y = wy & z = wz fixed, w varies
    }
    @Override
    public double get(double x, double y, double z, double w) {
        // x0 = floor, x1 = ciel
        long x0 = floor(x), x1 = x0 + 1;
        long y0 = floor(y), y1 = y0 + 1;
        long z0 = floor(z), z1 = z0 + 1;
        long w0 = floor(w), w1 = w0 + 1;

        // hash corners for indices
        int ind0000 = computeRandomIndex(x0, y0, z0, w0),
                ind0001 = computeRandomIndex(x0, y0, z0, w1),
                ind0010 = computeRandomIndex(x0, y0, z1, w0),
                ind0011 = computeRandomIndex(x0, y0, z1, w1),
                ind0100 = computeRandomIndex(x0, y1, z0, w0),
                ind0101 = computeRandomIndex(x0, y1, z0, w1),
                ind0110 = computeRandomIndex(x0, y1, z1, w0),
                ind0111 = computeRandomIndex(x0, y1, z1, w1),
                ind1000 = computeRandomIndex(x1, y0, z0, w0),
                ind1001 = computeRandomIndex(x1, y0, z0, w1),
                ind1010 = computeRandomIndex(x1, y0, z1, w0),
                ind1011 = computeRandomIndex(x1, y0, z1, w1),
                ind1100 = computeRandomIndex(x1, y1, z0, w0),
                ind1101 = computeRandomIndex(x1, y1, z0, w1),
                ind1110 = computeRandomIndex(x1, y1, z1, w0),
                ind1111 = computeRandomIndex(x1, y1, z1, w1);

        // get random values
        double r0000 = RANDOM_DOUBLES[ind0000],
                r0001 = RANDOM_DOUBLES[ind0001],
                r0010 = RANDOM_DOUBLES[ind0010],
                r0011 = RANDOM_DOUBLES[ind0011],
                r0100 = RANDOM_DOUBLES[ind0100],
                r0101 = RANDOM_DOUBLES[ind0101],
                r0110 = RANDOM_DOUBLES[ind0110],
                r0111 = RANDOM_DOUBLES[ind0111],
                r1000 = RANDOM_DOUBLES[ind1000],
                r1001 = RANDOM_DOUBLES[ind1001],
                r1010 = RANDOM_DOUBLES[ind1010],
                r1011 = RANDOM_DOUBLES[ind1011],
                r1100 = RANDOM_DOUBLES[ind1100],
                r1101 = RANDOM_DOUBLES[ind1101],
                r1110 = RANDOM_DOUBLES[ind1110],
                r1111 = RANDOM_DOUBLES[ind1111];

        // get interpolation weights
        double wx = x - x0, wy = y - y0, wz = z - z0, ww = w - w0;

        // interpolate & return result
        double ix000 = interpolate(r0000, r1000, wx), // y = 0 & z = 0 & w = 0 fixed, x varies
                ix001 = interpolate(r0001, r1001, wx), // y = 0 & z = 0 & w = 1 fixed, x varies
                ix010 = interpolate(r0010, r1010, wx), // y = 0 & z = 1 & w = 0 fixed, x varies
                ix011 = interpolate(r0011, r1011, wx), // y = 0 & z = 1 & w = 1 fixed, x varies
                ix100 = interpolate(r0100, r1100, wx), // y = 1 & z = 0 & w = 0 fixed, x varies
                ix101 = interpolate(r0101, r1101, wx), // y = 1 & z = 0 & w = 1 fixed, x varies
                ix110 = interpolate(r0110, r1110, wx), // y = 1 & z = 1 & w = 0 fixed, x varies
                ix111 = interpolate(r0111, r1111, wx); // y = 1 & z = 1 & w = 1 fixed, x varies

        double ixy00 = interpolate(ix000, ix100, wy), // x = wx & z = 0 & w = 0 fixed, y varies
                ixy01 = interpolate(ix001, ix101, wy), // x = wx & z = 0 & w = 1 fixed, y varies
                ixy10 = interpolate(ix010, ix110, wy), // x = wx & z = 1 & w = 0 fixed, y varies
                ixy11 = interpolate(ix011, ix111, wy); // x = wx & z = 1 & w = 1 fixed, y varies

        double ixyz0 = interpolate(ixy00, ixy10, wz), // x = wx & y = wy & w = 0 fixed, z varies
                ixyz1 = interpolate(ixy01, ixy11, wz); // x = wx & y = wy & w = 1 fixed, z varies

        return interpolate(ixyz0, ixyz1, ww); // x = wx & y = wy & z = wz fixed, w varies
    }

    @Override
    public float get(@NotNull float[] pos) {
        int dim = pos.length;

        // p[0] = floor, p[1] = ciel
        int[][] p = new int[dim][2];
        for (int i = 0; i < dim; i++) {
            p[i][0] = floor(pos[i]);
            p[i][1] = p[i][0] + 1;
        }

        long[] cornerIndices = new long[dim];
        float[] r = new float[1 << dim];
        for (int i = 0; i < r.length; i++) {
            // compute corners from index bits
            int bits = i;
            for (int ind = dim - 1; ind >= 0; ind--) {
                cornerIndices[ind] = p[ind][bits & 1];
                bits >>>= 1;
            }

            // hash corners for index
            int randomIndex = computeRandomIndex(cornerIndices);

            // save random value into r
            r[i] = RANDOM_FLOATS[randomIndex];
        }

        // interpolate & return result
        int c = 0; // iteration count
        for (int i = r.length >>> 1; i > 0; i >>>= 1) {
            float interpolationWeight = pos[c] - p[c][0];
            for (int j = 0; j < i; j++)
                r[j] = interpolate(r[j], r[j + i], interpolationWeight);
            c++;
        }

        // return result
        return r[0];
    }
    @Override
    public double get(@NotNull double[] pos) {
        int dim = pos.length;

        // p[0] = floor, p[1] = ciel
        long[][] p = new long[dim][2];
        for (int i = 0; i < dim; i++) {
            p[i][0] = floor(pos[i]);
            p[i][1] = p[i][0] + 1;
        }

        long[] cornerIndices = new long[dim];
        double[] r = new double[1 << dim];
        for (int i = 0; i < r.length; i++) {
            // compute corners from index bits
            int bits = i;
            for (int ind = dim - 1; ind >= 0; ind--) {
                cornerIndices[ind] = p[ind][bits & 1];
                bits >>>= 1;
            }

            // hash corners for index
            int randomIndex = computeRandomIndex(cornerIndices);

            // save random value into r
            r[i] = RANDOM_DOUBLES[randomIndex];
        }

        // interpolate & return result
        int c = 0; // iteration count
        for (int i = r.length >>> 1; i > 0; i >>>= 1) {
            double interpolationWeight = pos[c] - p[c][0];
            for (int j = 0; j < i; j++)
                r[j] = interpolate(r[j], r[j + i], interpolationWeight);
            c++;
        }

        // return result
        return r[0];
    }

    public static @NotNull ValueNoise aperiodic() {
        return new ValueNoise();
    }
    public static @NotNull ValueNoise aperiodic(long seed) {
        return new ValueNoise(seed);
    }

    public static @NotNull ValueNoise periodic(long defaultPeriod, @NotNull long... periods) {
        return new ValueNoise(defaultPeriod, periods);
    }
    public static @NotNull ValueNoise periodic(long seed, long defaultPeriod, @NotNull long... periods) {
        return new ValueNoise(seed, defaultPeriod, periods);
    }
}
