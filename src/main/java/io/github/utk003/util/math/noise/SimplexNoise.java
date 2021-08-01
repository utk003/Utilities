package io.github.utk003.util.math.noise;

import io.github.utk003.util.math.complex.ComplexDouble;
import io.github.utk003.util.math.solve.CubicFormula;
import io.github.utk003.util.math.solve.CubicSolution;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static io.github.utk003.util.math.Constants.*;

// https://en.wikipedia.org/wiki/Simplex_noise
// patented until ~ January 8, 2022
@ScheduledForRelease
@RequiresDocumentation
public final class SimplexNoise extends GradientNoise implements Noise {
    public SimplexNoise() {
        super();
    }
    public SimplexNoise(long seed) {
        super(seed);
    }

    public SimplexNoise(long defaultPeriod, @NotNull long[] periods) {
        super(defaultPeriod, periods);
    }
    public SimplexNoise(long seed, long defaultPeriod, @NotNull long[] periods) {
        super(seed, defaultPeriod, periods);
    }

    public enum SimplexInterpolation {
        CONTINUOUS(
                0.5f,
                SimplexNoiseBounds.CONTINUOUS_1D_BOUND,
                SimplexNoiseBounds.CONTINUOUS_2D_BOUND,
                SimplexNoiseBounds.CONTINUOUS_3D_BOUND,
                SimplexNoiseBounds.CONTINUOUS_4D_BOUND
        ),
        TRADITIONAL(
                0.6f,
                SimplexNoiseBounds.TRADITIONAL_1D_BOUND,
                SimplexNoiseBounds.TRADITIONAL_2D_BOUND,
                SimplexNoiseBounds.TRADITIONAL_3D_BOUND,
                SimplexNoiseBounds.TRADITIONAL_4D_BOUND
        );

        private final float FLOAT_R_SQUARED;
        private final double DOUBLE_R_SQUARED;
        SimplexInterpolation(float r2, double d1, double d2, double d3, double d4) {
            DOUBLE_R_SQUARED = FLOAT_R_SQUARED = r2;

            FLOAT_1D_NOISE_BOUND = (float) (DOUBLE_1D_NOISE_BOUND = d1);
            FLOAT_2D_NOISE_BOUND = (float) (DOUBLE_2D_NOISE_BOUND = d2);
            FLOAT_3D_NOISE_BOUND = (float) (DOUBLE_3D_NOISE_BOUND = d3);
            FLOAT_4D_NOISE_BOUND = (float) (DOUBLE_4D_NOISE_BOUND = d4);
        }

        private final float FLOAT_1D_NOISE_BOUND, FLOAT_2D_NOISE_BOUND, FLOAT_3D_NOISE_BOUND, FLOAT_4D_NOISE_BOUND;
        private final double DOUBLE_1D_NOISE_BOUND, DOUBLE_2D_NOISE_BOUND, DOUBLE_3D_NOISE_BOUND, DOUBLE_4D_NOISE_BOUND;
    }

    private @NotNull SimplexInterpolation interpolationType = SimplexInterpolation.TRADITIONAL;
    public @NotNull SimplexInterpolation getInterpolationType() {
        return interpolationType;
    }
    @Contract("_ -> this")
    public @NotNull SimplexNoise setInterpolationType(@NotNull SimplexInterpolation interpolationType) {
        this.interpolationType = interpolationType;
        return this;
    }

    private float computeNoiseContribution(float dx /*, 1D gradient is always <1> */) {
        // compute distance squared (d^2)
        float distSquared = dx * dx;

        // if d^2 >= r^2, mult = 0
        if (distSquared >= interpolationType.FLOAT_R_SQUARED) return 0.0f;

        // get multiplier = max{0, r^2 - d^2}
        float mult = interpolationType.FLOAT_R_SQUARED - distSquared;

        // get dot product = dx * 1 = dx
        // return mult^4 * dp
        return mult * mult * mult * mult * dx;
    }
    private double computeNoiseContribution(double dx /*, 1D gradient is always <1> */) {
        // compute distance squared (d^2)
        double distSquared = dx * dx;

        // if d^2 >= r^2, mult = 0
        if (distSquared >= interpolationType.DOUBLE_R_SQUARED) return 0.0;

        // get multiplier = max{0, r^2 - d^2}
        double mult = interpolationType.DOUBLE_R_SQUARED - distSquared;

        // get dot product = dx * 1 = dx
        // return mult^4 * dp
        return mult * mult * mult * mult * dx;
    }

    private float computeNoiseContribution(float dx, float dy, @NotNull FloatGradient2D grad) {
        // compute distance squared (d^2)
        float distSquared = dx * dx + dy * dy;

        // if d^2 >= r^2, mult = 0
        if (distSquared >= interpolationType.FLOAT_R_SQUARED) return 0.0f;

        // get multiplier = max{0, r^2 - d^2}^4
        float mult = interpolationType.FLOAT_R_SQUARED - distSquared;
        mult *= mult;
        mult *= mult;

        // get dot product
        float dp = dx * grad.dx + dy * grad.dy;

        // return mult * dp
        return mult * dp;
    }
    private double computeNoiseContribution(double dx, double dy, @NotNull DoubleGradient2D grad) {
        // compute distance squared (d^2)
        double distSquared = dx * dx + dy * dy;

        // if d^2 >= r^2, mult = 0
        if (distSquared >= interpolationType.DOUBLE_R_SQUARED) return 0.0;

        // get multiplier = max{0, r^2 - d^2}^4
        double mult = interpolationType.DOUBLE_R_SQUARED - distSquared;
        mult *= mult;
        mult *= mult;

        // get dot product
        double dp = dx * grad.dx + dy * grad.dy;

        // return mult * dp
        return mult * dp;
    }

    private float computeNoiseContribution(float dx, float dy, float dz, @NotNull FloatGradient3D grad) {
        // compute distance squared (d^2)
        float distSquared = dx * dx + dy * dy + dz * dz;

        // if d^2 >= r^2, mult = 0
        if (distSquared >= interpolationType.FLOAT_R_SQUARED) return 0.0f;

        // get multiplier = max{0, r^2 - d^2}^4
        float mult = interpolationType.FLOAT_R_SQUARED - distSquared;
        mult *= mult;
        mult *= mult;

        // get dot product
        float dp = dx * grad.dx + dy * grad.dy + dz * grad.dz;

        // return mult * dp
        return mult * dp;
    }
    private double computeNoiseContribution(double dx, double dy, double dz, @NotNull DoubleGradient3D grad) {
        // compute distance squared (d^2)
        double distSquared = dx * dx + dy * dy + dz * dz;

        // if d^2 >= r^2, mult = 0
        if (distSquared >= interpolationType.DOUBLE_R_SQUARED) return 0.0;

        // get multiplier = max{0, r^2 - d^2}^4
        double mult = interpolationType.DOUBLE_R_SQUARED - distSquared;
        mult *= mult;
        mult *= mult;

        // get dot product
        double dp = dx * grad.dx + dy * grad.dy + dz * grad.dz;

        // return mult * dp
        return mult * dp;
    }

    private float computeNoiseContribution(float dx, float dy, float dz, float dw, @NotNull FloatGradient4D grad) {
        // compute distance squared (d^2)
        float distSquared = dx * dx + dy * dy + dz * dz + dw * dw;

        // if d^2 >= r^2, mult = 0
        if (distSquared >= interpolationType.FLOAT_R_SQUARED) return 0.0f;

        // get multiplier = max{0, r^2 - d^2}^4
        float mult = interpolationType.FLOAT_R_SQUARED - distSquared;
        mult *= mult;
        mult *= mult;

        // get dot product
        float dp = dx * grad.dx + dy * grad.dy + dz * grad.dz + dw * grad.dw;

        // return mult * dp
        return mult * dp;
    }
    private double computeNoiseContribution(double dx, double dy, double dz, double dw, @NotNull DoubleGradient4D grad) {
        // compute distance squared (d^2)
        double distSquared = dx * dx + dy * dy + dz * dz + dw * dw;

        // if d^2 >= r^2, mult = 0
        if (distSquared >= interpolationType.DOUBLE_R_SQUARED) return 0.0;

        // get multiplier = max{0, r^2 - d^2}^4
        double mult = interpolationType.DOUBLE_R_SQUARED - distSquared;
        mult *= mult;
        mult *= mult;

        // get dot product
        double dp = dx * grad.dx + dy * grad.dy + dz * grad.dz + dw * grad.dw;

        // return mult * dp
        return mult * dp;
    }

    private float computeNoiseContribution(int dim, @NotNull float[] disp, @NotNull float[] grad) {
        // compute distance squared (d^2)
        float distSquared = 0.0f;
        for (float df : disp)
            distSquared += df * df;

        // if d^2 >= r^2, mult = 0
        if (distSquared >= interpolationType.FLOAT_R_SQUARED) return 0.0f;

        // get multiplier = max{0, r^2 - d^2}^4
        float mult = interpolationType.FLOAT_R_SQUARED - distSquared;
        mult *= mult;
        mult *= mult;

        // get dot product
        float dp = 0.0f;
        for (int i = 0; i < dim; i++)
            dp += disp[i] * grad[i];

        // return mult * dp
        return mult * dp;
    }
    private double computeNoiseContribution(int dim, @NotNull double[] disp, @NotNull double[] grad) {
        // compute distance squared (d^2)
        double distSquared = 0.0;
        for (double df : disp)
            distSquared += df * df;

        // if d^2 >= r^2, mult = 0
        if (distSquared >= interpolationType.DOUBLE_R_SQUARED) return 0.0;

        // get multiplier = max{0, r^2 - d^2}^4
        double mult = interpolationType.DOUBLE_R_SQUARED - distSquared;
        mult *= mult;
        mult *= mult;

        // get dot product
        double dp = 0.0;
        for (int i = 0; i < dim; i++)
            dp += disp[i] * grad[i];

        // return mult * dp
        return mult * dp;
    }

    // √(N) && 1/√(N)
    private static final double
            DOUBLE_SQRT_2 = SQRT_2, DOUBLE_INV_SQRT_2 = 1 / SQRT_2,
            DOUBLE_SQRT_3 = SQRT_3, DOUBLE_INV_SQRT_3 = 1 / SQRT_3,
            DOUBLE_SQRT_4 = 2.0000, DOUBLE_INV_SQRT_4 = 0.5000,
            DOUBLE_SQRT_5 = SQRT_5, DOUBLE_INV_SQRT_5 = 1 / SQRT_5;
    private static final float
            FLOAT_SQRT_2 = (float) DOUBLE_SQRT_2, FLOAT_INV_SQRT_2 = (float) DOUBLE_INV_SQRT_2,
            FLOAT_SQRT_3 = (float) DOUBLE_SQRT_3, FLOAT_INV_SQRT_3 = (float) DOUBLE_INV_SQRT_3,
            FLOAT_SQRT_4 = (float) DOUBLE_SQRT_4, FLOAT_INV_SQRT_4 = (float) DOUBLE_INV_SQRT_4,
            FLOAT_SQRT_5 = (float) DOUBLE_SQRT_5, FLOAT_INV_SQRT_5 = (float) DOUBLE_INV_SQRT_5;

    @Override
    public float get(float x) {
        // F = FLOAT_SQRT_2 - 1         // F = [√(N+1) - 1] / N
        // G = 1 - FLOAT_INV_SQRT_2     // G = [1-1/√(N+1)] / N

        // xhh = x + Fx = x (F + 1) = x √(2)
        // so0xhh = xf, so1xhh = xf + 1 = xc
        // so0x = so0xhh(1-g) = so0xhh / √(2), so1x = so1xhh / √(2)
        // d0x = x - so0x, d1x = x - so1x

        int xf = floor(x * FLOAT_SQRT_2), xc = xf + 1;
        float noise = computeNoiseContribution(x - xf * FLOAT_INV_SQRT_2) +
                computeNoiseContribution(x - xc * FLOAT_INV_SQRT_2);
        return noise / interpolationType.FLOAT_1D_NOISE_BOUND;
    }
    @Override
    public double get(double x) {
        // F = DOUBLE_SQRT_2 - 1        // F = [√(N+1) - 1] / N
        // G = 1 - DOUBLE_INV_SQRT_2    // G = [1-1/√(N+1)] / N

        // xhh = x + Fx = x (F + 1) = x √(2)
        // xf = floor(xhh), xc = xf + 1
        // so0xhh = xf, so1xhh = xf + 1 = xc
        // so0x = so0xhh(1-g) = so0xhh / √(2), so1x = so1xhh / √(2)
        // d0x = x - so0x, d1x = x - so1x
        // n0 = compNoise(d0x), n1 = compNoise(d1x)

        long xf = floor(x * DOUBLE_SQRT_2), xc = xf + 1;
        double noise = computeNoiseContribution(x - xf * DOUBLE_INV_SQRT_2) +
                computeNoiseContribution(x - xc * DOUBLE_INV_SQRT_2);
        return noise / interpolationType.DOUBLE_1D_NOISE_BOUND;
    }

    @Override
    public float get(float x, float y) {
        final float
                F = (FLOAT_SQRT_3 - 1) / 2,     // F = [√(N+1) - 1] / N
                G = (1 - FLOAT_INV_SQRT_3) / 2; // G = [1-1/√(N+1)] / N

        // hypercubic honeycomb (https://en.wikipedia.org/wiki/Hypercubic_honeycomb)
        float xhh, yhh;
        {
            float addend = F * (x + y);
            xhh = x + addend;
            yhh = y + addend;
        }

        // xf = floor, xi = fractional part
        int xf = floor(xhh), yf = floor(yhh);
        float xi = xhh - xf, yi = yhh - yf;

        // Schläfli orthoscheme (https://en.wikipedia.org/wiki/Schl%C3%A4fli_orthoscheme)
        int so0xhh = 0, so0yhh = 0,
                so1xhh = xi > yi ? 1 : 0, so1yhh = 1 - so1xhh,
                so2xhh = 1, so2yhh = 1;

        // shift simplex to correct position
        so0xhh += xf;
        so0yhh += yf;

        so1xhh += xf;
        so1yhh += yf;

        so2xhh += xf;
        so2yhh += yf;

        // get gradients
        FloatGradient2D g0 = getFloatGradient(so0xhh, so0yhh),
                g1 = getFloatGradient(so1xhh, so1yhh),
                g2 = getFloatGradient(so2xhh, so2yhh);

        // convert simplex vertices back to normal coordinate basis
        float so0x, so0y;
        {
            float subtrahend = G * (so0xhh + so0yhh);
            so0x = so0xhh - subtrahend;
            so0y = so0yhh - subtrahend;
        }
        float so1x, so1y;
        {
            float subtrahend = G * (so1xhh + so1yhh);
            so1x = so1xhh - subtrahend;
            so1y = so1yhh - subtrahend;
        }
        float so2x, so2y;
        {
            float subtrahend = G * (so2xhh + so2yhh);
            so2x = so2xhh - subtrahend;
            so2y = so2yhh - subtrahend;
        }

        // compute displacement vectors
        float d0x = x - so0x, d0y = y - so0y,
                d1x = x - so1x, d1y = y - so1y,
                d2x = x - so2x, d2y = y - so2y;

        // compute noise contribution
        float n0 = computeNoiseContribution(d0x, d0y, g0),
                n1 = computeNoiseContribution(d1x, d1y, g1),
                n2 = computeNoiseContribution(d2x, d2y, g2);

        // return cumulative noise
        return (n0 + n1 + n2) / interpolationType.FLOAT_2D_NOISE_BOUND;
    }
    @Override
    public double get(double x, double y) {
        final double
                F = (DOUBLE_SQRT_3 - 1) / 2,        // F = [√(N+1) - 1] / N
                G = (1 - DOUBLE_INV_SQRT_3) / 2;    // G = [1-1/√(N+1)] / N

        // hypercubic honeycomb (https://en.wikipedia.org/wiki/Hypercubic_honeycomb)
        double xhh, yhh;
        {
            double addend = F * (x + y);
            xhh = x + addend;
            yhh = y + addend;
        }

        // xf = floor, xi = fractional part
        long xf = floor(xhh), yf = floor(yhh);
        double xi = xhh - xf, yi = yhh - yf;

        // Schläfli orthoscheme (https://en.wikipedia.org/wiki/Schl%C3%A4fli_orthoscheme)
        long so0xhh = 0, so0yhh = 0,
                so1xhh = xi > yi ? 1 : 0, so1yhh = 1 - so1xhh,
                so2xhh = 1, so2yhh = 1;

        // shift simplex to correct position
        so0xhh += xf;
        so0yhh += yf;

        so1xhh += xf;
        so1yhh += yf;

        so2xhh += xf;
        so2yhh += yf;

        // get gradients
        DoubleGradient2D g0 = getDoubleGradient(so0xhh, so0yhh),
                g1 = getDoubleGradient(so1xhh, so1yhh),
                g2 = getDoubleGradient(so2xhh, so2yhh);

        // convert simplex vertices back to normal coordinate basis
        double so0x, so0y;
        {
            double subtrahend = G * (so0xhh + so0yhh);
            so0x = so0xhh - subtrahend;
            so0y = so0yhh - subtrahend;
        }
        double so1x, so1y;
        {
            double subtrahend = G * (so1xhh + so1yhh);
            so1x = so1xhh - subtrahend;
            so1y = so1yhh - subtrahend;
        }
        double so2x, so2y;
        {
            double subtrahend = G * (so2xhh + so2yhh);
            so2x = so2xhh - subtrahend;
            so2y = so2yhh - subtrahend;
        }

        // compute displacement vectors
        double d0x = x - so0x, d0y = y - so0y,
                d1x = x - so1x, d1y = y - so1y,
                d2x = x - so2x, d2y = y - so2y;

        // compute noise contribution
        double n0 = computeNoiseContribution(d0x, d0y, g0),
                n1 = computeNoiseContribution(d1x, d1y, g1),
                n2 = computeNoiseContribution(d2x, d2y, g2);

        // return cumulative noise
        return (n0 + n1 + n2) / interpolationType.DOUBLE_2D_NOISE_BOUND;
    }

    @Override
    public float get(float x, float y, float z) {
        final float
                F = (FLOAT_SQRT_4 - 1) / 3,     // F = [√(N+1) - 1] / N
                G = (1 - FLOAT_INV_SQRT_4) / 3; // G = [1-1/√(N+1)] / N

        // hypercubic honeycomb (https://en.wikipedia.org/wiki/Hypercubic_honeycomb)
        float xhh, yhh, zhh;
        {
            float addend = F * (x + y + z);
            xhh = x + addend;
            yhh = y + addend;
            zhh = z + addend;
        }

        // xf = floor, xi = fractional part
        int xf = floor(xhh), yf = floor(yhh), zf = floor(zhh);
        float xi = xhh - xf, yi = yhh - yf, zi = zhh - zf;

        // Schläfli orthoscheme (https://en.wikipedia.org/wiki/Schl%C3%A4fli_orthoscheme)
        int so0xhh = 0, so0yhh = 0, so0zhh = 0,
                so1xhh = 0, so1yhh = 0, so1zhh = 0,
                so2xhh = 0, so2yhh = 0, so2zhh = 0,
                so3xhh = 1, so3yhh = 1, so3zhh = 1;
        if (xi > yi) {
            if (xi > zi) {
                // x > y,z
                so1xhh = so2xhh = 1;
                if (yi > zi)
                    // x > y > z
                    so2yhh = 1;
                else /* zi > yi */
                    // x > z > y
                    so2zhh = 1;
            } else /* zi > xi */ {
                // z > x > y
                so1zhh = so2zhh = 1;
                so2xhh = 1;
            }
        } else /* yi > xi */ {
            if (xi > zi) {
                // y > x > z
                so1yhh = so2yhh = 1;
                so2xhh = 1;
            } else /* zi > xi */ {
                // y,z > x
                so2yhh = so2zhh = 1;
                if (yi > zi)
                    // y > z > x
                    so1yhh = 1;
                else /* zi > yi */
                    // z > y > x
                    so1zhh = 1;
            }
        }

        // shift simplex to correct position
        so0xhh += xf;
        so0yhh += yf;
        so0zhh += zf;

        so1xhh += xf;
        so1yhh += yf;
        so1zhh += zf;

        so2xhh += xf;
        so2yhh += yf;
        so2zhh += zf;

        so3xhh += xf;
        so3yhh += yf;
        so3zhh += zf;

        // get gradients
        FloatGradient3D g0 = getFloatGradient(so0xhh, so0yhh, so0zhh),
                g1 = getFloatGradient(so1xhh, so1yhh, so1zhh),
                g2 = getFloatGradient(so2xhh, so2yhh, so2zhh),
                g3 = getFloatGradient(so3xhh, so3yhh, so3zhh);

        // convert simplex vertices back to normal coordinate basis
        float so0x, so0y, so0z;
        {
            float subtrahend = G * (so0xhh + so0yhh + so0zhh);
            so0x = so0xhh - subtrahend;
            so0y = so0yhh - subtrahend;
            so0z = so0zhh - subtrahend;
        }
        float so1x, so1y, so1z;
        {
            float subtrahend = G * (so1xhh + so1yhh + so1zhh);
            so1x = so1xhh - subtrahend;
            so1y = so1yhh - subtrahend;
            so1z = so1zhh - subtrahend;
        }
        float so2x, so2y, so2z;
        {
            float subtrahend = G * (so2xhh + so2yhh + so2zhh);
            so2x = so2xhh - subtrahend;
            so2y = so2yhh - subtrahend;
            so2z = so2zhh - subtrahend;
        }
        float so3x, so3y, so3z;
        {
            float subtrahend = G * (so3xhh + so3yhh + so3zhh);
            so3x = so3xhh - subtrahend;
            so3y = so3yhh - subtrahend;
            so3z = so3zhh - subtrahend;
        }

        // compute displacement vectors
        float d0x = x - so0x, d0y = y - so0y, d0z = z - so0z,
                d1x = x - so1x, d1y = y - so1y, d1z = z - so1z,
                d2x = x - so2x, d2y = y - so2y, d2z = z - so2z,
                d3x = x - so3x, d3y = y - so3y, d3z = z - so3z;

        // compute noise contribution
        float n0 = computeNoiseContribution(d0x, d0y, d0z, g0),
                n1 = computeNoiseContribution(d1x, d1y, d1z, g1),
                n2 = computeNoiseContribution(d2x, d2y, d2z, g2),
                n3 = computeNoiseContribution(d3x, d3y, d3z, g3);

        // return cumulative noise
        return (n0 + n1 + n2 + n3) / interpolationType.FLOAT_3D_NOISE_BOUND;
    }
    @Override
    public double get(double x, double y, double z) {
        final double
                F = (DOUBLE_SQRT_4 - 1) / 3,        // F = [√(N+1) - 1] / N
                G = (1 - DOUBLE_INV_SQRT_4) / 3;    // G = [1-1/√(N+1)] / N

        // hypercubic honeycomb (https://en.wikipedia.org/wiki/Hypercubic_honeycomb)
        double xhh, yhh, zhh;
        {
            double addend = F * (x + y + z);
            xhh = x + addend;
            yhh = y + addend;
            zhh = z + addend;
        }

        // xf = floor, xi = fractional part
        long xf = floor(xhh), yf = floor(yhh), zf = floor(zhh);
        double xi = xhh - xf, yi = yhh - yf, zi = zhh - zf;

        // Schläfli orthoscheme (https://en.wikipedia.org/wiki/Schl%C3%A4fli_orthoscheme)
        long so0xhh = 0, so0yhh = 0, so0zhh = 0,
                so1xhh = 0, so1yhh = 0, so1zhh = 0,
                so2xhh = 0, so2yhh = 0, so2zhh = 0,
                so3xhh = 1, so3yhh = 1, so3zhh = 1;
        if (xi > yi) {
            if (xi > zi) {
                // x > y,z
                so1xhh = so2xhh = 1;
                if (yi > zi)
                    // x > y > z
                    so2yhh = 1;
                else /* zi > yi */
                    // x > z > y
                    so2zhh = 1;
            } else /* zi > xi */ {
                // z > x > y
                so1zhh = so2zhh = 1;
                so2xhh = 1;
            }
        } else /* yi > xi */ {
            if (xi > zi) {
                // y > x > z
                so1yhh = so2yhh = 1;
                so2xhh = 1;
            } else /* zi > xi */ {
                // y,z > x
                so2yhh = so2zhh = 1;
                if (yi > zi)
                    // y > z > x
                    so1yhh = 1;
                else /* zi > yi */
                    // z > y > x
                    so1zhh = 1;
            }
        }

        // shift simplex to correct position
        so0xhh += xf;
        so0yhh += yf;
        so0zhh += zf;

        so1xhh += xf;
        so1yhh += yf;
        so1zhh += zf;

        so2xhh += xf;
        so2yhh += yf;
        so2zhh += zf;

        so3xhh += xf;
        so3yhh += yf;
        so3zhh += zf;

        // get gradients
        DoubleGradient3D g0 = getDoubleGradient(so0xhh, so0yhh, so0zhh),
                g1 = getDoubleGradient(so1xhh, so1yhh, so1zhh),
                g2 = getDoubleGradient(so2xhh, so2yhh, so2zhh),
                g3 = getDoubleGradient(so3xhh, so3yhh, so3zhh);

        // convert simplex vertices back to normal coordinate basis
        double so0x, so0y, so0z;
        {
            double subtrahend = G * (so0xhh + so0yhh + so0zhh);
            so0x = so0xhh - subtrahend;
            so0y = so0yhh - subtrahend;
            so0z = so0zhh - subtrahend;
        }
        double so1x, so1y, so1z;
        {
            double subtrahend = G * (so1xhh + so1yhh + so1zhh);
            so1x = so1xhh - subtrahend;
            so1y = so1yhh - subtrahend;
            so1z = so1zhh - subtrahend;
        }
        double so2x, so2y, so2z;
        {
            double subtrahend = G * (so2xhh + so2yhh + so2zhh);
            so2x = so2xhh - subtrahend;
            so2y = so2yhh - subtrahend;
            so2z = so2zhh - subtrahend;
        }
        double so3x, so3y, so3z;
        {
            double subtrahend = G * (so3xhh + so3yhh + so3zhh);
            so3x = so3xhh - subtrahend;
            so3y = so3yhh - subtrahend;
            so3z = so3zhh - subtrahend;
        }

        // compute displacement vectors
        double d0x = x - so0x, d0y = y - so0y, d0z = z - so0z,
                d1x = x - so1x, d1y = y - so1y, d1z = z - so1z,
                d2x = x - so2x, d2y = y - so2y, d2z = z - so2z,
                d3x = x - so3x, d3y = y - so3y, d3z = z - so3z;

        // compute noise contribution
        double n0 = computeNoiseContribution(d0x, d0y, d0z, g0),
                n1 = computeNoiseContribution(d1x, d1y, d1z, g1),
                n2 = computeNoiseContribution(d2x, d2y, d2z, g2),
                n3 = computeNoiseContribution(d3x, d3y, d3z, g3);

        // return cumulative noise
        return (n0 + n1 + n2 + n3) / interpolationType.DOUBLE_3D_NOISE_BOUND;
    }

    @Override
    public float get(float x, float y, float z, float w) {
        final float
                F = (FLOAT_SQRT_5 - 1) / 4,     // F = [√(N+1) - 1] / N
                G = (1 - FLOAT_INV_SQRT_5) / 4; // G = [1-1/√(N+1)] / N

        // hypercubic honeycomb (https://en.wikipedia.org/wiki/Hypercubic_honeycomb)
        float xhh, yhh, zhh, whh;
        {
            float addend = F * (x + y + z + w);
            xhh = x + addend;
            yhh = y + addend;
            zhh = z + addend;
            whh = w + addend;
        }

        // xf = floor, xi = fractional part
        int xf = floor(xhh), yf = floor(yhh), zf = floor(zhh), wf = floor(whh);
        float xi = xhh - xf, yi = yhh - yf, zi = zhh - zf, wi = whh - wf;

        // Schläfli orthoscheme (https://en.wikipedia.org/wiki/Schl%C3%A4fli_orthoscheme)
        int so0xhh = 0, so0yhh = 0, so0zhh = 0, so0whh = 0,
                so1xhh = 0, so1yhh = 0, so1zhh = 0, so1whh = 0,
                so2xhh = 0, so2yhh = 0, so2zhh = 0, so2whh = 0,
                so3xhh = 0, so3yhh = 0, so3zhh = 0, so3whh = 0,
                so4xhh = 1, so4yhh = 1, so4zhh = 1, so4whh = 1;
        if (xi > yi) {
            if (xi > zi) {
                // x > y,z
                if (xi > wi) {
                    // x > y,z,w
                    so1xhh = so2xhh = so3xhh = 1;
                    if (yi > zi) {
                        //  x > y > z & x > w
                        if (yi > wi) {
                            // x > y > z,w
                            so2yhh = so3yhh = 1;
                            if (zi > wi)
                                // x > y > z > w
                                so3zhh = 1;
                            else /* wi > zi */
                                // x > y > w > z
                                so3whh = 1;
                        } else /* wi > yi */ {
                            // x > w > y > z
                            so2whh = so3whh = 1;
                            so3yhh = 1;
                        }
                    } else /* zi > yi */ {
                        // x > z > y & x > w
                        if (yi > wi) {
                            // x > z > y > w
                            so2zhh = so3zhh = 1;
                            so3yhh = 1;
                        } else /* wi > yi */ {
                            // x > z,w > y
                            so3zhh = so3whh = 1;
                            if (zi > wi)
                                // x > z > w > y
                                so2zhh = 1;
                            else /* wi > zi */
                                // x > w > z > y
                                so2whh = 1;
                        }
                    }
                } else /* wi > xi */ {
                    // w > x > y,z
                    so1whh = so2whh = so3whh = 1;
                    so2xhh = so3xhh = 1;
                    if (yi > zi)
                        // w > x > y > z
                        so3yhh = 1;
                    else /* zi > yi */
                        // w > x > z > y
                        so3zhh = 1;
                }
            } else /* zi > xi */ {
                // z > x > y
                if (xi > wi) {
                    // z > x > y,w
                    so1zhh = so2zhh = so3zhh = 1;
                    so2xhh = so3xhh = 1;
                    if (yi > wi)
                        // z > x > y > w
                        so3yhh = 1;
                    else /* wi > yi */
                        // z > x > w > y
                        so3whh = 1;
                } else /* wi > xi */ {
                    // z,w > x > y
                    so2zhh = so2whh = 1;
                    so3xhh = so3zhh = so3whh = 1;
                    if (zi > wi)
                        // z > w > x > y
                        so1zhh = 1;
                    else /* wi > zi */
                        // w > z > x > y
                        so1whh = 1;
                }
            }
        } else /* yi > xi */ {
            if (xi > zi) {
                // y > x > z
                if (xi > wi) {
                    // y > x > z,w
                    so1yhh = so2yhh = so3yhh = 1;
                    so2xhh = so3xhh = 1;
                    if (zi > wi)
                        // y > x > z > w
                        so3zhh = 1;
                    else /* wi > zi */
                        // y > x > w > z
                        so3whh = 1;
                } else /* wi > xi */ {
                    // y,w > x > z
                    so2yhh = so2whh = 1;
                    so3xhh = so3yhh = so3whh = 1;
                    if (yi > wi)
                        // y > w > x > z
                        so1yhh = 1;
                    else /* wi > yi */
                        // w > y > x > z
                        so1whh = 1;
                }
            } else /* zi > xi */ {
                // y,z > x
                if (xi > wi) {
                    // y,z > x > w
                    so2yhh = so2zhh = 1;
                    so3xhh = so3yhh = so3zhh = 1;
                    if (yi > zi)
                        // y > z > x > w
                        so1yhh = 1;
                    else /* zi > yi */
                        // z > y > x > w
                        so1zhh = 1;
                } else /* wi > xi */ {
                    // y,z,w > x
                    if (yi > zi) {
                        // y > z > x & w > x
                        if (yi > wi) {
                            // y > z,w > x
                            so1yhh = so2yhh = so3yhh = 1;
                            so3zhh = so3whh = 1;
                            if (zi > wi)
                                // y > z > w > x
                                so2zhh = 1;
                            else /* wi > zi */
                                // y > w > z > x
                                so2whh = 1;
                        } else /* wi > yi */ {
                            // w > y > z > x
                            so1whh = so2whh = so3whh = 1;
                            so2yhh = so3yhh = 1;
                            so3zhh = 1;
                        }
                    } else /* zi > yi */ {
                        // z > y > x & w > x
                        if (yi > wi) {
                            // z > y > w > x
                            so1zhh = so2zhh = so3zhh = 1;
                            so2yhh = so3yhh = 1;
                            so3whh = 1;
                        } else /* wi > yi */ {
                            // z,w > y > x
                            so2zhh = so2whh = 1;
                            so3yhh = so3zhh = so3whh = 1;
                            if (zi > wi)
                                // z > w > y > x
                                so1zhh = 1;
                            else /* wi > zi */
                                // w > z > y > x
                                so1whh = 1;
                        }
                    }
                }
            }
        }

        // shift simplex to correct position
        so0xhh += xf;
        so0yhh += yf;
        so0zhh += zf;
        so0whh += wf;

        so1xhh += xf;
        so1yhh += yf;
        so1zhh += zf;
        so1whh += wf;

        so2xhh += xf;
        so2yhh += yf;
        so2zhh += zf;
        so2whh += wf;

        so3xhh += xf;
        so3yhh += yf;
        so3zhh += zf;
        so3whh += wf;

        so4xhh += xf;
        so4yhh += yf;
        so4zhh += zf;
        so4whh += wf;

        // get gradients
        FloatGradient4D g0 = getFloatGradient(so0xhh, so0yhh, so0zhh, so0whh),
                g1 = getFloatGradient(so1xhh, so1yhh, so1zhh, so1whh),
                g2 = getFloatGradient(so2xhh, so2yhh, so2zhh, so2whh),
                g3 = getFloatGradient(so3xhh, so3yhh, so3zhh, so3whh),
                g4 = getFloatGradient(so4xhh, so4yhh, so4zhh, so4whh);

        // convert simplex vertices back to normal coordinate basis
        float so0x, so0y, so0z, so0w;
        {
            float subtrahend = G * (so0xhh + so0yhh + so0zhh + so0whh);
            so0x = so0xhh - subtrahend;
            so0y = so0yhh - subtrahend;
            so0z = so0zhh - subtrahend;
            so0w = so0whh - subtrahend;
        }
        float so1x, so1y, so1z, so1w;
        {
            float subtrahend = G * (so1xhh + so1yhh + so1zhh + so1whh);
            so1x = so1xhh - subtrahend;
            so1y = so1yhh - subtrahend;
            so1z = so1zhh - subtrahend;
            so1w = so1whh - subtrahend;
        }
        float so2x, so2y, so2z, so2w;
        {
            float subtrahend = G * (so2xhh + so2yhh + so2zhh + so2whh);
            so2x = so2xhh - subtrahend;
            so2y = so2yhh - subtrahend;
            so2z = so2zhh - subtrahend;
            so2w = so2whh - subtrahend;
        }
        float so3x, so3y, so3z, so3w;
        {
            float subtrahend = G * (so3xhh + so3yhh + so3zhh + so3whh);
            so3x = so3xhh - subtrahend;
            so3y = so3yhh - subtrahend;
            so3z = so3zhh - subtrahend;
            so3w = so3whh - subtrahend;
        }
        float so4x, so4y, so4z, so4w;
        {
            float subtrahend = G * (so4xhh + so4yhh + so4zhh + so4whh);
            so4x = so4xhh - subtrahend;
            so4y = so4yhh - subtrahend;
            so4z = so4zhh - subtrahend;
            so4w = so4whh - subtrahend;
        }

        // compute displacement vectors
        float d0x = x - so0x, d0y = y - so0y, d0z = z - so0z, d0w = w - so0w,
                d1x = x - so1x, d1y = y - so1y, d1z = z - so1z, d1w = w - so1w,
                d2x = x - so2x, d2y = y - so2y, d2z = z - so2z, d2w = w - so2w,
                d3x = x - so3x, d3y = y - so3y, d3z = z - so3z, d3w = w - so3w,
                d4x = x - so4x, d4y = y - so4y, d4z = z - so4z, d4w = w - so4w;

        // compute noise contribution
        float n0 = computeNoiseContribution(d0x, d0y, d0z, d0w, g0),
                n1 = computeNoiseContribution(d1x, d1y, d1z, d1w, g1),
                n2 = computeNoiseContribution(d2x, d2y, d2z, d2w, g2),
                n3 = computeNoiseContribution(d3x, d3y, d3z, d3w, g3),
                n4 = computeNoiseContribution(d4x, d4y, d4z, d4w, g4);

        // return cumulative noise
        return (n0 + n1 + n2 + n3 + n4) / interpolationType.FLOAT_4D_NOISE_BOUND;
    }
    @Override
    public double get(double x, double y, double z, double w) {
        final double
                F = (DOUBLE_SQRT_5 - 1) / 4,     // F = [√(N+1) - 1] / N
                G = (1 - DOUBLE_INV_SQRT_5) / 4; // G = [1-1/√(N+1)] / N

        // hypercubic honeycomb (https://en.wikipedia.org/wiki/Hypercubic_honeycomb)
        double xhh, yhh, zhh, whh;
        {
            double addend = F * (x + y + z + w);
            xhh = x + addend;
            yhh = y + addend;
            zhh = z + addend;
            whh = w + addend;
        }

        // xf = floor, xi = fractional part
        long xf = floor(xhh), yf = floor(yhh), zf = floor(zhh), wf = floor(whh);
        double xi = xhh - xf, yi = yhh - yf, zi = zhh - zf, wi = whh - wf;

        // Schläfli orthoscheme (https://en.wikipedia.org/wiki/Schl%C3%A4fli_orthoscheme)
        long so0xhh = 0, so0yhh = 0, so0zhh = 0, so0whh = 0,
                so1xhh = 0, so1yhh = 0, so1zhh = 0, so1whh = 0,
                so2xhh = 0, so2yhh = 0, so2zhh = 0, so2whh = 0,
                so3xhh = 0, so3yhh = 0, so3zhh = 0, so3whh = 0,
                so4xhh = 1, so4yhh = 1, so4zhh = 1, so4whh = 1;
        if (xi > yi) {
            if (xi > zi) {
                // x > y,z
                if (xi > wi) {
                    // x > y,z,w
                    so1xhh = so2xhh = so3xhh = 1;
                    if (yi > zi) {
                        //  x > y > z & x > w
                        if (yi > wi) {
                            // x > y > z,w
                            so2yhh = so3yhh = 1;
                            if (zi > wi)
                                // x > y > z > w
                                so3zhh = 1;
                            else /* wi > zi */
                                // x > y > w > z
                                so3whh = 1;
                        } else /* wi > yi */ {
                            // x > w > y > z
                            so2whh = so3whh = 1;
                            so3yhh = 1;
                        }
                    } else /* zi > yi */ {
                        // x > z > y & x > w
                        if (yi > wi) {
                            // x > z > y > w
                            so2zhh = so3zhh = 1;
                            so3yhh = 1;
                        } else /* wi > yi */ {
                            // x > z,w > y
                            so3zhh = so3whh = 1;
                            if (zi > wi)
                                // x > z > w > y
                                so2zhh = 1;
                            else /* wi > zi */
                                // x > w > z > y
                                so2whh = 1;
                        }
                    }
                } else /* wi > xi */ {
                    // w > x > y,z
                    so1whh = so2whh = so3whh = 1;
                    so2xhh = so3xhh = 1;
                    if (yi > zi)
                        // w > x > y > z
                        so3yhh = 1;
                    else /* zi > yi */
                        // w > x > z > y
                        so3zhh = 1;
                }
            } else /* zi > xi */ {
                // z > x > y
                if (xi > wi) {
                    // z > x > y,w
                    so1zhh = so2zhh = so3zhh = 1;
                    so2xhh = so3xhh = 1;
                    if (yi > wi)
                        // z > x > y > w
                        so3yhh = 1;
                    else /* wi > yi */
                        // z > x > w > y
                        so3whh = 1;
                } else /* wi > xi */ {
                    // z,w > x > y
                    so2zhh = so2whh = 1;
                    so3xhh = so3zhh = so3whh = 1;
                    if (zi > wi)
                        // z > w > x > y
                        so1zhh = 1;
                    else /* wi > zi */
                        // w > z > x > y
                        so1whh = 1;
                }
            }
        } else /* yi > xi */ {
            if (xi > zi) {
                // y > x > z
                if (xi > wi) {
                    // y > x > z,w
                    so1yhh = so2yhh = so3yhh = 1;
                    so2xhh = so3xhh = 1;
                    if (zi > wi)
                        // y > x > z > w
                        so3zhh = 1;
                    else /* wi > zi */
                        // y > x > w > z
                        so3whh = 1;
                } else /* wi > xi */ {
                    // y,w > x > z
                    so2yhh = so2whh = 1;
                    so3xhh = so3yhh = so3whh = 1;
                    if (yi > wi)
                        // y > w > x > z
                        so1yhh = 1;
                    else /* wi > yi */
                        // w > y > x > z
                        so1whh = 1;
                }
            } else /* zi > xi */ {
                // y,z > x
                if (xi > wi) {
                    // y,z > x > w
                    so2yhh = so2zhh = 1;
                    so3xhh = so3yhh = so3zhh = 1;
                    if (yi > zi)
                        // y > z > x > w
                        so1yhh = 1;
                    else /* zi > yi */
                        // z > y > x > w
                        so1zhh = 1;
                } else /* wi > xi */ {
                    // y,z,w > x
                    if (yi > zi) {
                        // y > z > x & w > x
                        if (yi > wi) {
                            // y > z,w > x
                            so1yhh = so2yhh = so3yhh = 1;
                            so3zhh = so3whh = 1;
                            if (zi > wi)
                                // y > z > w > x
                                so2zhh = 1;
                            else /* wi > zi */
                                // y > w > z > x
                                so2whh = 1;
                        } else /* wi > yi */ {
                            // w > y > z > x
                            so1whh = so2whh = so3whh = 1;
                            so2yhh = so3yhh = 1;
                            so3zhh = 1;
                        }
                    } else /* zi > yi */ {
                        // z > y > x & w > x
                        if (yi > wi) {
                            // z > y > w > x
                            so1zhh = so2zhh = so3zhh = 1;
                            so2yhh = so3yhh = 1;
                            so3whh = 1;
                        } else /* wi > yi */ {
                            // z,w > y > x
                            so2zhh = so2whh = 1;
                            so3yhh = so3zhh = so3whh = 1;
                            if (zi > wi)
                                // z > w > y > x
                                so1zhh = 1;
                            else /* wi > zi */
                                // w > z > y > x
                                so1whh = 1;
                        }
                    }
                }
            }
        }

        // shift simplex to correct position
        so0xhh += xf;
        so0yhh += yf;
        so0zhh += zf;
        so0whh += wf;

        so1xhh += xf;
        so1yhh += yf;
        so1zhh += zf;
        so1whh += wf;

        so2xhh += xf;
        so2yhh += yf;
        so2zhh += zf;
        so2whh += wf;

        so3xhh += xf;
        so3yhh += yf;
        so3zhh += zf;
        so3whh += wf;

        so4xhh += xf;
        so4yhh += yf;
        so4zhh += zf;
        so4whh += wf;

        // get gradients
        DoubleGradient4D g0 = getDoubleGradient(so0xhh, so0yhh, so0zhh, so0whh),
                g1 = getDoubleGradient(so1xhh, so1yhh, so1zhh, so1whh),
                g2 = getDoubleGradient(so2xhh, so2yhh, so2zhh, so2whh),
                g3 = getDoubleGradient(so3xhh, so3yhh, so3zhh, so3whh),
                g4 = getDoubleGradient(so4xhh, so4yhh, so4zhh, so4whh);

        // convert simplex vertices back to normal coordinate basis
        double so0x, so0y, so0z, so0w;
        {
            double subtrahend = G * (so0xhh + so0yhh + so0zhh + so0whh);
            so0x = so0xhh - subtrahend;
            so0y = so0yhh - subtrahend;
            so0z = so0zhh - subtrahend;
            so0w = so0whh - subtrahend;
        }
        double so1x, so1y, so1z, so1w;
        {
            double subtrahend = G * (so1xhh + so1yhh + so1zhh + so1whh);
            so1x = so1xhh - subtrahend;
            so1y = so1yhh - subtrahend;
            so1z = so1zhh - subtrahend;
            so1w = so1whh - subtrahend;
        }
        double so2x, so2y, so2z, so2w;
        {
            double subtrahend = G * (so2xhh + so2yhh + so2zhh + so2whh);
            so2x = so2xhh - subtrahend;
            so2y = so2yhh - subtrahend;
            so2z = so2zhh - subtrahend;
            so2w = so2whh - subtrahend;
        }
        double so3x, so3y, so3z, so3w;
        {
            double subtrahend = G * (so3xhh + so3yhh + so3zhh + so3whh);
            so3x = so3xhh - subtrahend;
            so3y = so3yhh - subtrahend;
            so3z = so3zhh - subtrahend;
            so3w = so3whh - subtrahend;
        }
        double so4x, so4y, so4z, so4w;
        {
            double subtrahend = G * (so4xhh + so4yhh + so4zhh + so4whh);
            so4x = so4xhh - subtrahend;
            so4y = so4yhh - subtrahend;
            so4z = so4zhh - subtrahend;
            so4w = so4whh - subtrahend;
        }

        // compute displacement vectors
        double d0x = x - so0x, d0y = y - so0y, d0z = z - so0z, d0w = w - so0w,
                d1x = x - so1x, d1y = y - so1y, d1z = z - so1z, d1w = w - so1w,
                d2x = x - so2x, d2y = y - so2y, d2z = z - so2z, d2w = w - so2w,
                d3x = x - so3x, d3y = y - so3y, d3z = z - so3z, d3w = w - so3w,
                d4x = x - so4x, d4y = y - so4y, d4z = z - so4z, d4w = w - so4w;

        // compute noise contribution
        double n0 = computeNoiseContribution(d0x, d0y, d0z, d0w, g0),
                n1 = computeNoiseContribution(d1x, d1y, d1z, d1w, g1),
                n2 = computeNoiseContribution(d2x, d2y, d2z, d2w, g2),
                n3 = computeNoiseContribution(d3x, d3y, d3z, d3w, g3),
                n4 = computeNoiseContribution(d4x, d4y, d4z, d4w, g4);

        // return cumulative noise
        return (n0 + n1 + n2 + n3 + n4) / interpolationType.DOUBLE_4D_NOISE_BOUND;
    }

    private static class ElementTracker implements Iterable<Integer> {
        private static class Node {
            public final int val;
            private @Nullable Node prev, next;

            private Node(int val) {
                this.val = val;
            }
        }

        private @NotNull Node root;
        private final @NotNull Node[] NODES;
        public ElementTracker(int dims) {
            NODES = new Node[dims];
            root = NODES[0] = new Node(0);
            for (int i = 1; i < dims; i++) {
                Node curr = NODES[i] = new Node(i), prev = NODES[i - 1];

                prev.next = curr;
                curr.prev = prev;
            }
        }

        public void remove(int i) {
            Node curr = NODES[i], prev = curr.prev, next = curr.next;
            if (prev != null)
                prev.next = next;
            else
                //noinspection ConstantConditions
                root = next;
            if (next != null)
                next.prev = prev;
            //noinspection ConstantConditions
            NODES[i] = null;
        }

        @Override
        public @NotNull Iterator<Integer> iterator() {
            return new It();
        }

        private class It implements Iterator<Integer> {
            private @Nullable Node curr = root;

            @Override
            public boolean hasNext() {
                return curr != null;
            }
            @Override
            public Integer next() {
                //noinspection ConstantConditions
                int val = curr.val;
                curr = curr.next;
                return val;
            }
        }
    }

    @Override
    public float get(@NotNull float[] pos) {
        final int dim;
        final float F, G;
        {
            dim = pos.length;
            float sqrt = (float) Math.sqrt(dim + 1);
            F = (sqrt - 1) / dim;       // F = [√(N+1) - 1] / N
            G = (1 - 1 / sqrt) / dim;   // G = [1-1/√(N+1)] / N
        }

        // hypercubic honeycomb (https://en.wikipedia.org/wiki/Hypercubic_honeycomb)
        float[] phh = new float[dim];
        {
            float addend = 0.0f;
            for (float p : pos)
                addend += p;
            addend *= F;

            for (int i = 0; i < dim; i++)
                phh[i] = pos[i] + addend;
        }

        // xf = floor, xi = fractional part
        int[] pf = new int[dim];
        float[] pi = new float[dim];
        for (int i = 0; i < dim; i++) {
            pf[i] = floor(phh[i]);
            pi[i] = phh[i] - pf[i];
        }

        // Schläfli orthoscheme (https://en.wikipedia.org/wiki/Schl%C3%A4fli_orthoscheme)
        long[][] soXphh = new long[dim + 1][dim];
        {
            ElementTracker tracker = new ElementTracker(dim);
            for (int i = 1; i <= dim; i++) {
                float max = -100.0f; // actual pi values between 0 and 1
                int maxIndex = -1;

                for (int j : tracker)
                    if (max < pi[j]) {
                        max = pi[j];
                        maxIndex = j;
                    }
                tracker.remove(maxIndex);

                for (int j = i; j <= dim; j++)
                    soXphh[j][maxIndex] = 1;
            }

            // shift simplex to correct position
            for (int i = 0; i <= dim; i++)
                for (int d = 0; d < dim; d++)
                    soXphh[i][d] += pf[d];
        }

        // compute noise contributions
        float noise = 0.0f;
        SeedHasher.Interator it = getSeedHasher().getInterator(dim);
        float[] grad = new float[dim], soXp = new float[dim];
        for (int i = 0; i <= dim; i++) {
            // compute gradient
            computeGradient(dim, soXphh[i], grad, it);

            // convert simplex vertices back to normal coordinate basis
            {
                float subtrahend = 0.0f;
                for (long l : soXphh[i])
                    subtrahend += l;
                subtrahend *= G;

                for (int d = 0; d < dim; d++)
                    soXp[d] = soXphh[i][d] - subtrahend;
            }

            // compute displacement vector
            for (int d = 0; d < dim; d++)
                soXp[d] = pos[d] - soXp[d];

            // compute noise contribution
            noise += computeNoiseContribution(dim, soXp, grad);
        }

        // return cumulative noise
        return noise / SimplexNoiseBounds.getAsFloat(dim, interpolationType);
    }
    @Override
    public double get(@NotNull double[] pos) {
        final int dim;
        final double F, G;
        {
            dim = pos.length;
            double sqrt = Math.sqrt(dim + 1);
            F = (sqrt - 1) / dim;       // F = [√(N+1) - 1] / N
            G = (1 - 1 / sqrt) / dim;   // G = [1-1/√(N+1)] / N
        }

        // hypercubic honeycomb (https://en.wikipedia.org/wiki/Hypercubic_honeycomb)
        double[] phh = new double[dim];
        {
            double addend = 0.0;
            for (double p : pos)
                addend += p;
            addend *= F;

            for (int i = 0; i < dim; i++)
                phh[i] = pos[i] + addend;
        }

        // xf = floor, xi = fractional part
        long[] pf = new long[dim];
        double[] pi = new double[dim];
        for (int i = 0; i < dim; i++) {
            pf[i] = floor(phh[i]);
            pi[i] = phh[i] - pf[i];
        }

        // Schläfli orthoscheme (https://en.wikipedia.org/wiki/Schl%C3%A4fli_orthoscheme)
        long[][] soXphh = new long[dim + 1][dim];
        {
            ElementTracker tracker = new ElementTracker(dim);
            for (int i = 1; i <= dim; i++) {
                double max = -100.0; // actual pi values between 0 and 1
                int maxIndex = -1;

                for (int j : tracker)
                    if (max < pi[j]) {
                        max = pi[j];
                        maxIndex = j;
                    }
                tracker.remove(maxIndex);

                for (int j = i; j <= dim; j++)
                    soXphh[j][maxIndex] = 1;
            }

            // shift simplex to correct position
            for (int i = 0; i <= dim; i++)
                for (int d = 0; d < dim; d++)
                    soXphh[i][d] += pf[d];
        }

        // compute noise contributions
        double noise = 0.0;
        SeedHasher.Interator it = getSeedHasher().getInterator(dim);
        double[] grad = new double[dim], soXp = new double[dim];
        for (int i = 0; i <= dim; i++) {
            // compute gradient
            computeGradient(dim, soXphh[i], grad, it);

            // convert simplex vertices back to normal coordinate basis
            {
                double subtrahend = 0.0;
                for (long l : soXphh[i])
                    subtrahend += l;
                subtrahend *= G;

                for (int d = 0; d < dim; d++)
                    soXp[d] = soXphh[i][d] - subtrahend;
            }

            // compute displacement vector
            for (int d = 0; d < dim; d++)
                soXp[d] = pos[d] - soXp[d];

            // compute noise contribution
            noise += computeNoiseContribution(dim, soXp, grad);
        }

        // return cumulative noise
        return noise / SimplexNoiseBounds.getAsDouble(dim, interpolationType);
    }

    public static @NotNull SimplexNoise aperiodic() {
        return new SimplexNoise();
    }
    public static @NotNull SimplexNoise aperiodic(long seed) {
        return new SimplexNoise(seed);
    }

    public static @NotNull SimplexNoise periodic(long defaultPeriod, @NotNull long... periods) {
        return new SimplexNoise(defaultPeriod, periods);
    }
    public static @NotNull SimplexNoise periodic(long seed, long defaultPeriod, @NotNull long... periods) {
        return new SimplexNoise(seed, defaultPeriod, periods);
    }

    // computed on Mathematica/approximated
    public static final class SimplexNoiseBounds {
        private static final double
                CONTINUOUS_1D_BOUND = 0.013983312811550396,
                CONTINUOUS_2D_BOUND = 0.010080204702811454,
                CONTINUOUS_3D_BOUND = 0.009289062925455909,
                CONTINUOUS_4D_BOUND = 0.009210831906368878;

        private static final @NotNull Map<Integer, Double> CONTINUOUS_BOUNDS_MAP = new HashMap<>();
        static {
            CONTINUOUS_BOUNDS_MAP.put(1, CONTINUOUS_1D_BOUND);
            CONTINUOUS_BOUNDS_MAP.put(2, CONTINUOUS_2D_BOUND);
            CONTINUOUS_BOUNDS_MAP.put(3, CONTINUOUS_3D_BOUND);
            CONTINUOUS_BOUNDS_MAP.put(4, CONTINUOUS_4D_BOUND);
        }

        private static final double
                TRADITIONAL_1D_BOUND = 0.03599643079336411,
                TRADITIONAL_2D_BOUND = 0.028902867534156943,
                TRADITIONAL_3D_BOUND = 0.025077363499228966,
                TRADITIONAL_4D_BOUND = 0.02289733608959788;

        private static final @NotNull Map<Integer, Double> TRADITIONAL_BOUNDS_MAP = new HashMap<>();
        static {
            TRADITIONAL_BOUNDS_MAP.put(1, TRADITIONAL_1D_BOUND);
            TRADITIONAL_BOUNDS_MAP.put(2, TRADITIONAL_2D_BOUND);
            TRADITIONAL_BOUNDS_MAP.put(3, TRADITIONAL_3D_BOUND);
            TRADITIONAL_BOUNDS_MAP.put(4, TRADITIONAL_4D_BOUND);
        }

        public static float getAsFloat(int dim, @NotNull SimplexInterpolation interpolationType) {
            return (float) getAsDouble(dim, interpolationType);
        }
        public static double getAsDouble(int dim, @NotNull SimplexInterpolation interpolationType) {
            switch (interpolationType) {
                case CONTINUOUS:
                    return getOrCalculateBound(
                            dim,
                            CONTINUOUS_BOUNDS_MAP,
                            SimplexInterpolation.CONTINUOUS.DOUBLE_R_SQUARED
                    );

                case TRADITIONAL:
                    return getOrCalculateBound(
                            dim,
                            TRADITIONAL_BOUNDS_MAP,
                            SimplexInterpolation.TRADITIONAL.DOUBLE_R_SQUARED
                    );

                default:
                    return 0.0;
            }
        }

        @SuppressWarnings("UnnecessaryLocalVariable")
        private static synchronized double getOrCalculateBound(int dim, @NotNull Map<Integer, Double> boundsMap, final double R_SQUARED) {
            {
                Double stored = boundsMap.get(dim);
                if (stored != null)
                    return stored;
            }

            // dim >= 5 (1-4 are pre-calculated + cached)
            /*
             * Our (derivative approximation) equations:
             * eq1: (d/(d+1)) * x^2
             * eq2: (d/(d+1)) * (1-x)^2
             * eq3: 0.75 (d/(d+1)) + (d/(d+1)) * (0.5-x)^2
             *
             * For dim >= 5, 0.75 (d/(d+1)) = 0.75 - 0.75/(d+1) >= 0.75 - 0.75/6 = 0.625 > 0.6 (traditional) > 0.5 (continuous)
             * Therefore, we can discard eq3
             *
             * Eq1/2 split the interval x in [0,1] into 3 segments.... [0,1-a], [1-a,a], and [a,1]
             *
             * l1^2 = (d/(d+1)) * x^2     < R^2
             * l2^2 = (d/(d+1)) * (1-x)^2 < R^2
             *
             * Therefore, a = R * √(1 + 1/d)
             *
             * Now, we test:
             * 1) critical points of `eq1` over [0,1-a] & x = 0, 1-a
             * 2) critical points of `eq1 + eq2` over [1-a,a] & x = 1-a, a
             * 3) critical points of `eq2` over [a,1] & x = a, 1
             *
             * Custom Notation/Variables:
             * H = √(d/(d+1))
             */

            // Calculations:
            /*
             * Part 1: critical points of `eq1` over [0,1-a] & x = 0, 1-a
             * l1^2 = (d/(d+1)) * x^2 < R^2
             * So, n1 = (R^2 - l1^2)^4 * l1
             *
             * l1 = H * x
             * n1(x) = (R^2 - H^2 x^2)^4 * H * x
             * n1'(x) = (R^2 - H^2 x^2)^4 * H + 4 (R^2 - H^2 x^2)^3 * H * x * (-2 H^2 x)
             *        = H * (R^2 - H^2 x^2)^3 * [(R^2 - H^2 x^2) + 4 * x * (-2 H^2 x)]
             *        = H * (R^2 - H^2 x^2)^3 * [R^2 - 9 H^2 x^2]
             *
             * critical points: x = R / H = a, x = R / (3H) = a / 3
             * x = a is too big (and is also a minimum)
             * x = a/3 works (and is a maximum)
             * -----------------------------------------------------------------------------
             * Part 3: critical points of `eq2` over [a,1] & x = a, 1
             * This case is symmetric to part 1
             * x = 1-a is too small for the lower bound in [a,1] (and is also a minimum)
             * x = 1 - a/3 works (and is a maximum)
             *
             * Note:
             * n3(x) = n1(1 - x) --> n3'(x) = -n1'(1-x)
             *  --> `critical points of n3` = 1 - `critical points of n1`
             * -----------------------------------------------------------------------------
             * Part 2: critical points of `eq1 + eq2` over [1-a,a] & x = 1-a, a
             * n2(x) = n1(x) + n3(x) = n1(x) + n1(1-x)
             * n2'(x) = n1'(x) +
             */

            /*
             * SKIPPING TO ACTUAL CODE -- explanation/working out can be found in mathematica file
             */
            // constants
            final double H_SQUARED = dim / (1.0 + dim), H = Math.sqrt(H_SQUARED);
            double h = H, h2 = H_SQUARED, h3 = h * h2, h5 = h2 * h3, h7 = h2 * h5, h9 = h2 * h7;
            double r2 = R_SQUARED, r4 = r2 * r2, r6 = r2 * r4;

            // Derivative eq (cubic): ax^3 + bx^2 + cx + d
            double a = 72 * h9;
            double b = 126 * h9 - 168 * h7 * r2;
            double c = 63 * h9 / 2 - 140 * h7 * r2 + 120 * h5 * r4;
            double d = 9 * h9 / 8 - 21 * h7 * r2 / 2 + 30 * h5 * r4 - 24 * h3 * r6;

            CubicSolution<ComplexDouble> roots = CubicFormula.solveCubic(a, b, c, d);
            roots.root1.sqrt().round().real += 0.5;
            roots.root2.sqrt().round().real += 0.5;
            roots.root3.sqrt().round().real += 0.5;

            double max = applyNoiseMaxFunction(0.5, h, r2);
            if (roots.root1.imag == 0) max = Math.max(max, applyNoiseMaxFunction(roots.root1.real, h, r2));
            if (roots.root2.imag == 0) max = Math.max(max, applyNoiseMaxFunction(roots.root2.real, h, r2));
            if (roots.root3.imag == 0) max = Math.max(max, applyNoiseMaxFunction(roots.root3.real, h, r2));

            boundsMap.put(dim, max);
            return max;
        }
        private static double applyNoiseMaxFunction(double x, final double H, final double R2) {
            if (x <= 0 || x >= 1) return 0;
            double hx = H * x;
            return applyOneSideNoiseMaxFunction(hx, R2) + applyOneSideNoiseMaxFunction(H - hx, R2);
        }
        private static double applyOneSideNoiseMaxFunction(final double hx, final double R2) {
            final double hx2 = hx * hx;
            if (R2 <= hx2) return 0.0;
            double diff = R2 - hx2, diff2 = diff * diff, diff4 = diff2 * diff2;
            return hx * diff4;
        }
    }
}
