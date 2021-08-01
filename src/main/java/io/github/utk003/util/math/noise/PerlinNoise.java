package io.github.utk003.util.math.noise;

import io.github.utk003.util.math.interp.Interpolatable;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// https://en.wikipedia.org/wiki/Perlin_noise
@ScheduledForRelease(inVersion = "v3.0.0")
@RequiresDocumentation
public final class PerlinNoise extends GradientNoise implements Noise, Interpolatable {
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

    private @NotNull InterpolationType interpolationType = InterpolationType.DEFAULT;
    @Override
    public @NotNull InterpolationType getInterpolationType() {
        return interpolationType;
    }
    @Override
    @Contract("_ -> this")
    public @NotNull PerlinNoise setInterpolationType(@NotNull InterpolationType interpolationType) {
        this.interpolationType = interpolationType;
        return this;
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
        return interpolate(dx0, dx1, wx) / PerlinNoiseBounds.FLOAT_BOUND_1D;
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
        return interpolate(dx0, dx1, wx) / PerlinNoiseBounds.DOUBLE_BOUND_1D;
    }

    @Override
    public float get(float x, float y) {
        // x0 = floor, x1 = ciel
        int x0 = floor(x), x1 = x0 + 1;
        int y0 = floor(y), y1 = y0 + 1;

        // compute distances
        float dx0 = x - x0, dx1 = x - x1,
                dy0 = y - y0, dy1 = y - y1;

        // get gradients
        FloatGradient2D g00 = getFloatGradient(x0, y0),
                g01 = getFloatGradient(x0, y1),
                g10 = getFloatGradient(x1, y0),
                g11 = getFloatGradient(x1, y1);

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

        float ixy = interpolate(ix0, ix1, wy); // x = wx fixed, y varies

        // return result
        return ixy / PerlinNoiseBounds.FLOAT_BOUND_2D;
    }
    @Override
    public double get(double x, double y) {
        // x0 = floor, x1 = ciel
        long x0 = floor(x), x1 = x0 + 1;
        long y0 = floor(y), y1 = y0 + 1;

        // compute distances
        double dx0 = x - x0, dx1 = x - x1,
                dy0 = y - y0, dy1 = y - y1;

        // get gradients
        DoubleGradient2D g00 = getDoubleGradient(x0, y0),
                g01 = getDoubleGradient(x0, y1),
                g10 = getDoubleGradient(x1, y0),
                g11 = getDoubleGradient(x1, y1);

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

        double ixy = interpolate(ix0, ix1, wy); // x = wx fixed, y varies

        // return result
        return ixy / PerlinNoiseBounds.DOUBLE_BOUND_2D;
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

        // get gradients
        FloatGradient3D g000 = getFloatGradient(x0, y0, z0),
                g001 = getFloatGradient(x0, y0, z1),
                g010 = getFloatGradient(x0, y1, z0),
                g011 = getFloatGradient(x0, y1, z1),
                g100 = getFloatGradient(x1, y0, z0),
                g101 = getFloatGradient(x1, y0, z1),
                g110 = getFloatGradient(x1, y1, z0),
                g111 = getFloatGradient(x1, y1, z1);

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

        float ixyz = interpolate(ixy0, ixy1, wz); // x = wx & y = wy fixed, z varies

        // return result
        return ixyz / PerlinNoiseBounds.FLOAT_BOUND_3D;
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

        // get gradients
        DoubleGradient3D g000 = getDoubleGradient(x0, y0, z0),
                g001 = getDoubleGradient(x0, y0, z1),
                g010 = getDoubleGradient(x0, y1, z0),
                g011 = getDoubleGradient(x0, y1, z1),
                g100 = getDoubleGradient(x1, y0, z0),
                g101 = getDoubleGradient(x1, y0, z1),
                g110 = getDoubleGradient(x1, y1, z0),
                g111 = getDoubleGradient(x1, y1, z1);

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

        double ixyz = interpolate(ixy0, ixy1, wz); // x = wx & y = wy fixed, z varies

        // return result
        return ixyz / PerlinNoiseBounds.DOUBLE_BOUND_3D;
    }

    @Override
    public float get(float x, float y, float z, float w) {
        // x0 = floor, x1 = ciel
        int x0 = floor(x), x1 = x0 + 1;
        int y0 = floor(y), y1 = y0 + 1;
        int z0 = floor(z), z1 = z0 + 1;
        int w0 = floor(w), w1 = w0 + 1;

        // compute distances
        float dx0 = x - x0, dx1 = x - x1,
                dy0 = y - y0, dy1 = y - y1,
                dz0 = z - z0, dz1 = z - z1,
                dw0 = w - w0, dw1 = w - w1;

        // get gradients
        FloatGradient4D g0000 = getFloatGradient(x0, y0, z0, w0),
                g0001 = getFloatGradient(x0, y0, z0, w1),
                g0010 = getFloatGradient(x0, y0, z1, w0),
                g0011 = getFloatGradient(x0, y0, z1, w1),
                g0100 = getFloatGradient(x0, y1, z0, w0),
                g0101 = getFloatGradient(x0, y1, z0, w1),
                g0110 = getFloatGradient(x0, y1, z1, w0),
                g0111 = getFloatGradient(x0, y1, z1, w1),
                g1000 = getFloatGradient(x1, y0, z0, w0),
                g1001 = getFloatGradient(x1, y0, z0, w1),
                g1010 = getFloatGradient(x1, y0, z1, w0),
                g1011 = getFloatGradient(x1, y0, z1, w1),
                g1100 = getFloatGradient(x1, y1, z0, w0),
                g1101 = getFloatGradient(x1, y1, z0, w1),
                g1110 = getFloatGradient(x1, y1, z1, w0),
                g1111 = getFloatGradient(x1, y1, z1, w1);

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

        float ixyzw = interpolate(ixyz0, ixyz1, ww); // x = wx & y = wy & z = wz fixed, w varies

        // return result
        return ixyzw / PerlinNoiseBounds.FLOAT_BOUND_4D;
    }
    @Override
    public double get(double x, double y, double z, double w) {
        // x0 = floor, x1 = ciel
        long x0 = floor(x), x1 = x0 + 1;
        long y0 = floor(y), y1 = y0 + 1;
        long z0 = floor(z), z1 = z0 + 1;
        long w0 = floor(w), w1 = w0 + 1;

        // compute distances
        double dx0 = x - x0, dx1 = x - x1,
                dy0 = y - y0, dy1 = y - y1,
                dz0 = z - z0, dz1 = z - z1,
                dw0 = w - w0, dw1 = w - w1;

        // get gradients
        DoubleGradient4D g0000 = getDoubleGradient(x0, y0, z0, w0),
                g0001 = getDoubleGradient(x0, y0, z0, w1),
                g0010 = getDoubleGradient(x0, y0, z1, w0),
                g0011 = getDoubleGradient(x0, y0, z1, w1),
                g0100 = getDoubleGradient(x0, y1, z0, w0),
                g0101 = getDoubleGradient(x0, y1, z0, w1),
                g0110 = getDoubleGradient(x0, y1, z1, w0),
                g0111 = getDoubleGradient(x0, y1, z1, w1),
                g1000 = getDoubleGradient(x1, y0, z0, w0),
                g1001 = getDoubleGradient(x1, y0, z0, w1),
                g1010 = getDoubleGradient(x1, y0, z1, w0),
                g1011 = getDoubleGradient(x1, y0, z1, w1),
                g1100 = getDoubleGradient(x1, y1, z0, w0),
                g1101 = getDoubleGradient(x1, y1, z0, w1),
                g1110 = getDoubleGradient(x1, y1, z1, w0),
                g1111 = getDoubleGradient(x1, y1, z1, w1);

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

        double ixyzw = interpolate(ixyz0, ixyz1, ww); // x = wx & y = wy & z = wz fixed, w varies

        // return result
        return ixyzw / PerlinNoiseBounds.DOUBLE_BOUND_4D;
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
        return dp[0] / PerlinNoiseBounds.getAsFloat(dim);
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
        return dp[0] / PerlinNoiseBounds.getAsDouble(dim);
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

    // √(N)/2
    public static final class PerlinNoiseBounds {
        private static final double
                DOUBLE_BOUND_1D = 0.5,
                DOUBLE_BOUND_2D = 0.707106781186547524400844,
                DOUBLE_BOUND_3D = 0.866025403784438646763723,
                DOUBLE_BOUND_4D = 1.0;
        private static final float
                FLOAT_BOUND_1D = (float) DOUBLE_BOUND_1D,
                FLOAT_BOUND_2D = (float) DOUBLE_BOUND_2D,
                FLOAT_BOUND_3D = (float) DOUBLE_BOUND_3D,
                FLOAT_BOUND_4D = (float) DOUBLE_BOUND_4D;

        private static final @NotNull Map<Integer, Double> BOUNDS_MAP = new HashMap<>();
        private static final @NotNull Function<Integer, Double> FUNC = (dim) -> Math.sqrt(dim) / 2;
        static {
            BOUNDS_MAP.put(1, DOUBLE_BOUND_1D);
            BOUNDS_MAP.put(2, DOUBLE_BOUND_2D);
            BOUNDS_MAP.put(3, DOUBLE_BOUND_3D);
            BOUNDS_MAP.put(4, DOUBLE_BOUND_4D);
        }

        public static synchronized float getAsFloat(int dim) {
            return (float) (double) BOUNDS_MAP.computeIfAbsent(dim, FUNC);
        }
        public static synchronized double getAsDouble(int dim) {
            return BOUNDS_MAP.computeIfAbsent(dim, FUNC);
        }
    }
}
