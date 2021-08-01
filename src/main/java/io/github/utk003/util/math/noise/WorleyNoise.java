package io.github.utk003.util.math.noise;

import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnnecessaryLocalVariable")
@ApiStatus.Experimental
@RequiresDocumentation
public abstract class WorleyNoise extends AbstractNoise implements Noise {
    private double hashToCoord(long baseCoord, long cellHash) {
        return baseCoord + (1.0 + (double) cellHash / Long.MIN_VALUE) / 2.0;
    }

    private static float dist(float p, float p0) {
        float d = p - p0;
        return d * d;
    }
    private static double dist(double p, double p0) {
        double d = p - p0;
        return d * d;
    }

    public WorleyNoise() {
        super();
    }
    public WorleyNoise(long seed) {
        super(seed);
    }
    public WorleyNoise(long defaultPeriod, @NotNull long[] periods) {
        super(defaultPeriod, periods);
    }
    public WorleyNoise(long seed, long defaultPeriod, @NotNull long[] periods) {
        super(seed, defaultPeriod, periods);
    }

    protected final class FloatDistanceVector1D {
        public final float d0, d1, d2;
        private FloatDistanceVector1D(float x) {
            int xf = floor(x), x0 = xf - 1, x1 = xf, x2 = xf + 1;
            d0 = dist(x, (float) hashToCoord(x0, computeHashKey(x0)));
            d1 = dist(x, (float) hashToCoord(x1, computeHashKey(x1)));
            d2 = dist(x, (float) hashToCoord(x2, computeHashKey(x2)));
        }
    }
    protected abstract float getNoise(@NotNull FloatDistanceVector1D distanceVector);

    protected final class DoubleDistanceVector1D {
        public final double d0, d1, d2;
        private DoubleDistanceVector1D(double x) {
            long xf = floor(x), x0 = xf - 1, x1 = xf, x2 = xf + 1;
            d0 = dist(x, hashToCoord(x0, computeHashKey(x0)));
            d1 = dist(x, hashToCoord(x1, computeHashKey(x1)));
            d2 = dist(x, hashToCoord(x2, computeHashKey(x2)));
        }
    }
    protected abstract double getNoise(@NotNull DoubleDistanceVector1D distanceVector);

    @Override
    public float get(float x) {
        return getNoise(new FloatDistanceVector1D(x));
    }
    @Override
    public double get(double x) {
        return getNoise(new DoubleDistanceVector1D(x));
    }

    protected final class FloatDistanceVector2D {
        public final float
                d00, d01, d02,
                d10, d11, d12,
                d20, d21, d22;
        private FloatDistanceVector2D(float x, float y) {
            int xf = floor(x), x0 = xf - 1, x1 = xf, x2 = xf + 1;
            int yf = floor(y), y0 = yf - 1, y1 = yf, y2 = yf + 1;

            float dx0 = dist(x, (float) hashToCoord(x0, computeHashKey(x0)));
            float dx1 = dist(x, (float) hashToCoord(x1, computeHashKey(x1)));
            float dx2 = dist(x, (float) hashToCoord(x2, computeHashKey(x2)));

            float dy0 = dist(y, (float) hashToCoord(y0, computeHashKey(y0)));
            float dy1 = dist(y, (float) hashToCoord(y1, computeHashKey(y1)));
            float dy2 = dist(y, (float) hashToCoord(y2, computeHashKey(y2)));

            d00 = dx0 + dy0;
            d01 = dx0 + dy1;
            d02 = dx0 + dy2;

            d10 = dx1 + dy0;
            d11 = dx1 + dy1;
            d12 = dx1 + dy2;

            d20 = dx2 + dy0;
            d21 = dx2 + dy1;
            d22 = dx2 + dy2;
        }
    }
    protected abstract float getNoise(@NotNull FloatDistanceVector2D distanceVector);

    protected final class DoubleDistanceVector2D {
        public final double
                d00, d01, d02,
                d10, d11, d12,
                d20, d21, d22;
        private DoubleDistanceVector2D(double x, double y) {
            long xf = floor(x), x0 = xf - 1, x1 = xf, x2 = xf + 1;
            long yf = floor(y), y0 = yf - 1, y1 = yf, y2 = yf + 1;

            double dx0 = dist(x, hashToCoord(x0, computeHashKey(x0)));
            double dx1 = dist(x, hashToCoord(x1, computeHashKey(x1)));
            double dx2 = dist(x, hashToCoord(x2, computeHashKey(x2)));

            double dy0 = dist(y, hashToCoord(y0, computeHashKey(y0)));
            double dy1 = dist(y, hashToCoord(y1, computeHashKey(y1)));
            double dy2 = dist(y, hashToCoord(y2, computeHashKey(y2)));

            d00 = dx0 + dy0;
            d01 = dx0 + dy1;
            d02 = dx0 + dy2;

            d10 = dx1 + dy0;
            d11 = dx1 + dy1;
            d12 = dx1 + dy2;

            d20 = dx2 + dy0;
            d21 = dx2 + dy1;
            d22 = dx2 + dy2;
        }
    }
    protected abstract double getNoise(@NotNull DoubleDistanceVector2D distanceVector);

    @Override
    public float get(float x, float y) {
        return getNoise(new FloatDistanceVector2D(x, y));
    }
    @Override
    public double get(double x, double y) {
        return getNoise(new DoubleDistanceVector2D(x, y));
    }

    protected final class FloatDistanceVector3D {
        public final float
                d000, d001, d002, d010, d011, d012, d020, d021, d022,
                d100, d101, d102, d110, d111, d112, d120, d121, d122,
                d200, d201, d202, d210, d211, d212, d220, d221, d222;
        private FloatDistanceVector3D(float x, float y, float z) {
            int xf = floor(x), x0 = xf - 1, x1 = xf, x2 = xf + 1;
            int yf = floor(y), y0 = yf - 1, y1 = yf, y2 = yf + 1;
            int zf = floor(z), z0 = zf - 1, z1 = zf, z2 = zf + 1;

            float dx0 = dist(x, (float) hashToCoord(x0, computeHashKey(x0)));
            float dx1 = dist(x, (float) hashToCoord(x1, computeHashKey(x1)));
            float dx2 = dist(x, (float) hashToCoord(x2, computeHashKey(x2)));

            float dy0 = dist(y, (float) hashToCoord(y0, computeHashKey(y0)));
            float dy1 = dist(y, (float) hashToCoord(y1, computeHashKey(y1)));
            float dy2 = dist(y, (float) hashToCoord(y2, computeHashKey(y2)));

            float dz0 = dist(z, (float) hashToCoord(z0, computeHashKey(z0)));
            float dz1 = dist(z, (float) hashToCoord(z1, computeHashKey(z1)));
            float dz2 = dist(z, (float) hashToCoord(z2, computeHashKey(z2)));

            // x = 0
            d000 = dx0 + dy0 + dz0;
            d001 = dx0 + dy0 + dz1;
            d002 = dx0 + dy0 + dz2;

            d010 = dx0 + dy1 + dz0;
            d011 = dx0 + dy1 + dz1;
            d012 = dx0 + dy1 + dz2;

            d020 = dx0 + dy2 + dz0;
            d021 = dx0 + dy2 + dz1;
            d022 = dx0 + dy2 + dz2;

            // x = 1
            d100 = dx1 + dy0 + dz0;
            d101 = dx1 + dy0 + dz1;
            d102 = dx1 + dy0 + dz2;

            d110 = dx1 + dy1 + dz0;
            d111 = dx1 + dy1 + dz1;
            d112 = dx1 + dy1 + dz2;

            d120 = dx1 + dy2 + dz0;
            d121 = dx1 + dy2 + dz1;
            d122 = dx1 + dy2 + dz2;

            // x = 2
            d200 = dx2 + dy0 + dz0;
            d201 = dx2 + dy0 + dz1;
            d202 = dx2 + dy0 + dz2;

            d210 = dx2 + dy1 + dz0;
            d211 = dx2 + dy1 + dz1;
            d212 = dx2 + dy1 + dz2;

            d220 = dx2 + dy2 + dz0;
            d221 = dx2 + dy2 + dz1;
            d222 = dx2 + dy2 + dz2;
        }
    }
    protected abstract float getNoise(@NotNull FloatDistanceVector3D distanceVector);

    protected final class DoubleDistanceVector3D {
        public final double
                d000, d001, d002, d010, d011, d012, d020, d021, d022,
                d100, d101, d102, d110, d111, d112, d120, d121, d122,
                d200, d201, d202, d210, d211, d212, d220, d221, d222;
        private DoubleDistanceVector3D(double x, double y, double z) {
            long xf = floor(x), x0 = xf - 1, x1 = xf, x2 = xf + 1;
            long yf = floor(y), y0 = yf - 1, y1 = yf, y2 = yf + 1;
            long zf = floor(z), z0 = zf - 1, z1 = zf, z2 = zf + 1;

            double dx0 = dist(x, hashToCoord(x0, computeHashKey(x0)));
            double dx1 = dist(x, hashToCoord(x1, computeHashKey(x1)));
            double dx2 = dist(x, hashToCoord(x2, computeHashKey(x2)));

            double dy0 = dist(y, hashToCoord(y0, computeHashKey(y0)));
            double dy1 = dist(y, hashToCoord(y1, computeHashKey(y1)));
            double dy2 = dist(y, hashToCoord(y2, computeHashKey(y2)));

            double dz0 = dist(z, hashToCoord(z0, computeHashKey(z0)));
            double dz1 = dist(z, hashToCoord(z1, computeHashKey(z1)));
            double dz2 = dist(z, hashToCoord(z2, computeHashKey(z2)));

            // x = 0
            d000 = dx0 + dy0 + dz0;
            d001 = dx0 + dy0 + dz1;
            d002 = dx0 + dy0 + dz2;

            d010 = dx0 + dy1 + dz0;
            d011 = dx0 + dy1 + dz1;
            d012 = dx0 + dy1 + dz2;

            d020 = dx0 + dy2 + dz0;
            d021 = dx0 + dy2 + dz1;
            d022 = dx0 + dy2 + dz2;

            // x = 1
            d100 = dx1 + dy0 + dz0;
            d101 = dx1 + dy0 + dz1;
            d102 = dx1 + dy0 + dz2;

            d110 = dx1 + dy1 + dz0;
            d111 = dx1 + dy1 + dz1;
            d112 = dx1 + dy1 + dz2;

            d120 = dx1 + dy2 + dz0;
            d121 = dx1 + dy2 + dz1;
            d122 = dx1 + dy2 + dz2;

            // x = 2
            d200 = dx2 + dy0 + dz0;
            d201 = dx2 + dy0 + dz1;
            d202 = dx2 + dy0 + dz2;

            d210 = dx2 + dy1 + dz0;
            d211 = dx2 + dy1 + dz1;
            d212 = dx2 + dy1 + dz2;

            d220 = dx2 + dy2 + dz0;
            d221 = dx2 + dy2 + dz1;
            d222 = dx2 + dy2 + dz2;
        }
    }
    protected abstract double getNoise(@NotNull DoubleDistanceVector3D distanceVector);

    @Override
    public float get(float x, float y, float z) {
        return getNoise(new FloatDistanceVector3D(x, y, z));
    }
    @Override
    public double get(double x, double y, double z) {
        return getNoise(new DoubleDistanceVector3D(x, y, z));
    }

    protected final class FloatDistanceVector4D {
        public final float
                d0000, d0001, d0002, d0010, d0011, d0012, d0020, d0021, d0022,
                d0100, d0101, d0102, d0110, d0111, d0112, d0120, d0121, d0122,
                d0200, d0201, d0202, d0210, d0211, d0212, d0220, d0221, d0222,
                d1000, d1001, d1002, d1010, d1011, d1012, d1020, d1021, d1022,
                d1100, d1101, d1102, d1110, d1111, d1112, d1120, d1121, d1122,
                d1200, d1201, d1202, d1210, d1211, d1212, d1220, d1221, d1222,
                d2000, d2001, d2002, d2010, d2011, d2012, d2020, d2021, d2022,
                d2100, d2101, d2102, d2110, d2111, d2112, d2120, d2121, d2122,
                d2200, d2201, d2202, d2210, d2211, d2212, d2220, d2221, d2222;
        private FloatDistanceVector4D(float x, float y, float z, float w) {
            int xf = floor(x), x0 = xf - 1, x1 = xf, x2 = xf + 1;
            int yf = floor(y), y0 = yf - 1, y1 = yf, y2 = yf + 1;
            int zf = floor(z), z0 = zf - 1, z1 = zf, z2 = zf + 1;
            int wf = floor(w), w0 = wf - 1, w1 = wf, w2 = wf + 1;

            float dx0 = dist(x, (float) hashToCoord(x0, computeHashKey(x0)));
            float dx1 = dist(x, (float) hashToCoord(x1, computeHashKey(x1)));
            float dx2 = dist(x, (float) hashToCoord(x2, computeHashKey(x2)));

            float dy0 = dist(y, (float) hashToCoord(y0, computeHashKey(y0)));
            float dy1 = dist(y, (float) hashToCoord(y1, computeHashKey(y1)));
            float dy2 = dist(y, (float) hashToCoord(y2, computeHashKey(y2)));

            float dz0 = dist(z, (float) hashToCoord(z0, computeHashKey(z0)));
            float dz1 = dist(z, (float) hashToCoord(z1, computeHashKey(z1)));
            float dz2 = dist(z, (float) hashToCoord(z2, computeHashKey(z2)));

            float dw0 = dist(w, (float) hashToCoord(w0, computeHashKey(w0)));
            float dw1 = dist(w, (float) hashToCoord(w1, computeHashKey(w1)));
            float dw2 = dist(w, (float) hashToCoord(w2, computeHashKey(w2)));

            // x = 0, y = 0
            d0000 = dx0 + dy0 + dz0 + dw0;
            d0001 = dx0 + dy0 + dz0 + dw1;
            d0002 = dx0 + dy0 + dz0 + dw2;

            d0010 = dx0 + dy0 + dz1 + dw0;
            d0011 = dx0 + dy0 + dz1 + dw1;
            d0012 = dx0 + dy0 + dz1 + dw2;

            d0020 = dx0 + dy0 + dz2 + dw0;
            d0021 = dx0 + dy0 + dz2 + dw1;
            d0022 = dx0 + dy0 + dz2 + dw2;

            // x = 0, y = 1
            d0100 = dx0 + dy1 + dz0 + dw0;
            d0101 = dx0 + dy1 + dz0 + dw1;
            d0102 = dx0 + dy1 + dz0 + dw2;

            d0110 = dx0 + dy1 + dz1 + dw0;
            d0111 = dx0 + dy1 + dz1 + dw1;
            d0112 = dx0 + dy1 + dz1 + dw2;

            d0120 = dx0 + dy1 + dz2 + dw0;
            d0121 = dx0 + dy1 + dz2 + dw1;
            d0122 = dx0 + dy1 + dz2 + dw2;

            // x = 0, y = 2
            d0200 = dx0 + dy2 + dz0 + dw0;
            d0201 = dx0 + dy2 + dz0 + dw1;
            d0202 = dx0 + dy2 + dz0 + dw2;

            d0210 = dx0 + dy2 + dz1 + dw0;
            d0211 = dx0 + dy2 + dz1 + dw1;
            d0212 = dx0 + dy2 + dz1 + dw2;

            d0220 = dx0 + dy2 + dz2 + dw0;
            d0221 = dx0 + dy2 + dz2 + dw1;
            d0222 = dx0 + dy2 + dz2 + dw2;

            // x = 1, y = 0
            d1000 = dx1 + dy0 + dz0 + dw0;
            d1001 = dx1 + dy0 + dz0 + dw1;
            d1002 = dx1 + dy0 + dz0 + dw2;

            d1010 = dx1 + dy0 + dz1 + dw0;
            d1011 = dx1 + dy0 + dz1 + dw1;
            d1012 = dx1 + dy0 + dz1 + dw2;

            d1020 = dx1 + dy0 + dz2 + dw0;
            d1021 = dx1 + dy0 + dz2 + dw1;
            d1022 = dx1 + dy0 + dz2 + dw2;

            // x = 1, y = 1
            d1100 = dx1 + dy1 + dz0 + dw0;
            d1101 = dx1 + dy1 + dz0 + dw1;
            d1102 = dx1 + dy1 + dz0 + dw2;

            d1110 = dx1 + dy1 + dz1 + dw0;
            d1111 = dx1 + dy1 + dz1 + dw1;
            d1112 = dx1 + dy1 + dz1 + dw2;

            d1120 = dx1 + dy1 + dz2 + dw0;
            d1121 = dx1 + dy1 + dz2 + dw1;
            d1122 = dx1 + dy1 + dz2 + dw2;

            // x = 1, y = 2
            d1200 = dx1 + dy2 + dz0 + dw0;
            d1201 = dx1 + dy2 + dz0 + dw1;
            d1202 = dx1 + dy2 + dz0 + dw2;

            d1210 = dx1 + dy2 + dz1 + dw0;
            d1211 = dx1 + dy2 + dz1 + dw1;
            d1212 = dx1 + dy2 + dz1 + dw2;

            d1220 = dx1 + dy2 + dz2 + dw0;
            d1221 = dx1 + dy2 + dz2 + dw1;
            d1222 = dx1 + dy2 + dz2 + dw2;

            // x = 2, y = 0
            d2000 = dx2 + dy0 + dz0 + dw0;
            d2001 = dx2 + dy0 + dz0 + dw1;
            d2002 = dx2 + dy0 + dz0 + dw2;

            d2010 = dx2 + dy0 + dz1 + dw0;
            d2011 = dx2 + dy0 + dz1 + dw1;
            d2012 = dx2 + dy0 + dz1 + dw2;

            d2020 = dx2 + dy0 + dz2 + dw0;
            d2021 = dx2 + dy0 + dz2 + dw1;
            d2022 = dx2 + dy0 + dz2 + dw2;

            // x = 2, y = 1
            d2100 = dx2 + dy1 + dz0 + dw0;
            d2101 = dx2 + dy1 + dz0 + dw1;
            d2102 = dx2 + dy1 + dz0 + dw2;

            d2110 = dx2 + dy1 + dz1 + dw0;
            d2111 = dx2 + dy1 + dz1 + dw1;
            d2112 = dx2 + dy1 + dz1 + dw2;

            d2120 = dx2 + dy1 + dz2 + dw0;
            d2121 = dx2 + dy1 + dz2 + dw1;
            d2122 = dx2 + dy1 + dz2 + dw2;

            // x = 2, y = 2
            d2200 = dx2 + dy2 + dz0 + dw0;
            d2201 = dx2 + dy2 + dz0 + dw1;
            d2202 = dx2 + dy2 + dz0 + dw2;

            d2210 = dx2 + dy2 + dz1 + dw0;
            d2211 = dx2 + dy2 + dz1 + dw1;
            d2212 = dx2 + dy2 + dz1 + dw2;

            d2220 = dx2 + dy2 + dz2 + dw0;
            d2221 = dx2 + dy2 + dz2 + dw1;
            d2222 = dx2 + dy2 + dz2 + dw2;
        }
    }
    protected abstract float getNoise(@NotNull FloatDistanceVector4D distanceVector);

    protected final class DoubleDistanceVector4D {
        public final double
                d0000, d0001, d0002, d0010, d0011, d0012, d0020, d0021, d0022,
                d0100, d0101, d0102, d0110, d0111, d0112, d0120, d0121, d0122,
                d0200, d0201, d0202, d0210, d0211, d0212, d0220, d0221, d0222,
                d1000, d1001, d1002, d1010, d1011, d1012, d1020, d1021, d1022,
                d1100, d1101, d1102, d1110, d1111, d1112, d1120, d1121, d1122,
                d1200, d1201, d1202, d1210, d1211, d1212, d1220, d1221, d1222,
                d2000, d2001, d2002, d2010, d2011, d2012, d2020, d2021, d2022,
                d2100, d2101, d2102, d2110, d2111, d2112, d2120, d2121, d2122,
                d2200, d2201, d2202, d2210, d2211, d2212, d2220, d2221, d2222;
        private DoubleDistanceVector4D(double x, double y, double z, double w) {
            long xf = floor(x), x0 = xf - 1, x1 = xf, x2 = xf + 1;
            long yf = floor(y), y0 = yf - 1, y1 = yf, y2 = yf + 1;
            long zf = floor(z), z0 = zf - 1, z1 = zf, z2 = zf + 1;
            long wf = floor(w), w0 = wf - 1, w1 = wf, w2 = wf + 1;

            double dx0 = dist(x, hashToCoord(x0, computeHashKey(x0)));
            double dx1 = dist(x, hashToCoord(x1, computeHashKey(x1)));
            double dx2 = dist(x, hashToCoord(x2, computeHashKey(x2)));

            double dy0 = dist(y, hashToCoord(y0, computeHashKey(y0)));
            double dy1 = dist(y, hashToCoord(y1, computeHashKey(y1)));
            double dy2 = dist(y, hashToCoord(y2, computeHashKey(y2)));

            double dz0 = dist(z, hashToCoord(z0, computeHashKey(z0)));
            double dz1 = dist(z, hashToCoord(z1, computeHashKey(z1)));
            double dz2 = dist(z, hashToCoord(z2, computeHashKey(z2)));

            double dw0 = dist(w, hashToCoord(w0, computeHashKey(w0)));
            double dw1 = dist(w, hashToCoord(w1, computeHashKey(w1)));
            double dw2 = dist(w, hashToCoord(w2, computeHashKey(w2)));

            // x = 0, y = 0
            d0000 = dx0 + dy0 + dz0 + dw0;
            d0001 = dx0 + dy0 + dz0 + dw1;
            d0002 = dx0 + dy0 + dz0 + dw2;

            d0010 = dx0 + dy0 + dz1 + dw0;
            d0011 = dx0 + dy0 + dz1 + dw1;
            d0012 = dx0 + dy0 + dz1 + dw2;

            d0020 = dx0 + dy0 + dz2 + dw0;
            d0021 = dx0 + dy0 + dz2 + dw1;
            d0022 = dx0 + dy0 + dz2 + dw2;

            // x = 0, y = 1
            d0100 = dx0 + dy1 + dz0 + dw0;
            d0101 = dx0 + dy1 + dz0 + dw1;
            d0102 = dx0 + dy1 + dz0 + dw2;

            d0110 = dx0 + dy1 + dz1 + dw0;
            d0111 = dx0 + dy1 + dz1 + dw1;
            d0112 = dx0 + dy1 + dz1 + dw2;

            d0120 = dx0 + dy1 + dz2 + dw0;
            d0121 = dx0 + dy1 + dz2 + dw1;
            d0122 = dx0 + dy1 + dz2 + dw2;

            // x = 0, y = 2
            d0200 = dx0 + dy2 + dz0 + dw0;
            d0201 = dx0 + dy2 + dz0 + dw1;
            d0202 = dx0 + dy2 + dz0 + dw2;

            d0210 = dx0 + dy2 + dz1 + dw0;
            d0211 = dx0 + dy2 + dz1 + dw1;
            d0212 = dx0 + dy2 + dz1 + dw2;

            d0220 = dx0 + dy2 + dz2 + dw0;
            d0221 = dx0 + dy2 + dz2 + dw1;
            d0222 = dx0 + dy2 + dz2 + dw2;

            // x = 1, y = 0
            d1000 = dx1 + dy0 + dz0 + dw0;
            d1001 = dx1 + dy0 + dz0 + dw1;
            d1002 = dx1 + dy0 + dz0 + dw2;

            d1010 = dx1 + dy0 + dz1 + dw0;
            d1011 = dx1 + dy0 + dz1 + dw1;
            d1012 = dx1 + dy0 + dz1 + dw2;

            d1020 = dx1 + dy0 + dz2 + dw0;
            d1021 = dx1 + dy0 + dz2 + dw1;
            d1022 = dx1 + dy0 + dz2 + dw2;

            // x = 1, y = 1
            d1100 = dx1 + dy1 + dz0 + dw0;
            d1101 = dx1 + dy1 + dz0 + dw1;
            d1102 = dx1 + dy1 + dz0 + dw2;

            d1110 = dx1 + dy1 + dz1 + dw0;
            d1111 = dx1 + dy1 + dz1 + dw1;
            d1112 = dx1 + dy1 + dz1 + dw2;

            d1120 = dx1 + dy1 + dz2 + dw0;
            d1121 = dx1 + dy1 + dz2 + dw1;
            d1122 = dx1 + dy1 + dz2 + dw2;

            // x = 1, y = 2
            d1200 = dx1 + dy2 + dz0 + dw0;
            d1201 = dx1 + dy2 + dz0 + dw1;
            d1202 = dx1 + dy2 + dz0 + dw2;

            d1210 = dx1 + dy2 + dz1 + dw0;
            d1211 = dx1 + dy2 + dz1 + dw1;
            d1212 = dx1 + dy2 + dz1 + dw2;

            d1220 = dx1 + dy2 + dz2 + dw0;
            d1221 = dx1 + dy2 + dz2 + dw1;
            d1222 = dx1 + dy2 + dz2 + dw2;

            // x = 2, y = 0
            d2000 = dx2 + dy0 + dz0 + dw0;
            d2001 = dx2 + dy0 + dz0 + dw1;
            d2002 = dx2 + dy0 + dz0 + dw2;

            d2010 = dx2 + dy0 + dz1 + dw0;
            d2011 = dx2 + dy0 + dz1 + dw1;
            d2012 = dx2 + dy0 + dz1 + dw2;

            d2020 = dx2 + dy0 + dz2 + dw0;
            d2021 = dx2 + dy0 + dz2 + dw1;
            d2022 = dx2 + dy0 + dz2 + dw2;

            // x = 2, y = 1
            d2100 = dx2 + dy1 + dz0 + dw0;
            d2101 = dx2 + dy1 + dz0 + dw1;
            d2102 = dx2 + dy1 + dz0 + dw2;

            d2110 = dx2 + dy1 + dz1 + dw0;
            d2111 = dx2 + dy1 + dz1 + dw1;
            d2112 = dx2 + dy1 + dz1 + dw2;

            d2120 = dx2 + dy1 + dz2 + dw0;
            d2121 = dx2 + dy1 + dz2 + dw1;
            d2122 = dx2 + dy1 + dz2 + dw2;

            // x = 2, y = 2
            d2200 = dx2 + dy2 + dz0 + dw0;
            d2201 = dx2 + dy2 + dz0 + dw1;
            d2202 = dx2 + dy2 + dz0 + dw2;

            d2210 = dx2 + dy2 + dz1 + dw0;
            d2211 = dx2 + dy2 + dz1 + dw1;
            d2212 = dx2 + dy2 + dz1 + dw2;

            d2220 = dx2 + dy2 + dz2 + dw0;
            d2221 = dx2 + dy2 + dz2 + dw1;
            d2222 = dx2 + dy2 + dz2 + dw2;
        }
    }
    protected abstract double getNoise(@NotNull DoubleDistanceVector4D distanceVector);

    @Override
    public float get(float x, float y, float z, float w) {
        return getNoise(new FloatDistanceVector4D(x, y, z, w));
    }
    @Override
    public double get(double x, double y, double z, double w) {
        return getNoise(new DoubleDistanceVector4D(x, y, z, w));
    }

    protected final class FloatDistanceVectorND {
        public final int N;
        public final @NotNull float[] dVec;
        private FloatDistanceVectorND(@NotNull float[] p) {
            N = p.length;

            float[][] dp = new float[N][3];
            for (int i = 0; i < N; i++) {
                int pf = floor(p[i]), p0 = pf - 1, p1 = pf, p2 = pf + 1;
                dp[i][0] = dist(p[i], (float) hashToCoord(p0, computeHashKey(p0)));
                dp[i][1] = dist(p[i], (float) hashToCoord(p1, computeHashKey(p1)));
                dp[i][2] = dist(p[i], (float) hashToCoord(p2, computeHashKey(p2)));
            }

            int vecLen = 1;
            for (int i = 0; i < N; i++) vecLen *= 3;
            dVec = new float[vecLen];

            for (int d = 0; d < vecLen; d++) {
                int ind = d;
                for (int i = N - 1; i >= 0; i--) {
                    dVec[d] += dp[i][ind % 3];
                    ind /= 3;
                }
            }
        }
    }
    protected abstract float getNoise(@NotNull FloatDistanceVectorND distanceVector);

    protected final class DoubleDistanceVectorND {
        public final int N;
        public final @NotNull double[] dVec;
        private DoubleDistanceVectorND(@NotNull double[] p) {
            N = p.length;

            double[][] dp = new double[N][3];
            for (int i = 0; i < N; i++) {
                long pf = floor(p[i]), p0 = pf - 1, p1 = pf, p2 = pf + 1;
                dp[i][0] = dist(p[i], hashToCoord(p0, computeHashKey(p0)));
                dp[i][1] = dist(p[i], hashToCoord(p1, computeHashKey(p1)));
                dp[i][2] = dist(p[i], hashToCoord(p2, computeHashKey(p2)));
            }

            int vecLen = 1;
            for (int i = 0; i < N; i++) vecLen *= 3;
            dVec = new double[vecLen];

            for (int d = 0; d < vecLen; d++) {
                int ind = d;
                for (int i = N - 1; i >= 0; i--) {
                    dVec[d] += dp[i][ind % 3];
                    ind /= 3;
                }
            }
        }
    }
    protected abstract float getNoise(@NotNull DoubleDistanceVectorND distanceVector);

    @Override
    public float get(@NotNull float[] pos) {
        return getNoise(new FloatDistanceVectorND(pos));
    }
    @Override
    public double get(@NotNull double[] pos) {
        return getNoise(new DoubleDistanceVectorND(pos));
    }
}
