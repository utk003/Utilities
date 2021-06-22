package io.github.utk003.util.math.noise;

import io.github.utk003.util.math.interp.LinearStepInterpolation;
import io.github.utk003.util.math.interp.SmoothStepInterpolation;
import io.github.utk003.util.math.interp.SmootherStepInterpolation;
import org.jetbrains.annotations.NotNull;

// https://en.wikipedia.org/wiki/Perlin_noise
public final class PerlinNoise extends GradientNoise implements Noise {
    public PerlinNoise() {
        super();
    }
    public PerlinNoise(long seed) {
        super(seed);
    }

    public PerlinNoise(long defaultPeriod, @NotNull long[] periods) {
        super(defaultPeriod, periods);
    }
    public PerlinNoise(long seed, long defaultPeriod, @NotNull long[] periods) {
        super(seed, defaultPeriod, periods);
    }

    public enum PerlinInterpolation {
        LINEAR_STEP {
            @Override
            public float interpolate(float e0, float e1, float weight) {
                return LinearStepInterpolation.interpolate(e0, e1, weight);
            }
            @Override
            public double interpolate(double e0, double e1, double weight) {
                return LinearStepInterpolation.interpolate(e0, e1, weight);
            }
        },
        SMOOTH_STEP {
            @Override
            public float interpolate(float e0, float e1, float weight) {
                return SmoothStepInterpolation.interpolate(e0, e1, weight);
            }
            @Override
            public double interpolate(double e0, double e1, double weight) {
                return SmoothStepInterpolation.interpolate(e0, e1, weight);
            }
        },
        SMOOTHER_STEP {
            @Override
            public float interpolate(float e0, float e1, float weight) {
                return SmootherStepInterpolation.interpolate(e0, e1, weight);
            }
            @Override
            public double interpolate(double e0, double e1, double weight) {
                return SmootherStepInterpolation.interpolate(e0, e1, weight);
            }
        };

        public abstract float interpolate(float e0, float e1, float weight);
        public abstract double interpolate(double e0, double e1, double weight);
    }

    private @NotNull PerlinInterpolation interpolationType = PerlinInterpolation.SMOOTH_STEP;
    public @NotNull PerlinInterpolation getInterpolationType() {
        return interpolationType;
    }
    public void setInterpolationType(@NotNull PerlinInterpolation interpolationType) {
        this.interpolationType = interpolationType;
    }

    protected float interpolate(float e0, float e1, float weight) {
        return interpolationType.interpolate(e0, e1, weight);
    }
    protected double interpolate(double e0, double e1, double weight) {
        return interpolationType.interpolate(e0, e1, weight);
    }

    @Override
    public float get(float x) {
        // x0 = floor, x1 = ciel
        int x0 = floor(x), x1 = x0 + 1;

        // compute distances
        float dx0 = x - x0, dx1 = x - x1;

        // skip "hash corners for indices", "get gradients" & "compute dot products"
        // -- gradients are always <1.0f>
        // -- dp0 = dx0, dp1 = dx1

        // get interpolation weights
        float wx = x - x0;

        // interpolate/return result
        return interpolate(dx0, dx1, wx);
    }
    @Override
    public double get(double x) {
        // x0 = floor, x1 = ciel
        long x0 = floor(x), x1 = x0 + 1;

        // compute distances
        double dx0 = x - x0, dx1 = x - x1;

        // skip "hash corners for indices", "get gradients" & "compute dot products"
        // -- gradients are always <1.0>
        // -- dp0 = dx0, dp1 = dx1

        // get interpolation weights
        double wx = x - x0;

        // interpolate/return result
        return interpolate(dx0, dx1, wx);
    }

    @Override
    public float get(float x, float y) {
        // x0 = floor, x1 = ciel
        int x0 = floor(x), x1 = x0 + 1;
        int y0 = floor(y), y1 = y0 + 1;

        // compute distances
        float dx0 = x - x0, dx1 = x - x1,
                dy0 = y - y0, dy1 = y - y1;

        // hash corners for indices
        int ind00 = computeGradientIndex(x0, y0),
                ind01 = computeGradientIndex(x0, y1),
                ind10 = computeGradientIndex(x1, y0),
                ind11 = computeGradientIndex(x1, y1);

        // get gradients
        FloatGradient2D g00 = FLOAT_GRADIENTS_2D[ind00],
                g01 = FLOAT_GRADIENTS_2D[ind01],
                g10 = FLOAT_GRADIENTS_2D[ind10],
                g11 = FLOAT_GRADIENTS_2D[ind11];

        // compute dot products
        float dp00 = dx0 * g00.dx + dy0 * g00.dy,
                dp01 = dx0 * g01.dx + dy1 * g01.dy,
                dp10 = dx1 * g10.dx + dy0 * g10.dy,
                dp11 = dx1 * g11.dx + dy1 * g11.dy;

        // get interpolation weights
        float wx = x - x0, wy = y - y0;

        // interpolate
        float ix0 = interpolate(dp00, dp10, wx), // y = 0 fixed, x varies
                ix1 = interpolate(dp01, dp11, wx); // y = 1 fixed, x varies

        // return result
        return interpolate(ix0, ix1, wy); // x = wx fixed, y varies
    }
    @Override
    public double get(double x, double y) {
        // x0 = floor, x1 = ciel
        long x0 = floor(x), x1 = x0 + 1;
        long y0 = floor(y), y1 = y0 + 1;

        // compute distances
        double dx0 = x - x0, dx1 = x - x1,
                dy0 = y - y0, dy1 = y - y1;

        // hash corners for indices
        int ind00 = computeGradientIndex(x0, y0),
                ind01 = computeGradientIndex(x0, y1),
                ind10 = computeGradientIndex(x1, y0),
                ind11 = computeGradientIndex(x1, y1);

        // get gradients
        DoubleGradient2D g00 = DOUBLE_GRADIENTS_2D[ind00],
                g01 = DOUBLE_GRADIENTS_2D[ind01],
                g10 = DOUBLE_GRADIENTS_2D[ind10],
                g11 = DOUBLE_GRADIENTS_2D[ind11];

        // compute dot products
        double dp00 = dx0 * g00.dx + dy0 * g00.dy,
                dp01 = dx0 * g01.dx + dy1 * g01.dy,
                dp10 = dx1 * g10.dx + dy0 * g10.dy,
                dp11 = dx1 * g11.dx + dy1 * g11.dy;

        // get interpolation weights
        double wx = x - x0, wy = y - y0;

        // interpolate
        double ix0 = interpolate(dp00, dp10, wx), // y = 0 fixed, x varies
                ix1 = interpolate(dp01, dp11, wx); // y = 1 fixed, x varies

        // return result
        return interpolate(ix0, ix1, wy); // x = wx fixed, y varies
    }

    @Override
    public float get(float x, float y, float z) {
        // x0 = floor, x1 = ciel
        int x0 = floor(x), x1 = x0 + 1;
        int y0 = floor(y), y1 = y0 + 1;
        int z0 = floor(z), z1 = z0 + 1;

        // compute distances
        float dx0 = x - x0, dx1 = x - x1,
                dy0 = y - y0, dy1 = y - y1,
                dz0 = z - z0, dz1 = z - z1;

        // hash corners for indices
        int ind000 = computeGradientIndex(x0, y0, z0),
                ind001 = computeGradientIndex(x0, y0, z1),
                ind010 = computeGradientIndex(x0, y1, z0),
                ind011 = computeGradientIndex(x0, y1, z1),
                ind100 = computeGradientIndex(x1, y0, z0),
                ind101 = computeGradientIndex(x1, y0, z1),
                ind110 = computeGradientIndex(x1, y1, z0),
                ind111 = computeGradientIndex(x1, y1, z1);

        // get gradients
        FloatGradient3D g000 = FLOAT_GRADIENTS_3D[ind000],
                g001 = FLOAT_GRADIENTS_3D[ind001],
                g010 = FLOAT_GRADIENTS_3D[ind010],
                g011 = FLOAT_GRADIENTS_3D[ind011],
                g100 = FLOAT_GRADIENTS_3D[ind100],
                g101 = FLOAT_GRADIENTS_3D[ind101],
                g110 = FLOAT_GRADIENTS_3D[ind110],
                g111 = FLOAT_GRADIENTS_3D[ind111];

        // compute dot products
        float dp000 = dx0 * g000.dx + dy0 * g000.dy + dz0 * g000.dz,
                dp001 = dx0 * g001.dx + dy0 * g001.dy + dz1 * g001.dz,
                dp010 = dx0 * g010.dx + dy1 * g010.dy + dz0 * g010.dz,
                dp011 = dx0 * g011.dx + dy1 * g011.dy + dz1 * g011.dz,
                dp100 = dx1 * g100.dx + dy0 * g100.dy + dz0 * g100.dz,
                dp101 = dx1 * g101.dx + dy0 * g101.dy + dz1 * g101.dz,
                dp110 = dx1 * g110.dx + dy1 * g110.dy + dz0 * g110.dz,
                dp111 = dx1 * g111.dx + dy1 * g111.dy + dz1 * g111.dz;

        // get interpolation weights
        float wx = x - x0, wy = y - y0, wz = z - z0;

        // interpolate
        float ix00 = interpolate(dp000, dp100, wx), // y = 0 & z = 0 fixed, x varies
                ix01 = interpolate(dp001, dp101, wx), // y = 0 & z = 1 fixed, x varies
                ix10 = interpolate(dp010, dp110, wx), // y = 1 & z = 0 fixed, x varies
                ix11 = interpolate(dp011, dp111, wx); // y = 1 & z = 1 fixed, x varies

        float ixy0 = interpolate(ix00, ix10, wy), // x = wx & z = 0 fixed, y varies
                ixy1 = interpolate(ix01, ix11, wy); // x = wx & z = 1 fixed, y varies

        // return result
        return interpolate(ixy0, ixy1, wz); // x = wx & y = wy fixed, z varies
    }
    @Override
    public double get(double x, double y, double z) {
        // x0 = floor, x1 = ciel
        long x0 = floor(x), x1 = x0 + 1;
        long y0 = floor(y), y1 = y0 + 1;
        long z0 = floor(z), z1 = z0 + 1;

        // compute distances
        double dx0 = x - x0, dx1 = x - x1,
                dy0 = y - y0, dy1 = y - y1,
                dz0 = z - z0, dz1 = z - z1;

        // hash corners for indices
        int ind000 = computeGradientIndex(x0, y0, z0),
                ind001 = computeGradientIndex(x0, y0, z1),
                ind010 = computeGradientIndex(x0, y1, z0),
                ind011 = computeGradientIndex(x0, y1, z1),
                ind100 = computeGradientIndex(x1, y0, z0),
                ind101 = computeGradientIndex(x1, y0, z1),
                ind110 = computeGradientIndex(x1, y1, z0),
                ind111 = computeGradientIndex(x1, y1, z1);

        // get gradients
        DoubleGradient3D g000 = DOUBLE_GRADIENTS_3D[ind000],
                g001 = DOUBLE_GRADIENTS_3D[ind001],
                g010 = DOUBLE_GRADIENTS_3D[ind010],
                g011 = DOUBLE_GRADIENTS_3D[ind011],
                g100 = DOUBLE_GRADIENTS_3D[ind100],
                g101 = DOUBLE_GRADIENTS_3D[ind101],
                g110 = DOUBLE_GRADIENTS_3D[ind110],
                g111 = DOUBLE_GRADIENTS_3D[ind111];

        // compute dot products
        double dp000 = dx0 * g000.dx + dy0 * g000.dy + dz0 * g000.dz,
                dp001 = dx0 * g001.dx + dy0 * g001.dy + dz1 * g001.dz,
                dp010 = dx0 * g010.dx + dy1 * g010.dy + dz0 * g010.dz,
                dp011 = dx0 * g011.dx + dy1 * g011.dy + dz1 * g011.dz,
                dp100 = dx1 * g100.dx + dy0 * g100.dy + dz0 * g100.dz,
                dp101 = dx1 * g101.dx + dy0 * g101.dy + dz1 * g101.dz,
                dp110 = dx1 * g110.dx + dy1 * g110.dy + dz0 * g110.dz,
                dp111 = dx1 * g111.dx + dy1 * g111.dy + dz1 * g111.dz;

        // get interpolation weights
        double wx = x - x0, wy = y - y0, wz = z - z0;

        // interpolate
        double ix00 = interpolate(dp000, dp100, wx), // y = 0 & z = 0 fixed, x varies
                ix01 = interpolate(dp001, dp101, wx), // y = 0 & z = 1 fixed, x varies
                ix10 = interpolate(dp010, dp110, wx), // y = 1 & z = 0 fixed, x varies
                ix11 = interpolate(dp011, dp111, wx); // y = 1 & z = 1 fixed, x varies

        double ixy0 = interpolate(ix00, ix10, wy), // x = wx & z = 0 fixed, y varies
                ixy1 = interpolate(ix01, ix11, wy); // x = wx & z = 1 fixed, y varies

        // return result
        return interpolate(ixy0, ixy1, wz); // x = wx & y = wy fixed, z varies
    }

    @Override
    public float get(float x, float y, float z, float w) {
        // x0 = floor, x1 = ciel
        int x0 = floor(x), x1 = x0 + 1;
        int y0 = floor(y), y1 = y0 + 1;
        int z0 = floor(z), z1 = z0 + 1;
        int w0 = floor(z), w1 = w0 + 1;

        // compute distances
        float dx0 = x - x0, dx1 = x - x1,
                dy0 = y - y0, dy1 = y - y1,
                dz0 = z - z0, dz1 = z - z1,
                dw0 = w - w0, dw1 = w - w1;

        // hash corners for indices
        int ind0000 = computeGradientIndex(x0, y0, z0, w0),
                ind0001 = computeGradientIndex(x0, y0, z0, w1),
                ind0010 = computeGradientIndex(x0, y0, z1, w0),
                ind0011 = computeGradientIndex(x0, y0, z1, w1),
                ind0100 = computeGradientIndex(x0, y1, z0, w0),
                ind0101 = computeGradientIndex(x0, y1, z0, w1),
                ind0110 = computeGradientIndex(x0, y1, z1, w0),
                ind0111 = computeGradientIndex(x0, y1, z1, w1),
                ind1000 = computeGradientIndex(x1, y0, z0, w0),
                ind1001 = computeGradientIndex(x1, y0, z0, w1),
                ind1010 = computeGradientIndex(x1, y0, z1, w0),
                ind1011 = computeGradientIndex(x1, y0, z1, w1),
                ind1100 = computeGradientIndex(x1, y1, z0, w0),
                ind1101 = computeGradientIndex(x1, y1, z0, w1),
                ind1110 = computeGradientIndex(x1, y1, z1, w0),
                ind1111 = computeGradientIndex(x1, y1, z1, w1);

        // get gradients
        FloatGradient4D g0000 = FLOAT_GRADIENTS_4D[ind0000],
                g0001 = FLOAT_GRADIENTS_4D[ind0001],
                g0010 = FLOAT_GRADIENTS_4D[ind0010],
                g0011 = FLOAT_GRADIENTS_4D[ind0011],
                g0100 = FLOAT_GRADIENTS_4D[ind0100],
                g0101 = FLOAT_GRADIENTS_4D[ind0101],
                g0110 = FLOAT_GRADIENTS_4D[ind0110],
                g0111 = FLOAT_GRADIENTS_4D[ind0111],
                g1000 = FLOAT_GRADIENTS_4D[ind1000],
                g1001 = FLOAT_GRADIENTS_4D[ind1001],
                g1010 = FLOAT_GRADIENTS_4D[ind1010],
                g1011 = FLOAT_GRADIENTS_4D[ind1011],
                g1100 = FLOAT_GRADIENTS_4D[ind1100],
                g1101 = FLOAT_GRADIENTS_4D[ind1101],
                g1110 = FLOAT_GRADIENTS_4D[ind1110],
                g1111 = FLOAT_GRADIENTS_4D[ind1111];

        // compute dot products
        float dp0000 = dx0 * g0000.dx + dy0 * g0000.dy + dz0 * g0000.dz + dw0 * g0000.dw,
                dp0001 = dx0 * g0001.dx + dy0 * g0001.dy + dz0 * g0001.dz + dw1 * g0001.dw,
                dp0010 = dx0 * g0010.dx + dy0 * g0010.dy + dz1 * g0010.dz + dw0 * g0010.dw,
                dp0011 = dx0 * g0011.dx + dy0 * g0011.dy + dz1 * g0011.dz + dw1 * g0011.dw,
                dp0100 = dx0 * g0100.dx + dy1 * g0100.dy + dz0 * g0100.dz + dw0 * g0100.dw,
                dp0101 = dx0 * g0101.dx + dy1 * g0101.dy + dz0 * g0101.dz + dw1 * g0101.dw,
                dp0110 = dx0 * g0110.dx + dy1 * g0110.dy + dz1 * g0110.dz + dw0 * g0110.dw,
                dp0111 = dx0 * g0111.dx + dy1 * g0111.dy + dz1 * g0111.dz + dw1 * g0111.dw,
                dp1000 = dx1 * g1000.dx + dy0 * g1000.dy + dz0 * g1000.dz + dw0 * g1000.dw,
                dp1001 = dx1 * g1001.dx + dy0 * g1001.dy + dz0 * g1001.dz + dw1 * g1001.dw,
                dp1010 = dx1 * g1010.dx + dy0 * g1010.dy + dz1 * g1010.dz + dw0 * g1010.dw,
                dp1011 = dx1 * g1011.dx + dy0 * g1011.dy + dz1 * g1011.dz + dw1 * g1011.dw,
                dp1100 = dx1 * g1100.dx + dy1 * g1100.dy + dz0 * g1100.dz + dw0 * g1100.dw,
                dp1101 = dx1 * g1101.dx + dy1 * g1101.dy + dz0 * g1101.dz + dw1 * g1101.dw,
                dp1110 = dx1 * g1110.dx + dy1 * g1110.dy + dz1 * g1110.dz + dw0 * g1110.dw,
                dp1111 = dx1 * g1111.dx + dy1 * g1111.dy + dz1 * g1111.dz + dw1 * g1111.dw;

        // get interpolation weights
        float wx = x - x0, wy = y - y0, wz = z - z0, ww = w - w0;

        // interpolate
        float ix000 = interpolate(dp0000, dp1000, wx), // y = 0 & z = 0 & w = 0 fixed, x varies
                ix001 = interpolate(dp0001, dp1001, wx), // y = 0 & z = 0 & w = 1 fixed, x varies
                ix010 = interpolate(dp0010, dp1010, wx), // y = 0 & z = 1 & w = 0 fixed, x varies
                ix011 = interpolate(dp0011, dp1011, wx), // y = 0 & z = 1 & w = 1 fixed, x varies
                ix100 = interpolate(dp0100, dp1100, wx), // y = 1 & z = 0 & w = 0 fixed, x varies
                ix101 = interpolate(dp0101, dp1101, wx), // y = 1 & z = 0 & w = 1 fixed, x varies
                ix110 = interpolate(dp0110, dp1110, wx), // y = 1 & z = 1 & w = 0 fixed, x varies
                ix111 = interpolate(dp0111, dp1111, wx); // y = 1 & z = 1 & w = 1 fixed, x varies

        float ixy00 = interpolate(ix000, ix100, wy), // x = wx & z = 0 & w = 0 fixed, y varies
                ixy01 = interpolate(ix001, ix101, wy), // x = wx & z = 0 & w = 1 fixed, y varies
                ixy10 = interpolate(ix010, ix110, wy), // x = wx & z = 1 & w = 0 fixed, y varies
                ixy11 = interpolate(ix011, ix111, wy); // x = wx & z = 1 & w = 1 fixed, y varies

        float ixyz0 = interpolate(ixy00, ixy10, wz), // x = wx & y = wy & w = 0 fixed, z varies
                ixyz1 = interpolate(ixy01, ixy11, wz); // x = wx & y = wy & w = 1 fixed, z varies

        // return result
        return interpolate(ixyz0, ixyz1, ww); // x = wx & y = wy & z = wz fixed, w varies
    }
    @Override
    public double get(double x, double y, double z, double w) {
        // x0 = floor, x1 = ciel
        long x0 = floor(x), x1 = x0 + 1;
        long y0 = floor(y), y1 = y0 + 1;
        long z0 = floor(z), z1 = z0 + 1;
        long w0 = floor(z), w1 = w0 + 1;

        // compute distances
        double dx0 = x - x0, dx1 = x - x1,
                dy0 = y - y0, dy1 = y - y1,
                dz0 = z - z0, dz1 = z - z1,
                dw0 = w - w0, dw1 = w - w1;

        // hash corners for indices
        int ind0000 = computeGradientIndex(x0, y0, z0, w0),
                ind0001 = computeGradientIndex(x0, y0, z0, w1),
                ind0010 = computeGradientIndex(x0, y0, z1, w0),
                ind0011 = computeGradientIndex(x0, y0, z1, w1),
                ind0100 = computeGradientIndex(x0, y1, z0, w0),
                ind0101 = computeGradientIndex(x0, y1, z0, w1),
                ind0110 = computeGradientIndex(x0, y1, z1, w0),
                ind0111 = computeGradientIndex(x0, y1, z1, w1),
                ind1000 = computeGradientIndex(x1, y0, z0, w0),
                ind1001 = computeGradientIndex(x1, y0, z0, w1),
                ind1010 = computeGradientIndex(x1, y0, z1, w0),
                ind1011 = computeGradientIndex(x1, y0, z1, w1),
                ind1100 = computeGradientIndex(x1, y1, z0, w0),
                ind1101 = computeGradientIndex(x1, y1, z0, w1),
                ind1110 = computeGradientIndex(x1, y1, z1, w0),
                ind1111 = computeGradientIndex(x1, y1, z1, w1);

        // get gradients
        DoubleGradient4D g0000 = DOUBLE_GRADIENTS_4D[ind0000],
                g0001 = DOUBLE_GRADIENTS_4D[ind0001],
                g0010 = DOUBLE_GRADIENTS_4D[ind0010],
                g0011 = DOUBLE_GRADIENTS_4D[ind0011],
                g0100 = DOUBLE_GRADIENTS_4D[ind0100],
                g0101 = DOUBLE_GRADIENTS_4D[ind0101],
                g0110 = DOUBLE_GRADIENTS_4D[ind0110],
                g0111 = DOUBLE_GRADIENTS_4D[ind0111],
                g1000 = DOUBLE_GRADIENTS_4D[ind1000],
                g1001 = DOUBLE_GRADIENTS_4D[ind1001],
                g1010 = DOUBLE_GRADIENTS_4D[ind1010],
                g1011 = DOUBLE_GRADIENTS_4D[ind1011],
                g1100 = DOUBLE_GRADIENTS_4D[ind1100],
                g1101 = DOUBLE_GRADIENTS_4D[ind1101],
                g1110 = DOUBLE_GRADIENTS_4D[ind1110],
                g1111 = DOUBLE_GRADIENTS_4D[ind1111];

        // compute dot products
        double dp0000 = dx0 * g0000.dx + dy0 * g0000.dy + dz0 * g0000.dz + dw0 * g0000.dw,
                dp0001 = dx0 * g0001.dx + dy0 * g0001.dy + dz0 * g0001.dz + dw1 * g0001.dw,
                dp0010 = dx0 * g0010.dx + dy0 * g0010.dy + dz1 * g0010.dz + dw0 * g0010.dw,
                dp0011 = dx0 * g0011.dx + dy0 * g0011.dy + dz1 * g0011.dz + dw1 * g0011.dw,
                dp0100 = dx0 * g0100.dx + dy1 * g0100.dy + dz0 * g0100.dz + dw0 * g0100.dw,
                dp0101 = dx0 * g0101.dx + dy1 * g0101.dy + dz0 * g0101.dz + dw1 * g0101.dw,
                dp0110 = dx0 * g0110.dx + dy1 * g0110.dy + dz1 * g0110.dz + dw0 * g0110.dw,
                dp0111 = dx0 * g0111.dx + dy1 * g0111.dy + dz1 * g0111.dz + dw1 * g0111.dw,
                dp1000 = dx1 * g1000.dx + dy0 * g1000.dy + dz0 * g1000.dz + dw0 * g1000.dw,
                dp1001 = dx1 * g1001.dx + dy0 * g1001.dy + dz0 * g1001.dz + dw1 * g1001.dw,
                dp1010 = dx1 * g1010.dx + dy0 * g1010.dy + dz1 * g1010.dz + dw0 * g1010.dw,
                dp1011 = dx1 * g1011.dx + dy0 * g1011.dy + dz1 * g1011.dz + dw1 * g1011.dw,
                dp1100 = dx1 * g1100.dx + dy1 * g1100.dy + dz0 * g1100.dz + dw0 * g1100.dw,
                dp1101 = dx1 * g1101.dx + dy1 * g1101.dy + dz0 * g1101.dz + dw1 * g1101.dw,
                dp1110 = dx1 * g1110.dx + dy1 * g1110.dy + dz1 * g1110.dz + dw0 * g1110.dw,
                dp1111 = dx1 * g1111.dx + dy1 * g1111.dy + dz1 * g1111.dz + dw1 * g1111.dw;

        // get interpolation weights
        double wx = x - x0, wy = y - y0, wz = z - z0, ww = w - w0;

        // interpolate
        double ix000 = interpolate(dp0000, dp1000, wx), // y = 0 & z = 0 & w = 0 fixed, x varies
                ix001 = interpolate(dp0001, dp1001, wx), // y = 0 & z = 0 & w = 1 fixed, x varies
                ix010 = interpolate(dp0010, dp1010, wx), // y = 0 & z = 1 & w = 0 fixed, x varies
                ix011 = interpolate(dp0011, dp1011, wx), // y = 0 & z = 1 & w = 1 fixed, x varies
                ix100 = interpolate(dp0100, dp1100, wx), // y = 1 & z = 0 & w = 0 fixed, x varies
                ix101 = interpolate(dp0101, dp1101, wx), // y = 1 & z = 0 & w = 1 fixed, x varies
                ix110 = interpolate(dp0110, dp1110, wx), // y = 1 & z = 1 & w = 0 fixed, x varies
                ix111 = interpolate(dp0111, dp1111, wx); // y = 1 & z = 1 & w = 1 fixed, x varies

        double ixy00 = interpolate(ix000, ix100, wy), // x = wx & z = 0 & w = 0 fixed, y varies
                ixy01 = interpolate(ix001, ix101, wy), // x = wx & z = 0 & w = 1 fixed, y varies
                ixy10 = interpolate(ix010, ix110, wy), // x = wx & z = 1 & w = 0 fixed, y varies
                ixy11 = interpolate(ix011, ix111, wy); // x = wx & z = 1 & w = 1 fixed, y varies

        double ixyz0 = interpolate(ixy00, ixy10, wz), // x = wx & y = wy & w = 0 fixed, z varies
                ixyz1 = interpolate(ixy01, ixy11, wz); // x = wx & y = wy & w = 1 fixed, z varies

        // return result
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

        // compute distances
        float[][] d = new float[dim][2];
        for (int i = 0; i < dim; i++) {
            d[i][0] = pos[i] - p[i][0];
            d[i][1] = pos[i] - p[i][1];
        }

        long[] cornerIndices = new long[dim];
        float[] dp = new float[1 << dim], grad = new float[dim];
        SeedHasher.Interator it = getSeedHasher().getInterator(dim);
        for (int i = 0; i < dp.length; i++) {
            // compute corners from index bits
            int bits = i;
            for (int ind = dim - 1; ind >= 0; ind--) {
                cornerIndices[ind] = p[ind][bits & 1];
                bits >>>= 1;
            }

            // compute gradient vector (in grad)
            computeGradient(dim, cornerIndices, grad, it);

            // compute dot product
            bits = i;
            dp[i] = 0.0f;
            for (int ind = dim - 1; ind >= 0; ind--) {
                dp[i] += grad[ind] * d[ind][bits & 1];
                bits >>>= 1;
            }
        }

        // get interpolation weights
        @SuppressWarnings("UnnecessaryLocalVariable") float[] w = grad; // reuse array
        for (int i = 0; i < dim; i++)
            w[i] = d[i][0];

        // interpolate
        int c = 0; // iteration count
        for (int i = dp.length >>> 1; i > 0; i >>>= 1) {
            for (int j = 0; j < i; j++)
                dp[j] = interpolate(dp[j], dp[j + i], w[c]);
            c++;
        }

        // return result
        return dp[0];
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

        // compute distances
        double[][] d = new double[dim][2];
        for (int i = 0; i < dim; i++) {
            d[i][0] = pos[i] - p[i][0];
            d[i][1] = pos[i] - p[i][1];
        }

        long[] cornerIndices = new long[dim];
        double[] dp = new double[1 << dim], grad = new double[dim];
        SeedHasher.Interator it = getSeedHasher().getInterator(dim);
        for (int i = 0; i < dp.length; i++) {
            // compute corners from index bits
            int bits = i;
            for (int ind = dim - 1; ind >= 0; ind--) {
                cornerIndices[ind] = p[ind][bits & 1];
                bits >>>= 1;
            }

            // compute gradient vector (in grad)
            computeGradient(dim, cornerIndices, grad, it);

            // compute dot product
            bits = i;
            dp[i] = 0.0f;
            for (int ind = dim - 1; ind >= 0; ind--) {
                dp[i] += grad[ind] * d[ind][bits & 1];
                bits >>>= 1;
            }
        }

        // get interpolation weights
        @SuppressWarnings("UnnecessaryLocalVariable") double[] w = grad; // reuse array
        for (int i = 0; i < dim; i++)
            w[i] = d[i][0];

        // interpolate
        int c = 0; // iteration count
        for (int i = dp.length >>> 1; i > 0; i >>>= 1) {
            for (int j = 0; j < i; j++)
                dp[j] = interpolate(dp[j], dp[j + i], w[c]);
            c++;
        }

        // return result
        return dp[0];
    }


    public static @NotNull PerlinNoise aperiodic() {
        return new PerlinNoise();
    }
    public static @NotNull PerlinNoise aperiodic(long seed) {
        return new PerlinNoise(seed);
    }

    public static @NotNull PerlinNoise periodic(long defaultPeriod, @NotNull long... periods) {
        return new PerlinNoise(defaultPeriod, periods);
    }
    public static @NotNull PerlinNoise periodic(long seed, long defaultPeriod, @NotNull long... periods) {
        return new PerlinNoise(seed, defaultPeriod, periods);
    }
}
