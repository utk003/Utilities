package io.github.utk003.util.math.noise;

import io.github.utk003.util.math.noise.AbstractNoise.SeedHasher.Interator;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;

@ScheduledForRelease(inVersion = "v3.0.0")
@RequiresDocumentation
public abstract class GradientNoise extends AbstractNoise implements Noise {
    public GradientNoise() {
        super();
    }
    public GradientNoise(long seed) {
        super(seed);
    }
    public GradientNoise(long defaultPeriod, @NotNull long[] periods) {
        super(defaultPeriod, periods);
    }
    public GradientNoise(long seed, long defaultPeriod, @NotNull long[] periods) {
        super(seed, defaultPeriod, periods);
    }

    protected static final class FloatGradient2D {
        public final float dx, dy;
        private FloatGradient2D(float dx, float dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    protected static final class DoubleGradient2D {
        public final double dx, dy;
        private DoubleGradient2D(double dx, double dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    private static final int LOG_GRADIENT_2D_ARRAY_SIZE = 8, GRADIENT_2D_ARRAY_SIZE = 1 << LOG_GRADIENT_2D_ARRAY_SIZE;
    private static final @NotNull FloatGradient2D[] FLOAT_GRADIENTS_2D = new FloatGradient2D[GRADIENT_2D_ARRAY_SIZE];
    private static final @NotNull DoubleGradient2D[] DOUBLE_GRADIENTS_2D = new DoubleGradient2D[GRADIENT_2D_ARRAY_SIZE];
    static {
        for (int i = 0; i < 256; i++) {
            double t = Math.PI / 256, a1 = t * (2 * i + 1);
            double x = Math.cos(a1), y = Math.sin(a1);

            FLOAT_GRADIENTS_2D[i] = new FloatGradient2D((float) x, (float) y);
            DOUBLE_GRADIENTS_2D[i] = new DoubleGradient2D(x, y);
        }
    }
    private static final int GRADIENT_2D_HASH_SHIFT = 64 - LOG_GRADIENT_2D_ARRAY_SIZE;
    private int computeGradientIndex(long i1, long i2) {
        long key = computeHashKey(i1, i2);
        return hashComputedKey(key, GRADIENT_2D_HASH_SHIFT, GRADIENT_2D_ARRAY_SIZE);
    }
    protected @NotNull FloatGradient2D getFloatGradient(int i1, int i2) {
        return FLOAT_GRADIENTS_2D[computeGradientIndex(i1, i2)];
    }
    protected @NotNull DoubleGradient2D getDoubleGradient(long i1, long i2) {
        return DOUBLE_GRADIENTS_2D[computeGradientIndex(i1, i2)];
    }

    protected static final class FloatGradient3D {
        public final float dx, dy, dz;
        private FloatGradient3D(float dx, float dy, float dz) {
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
        }
    }

    protected static final class DoubleGradient3D {
        public final double dx, dy, dz;
        private DoubleGradient3D(double dx, double dy, double dz) {
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
        }
    }

    private static final int LOG_GRADIENT_3D_ARRAY_SIZE = 11, GRADIENT_3D_ARRAY_SIZE = 1 << LOG_GRADIENT_3D_ARRAY_SIZE;
    private static final @NotNull FloatGradient3D[] FLOAT_GRADIENTS_3D = new FloatGradient3D[GRADIENT_3D_ARRAY_SIZE];
    private static final @NotNull DoubleGradient3D[] DOUBLE_GRADIENTS_3D = new DoubleGradient3D[GRADIENT_3D_ARRAY_SIZE];
    static {
        int ind = 0;
        // i bounds angle to between 0 and pi radians
        for (int i = 0; i < 32; i++)
            // j cycles 0 to 2pi radians
            for (int j = 0; j < 64; j++) {
                double t = Math.PI / 64, a1 = t * (2 * i + 1), a2 = t * (2 * j + 1);
                double s2 = Math.sin(a2), x = Math.cos(a1) * s2, y = Math.sin(a1) * s2, z = Math.cos(a2);

                FLOAT_GRADIENTS_3D[ind] = new FloatGradient3D((float) x, (float) y, (float) z);
                DOUBLE_GRADIENTS_3D[ind] = new DoubleGradient3D(x, y, z);

                ind++;
            }
    }
    private static final int GRADIENT_3D_HASH_SHIFT = 64 - LOG_GRADIENT_3D_ARRAY_SIZE;
    private int computeGradientIndex(long i1, long i2, long i3) {
        long key = computeHashKey(i1, i2, i3);
        return hashComputedKey(key, GRADIENT_3D_HASH_SHIFT, GRADIENT_3D_ARRAY_SIZE);
    }
    protected @NotNull FloatGradient3D getFloatGradient(int i1, int i2, int i3) {
        return FLOAT_GRADIENTS_3D[computeGradientIndex(i1, i2, i3)];
    }
    protected @NotNull DoubleGradient3D getDoubleGradient(long i1, long i2, long i3) {
        return DOUBLE_GRADIENTS_3D[computeGradientIndex(i1, i2, i3)];
    }

    protected static final class FloatGradient4D {
        public final float dx, dy, dz, dw;
        private FloatGradient4D(float dx, float dy, float dz, float dw) {
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
            this.dw = dw;
        }
    }

    protected static final class DoubleGradient4D {
        public final double dx, dy, dz, dw;
        private DoubleGradient4D(double dx, double dy, double dz, double dw) {
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
            this.dw = dw;
        }
    }

    private static final int LOG_GRADIENT_4D_ARRAY_SIZE = 16, GRADIENT_4D_ARRAY_SIZE = 1 << LOG_GRADIENT_4D_ARRAY_SIZE;
    private static final @NotNull FloatGradient4D[] FLOAT_GRADIENTS_4D = new FloatGradient4D[GRADIENT_4D_ARRAY_SIZE];
    private static final @NotNull DoubleGradient4D[] DOUBLE_GRADIENTS_4D = new DoubleGradient4D[GRADIENT_4D_ARRAY_SIZE];
    static {
        int ind = 0;
        // i, j bound angles to between 0 and pi radians
        for (int i = 0; i < 32; i++)
            for (int j = 0; j < 32; j++)
                // k cycles 0 to 2pi radians
                for (int k = 0; k < 64; k++) {
                    double t = Math.PI / 64, a1 = t * (2 * i + 1), a2 = t * (2 * j + 1), a3 = t * (2 * k + 1);
                    double s3 = Math.sin(a3), c3 = Math.cos(a3);
                    double x = Math.cos(a1) * c3, y = Math.sin(a1) * c3, z = Math.cos(a2) * s3, w = Math.sin(a2) * s3;

                    FLOAT_GRADIENTS_4D[ind] = new FloatGradient4D((float) x, (float) y, (float) z, (float) w);
                    DOUBLE_GRADIENTS_4D[ind] = new DoubleGradient4D(x, y, z, w);

                    ind++;
                }
    }
    private static final int GRADIENT_4D_HASH_SHIFT = 64 - LOG_GRADIENT_4D_ARRAY_SIZE;
    private int computeGradientIndex(long i1, long i2, long i3, long i4) {
        long key = computeHashKey(i1, i2, i3, i4);
        return hashComputedKey(key, GRADIENT_4D_HASH_SHIFT, GRADIENT_4D_ARRAY_SIZE);
    }
    protected @NotNull FloatGradient4D getFloatGradient(int i1, int i2, int i3, int i4) {
        return FLOAT_GRADIENTS_4D[computeGradientIndex(i1, i2, i3, i4)];
    }
    protected @NotNull DoubleGradient4D getDoubleGradient(long i1, long i2, long i3, long i4) {
        return DOUBLE_GRADIENTS_4D[computeGradientIndex(i1, i2, i3, i4)];
    }

    protected void computeGradient(int dim, @NotNull long[] indices, @NotNull float[] grad, @NotNull Interator it) {
        // hash corners to get indices/hash keys
        computeHashKeys(indices);

        // compute random gradient's angles + gradient elements
        grad[0] = 1.0f;
        it.reset();

        for (int j = 1; j < dim; j++) {
            // get random angle
            float a1 = it.next(), a2 = it.next();
            for (long cornerHash : indices) {
                a1 += it.next() * cornerHash;
                a2 += it.next() * cornerHash;
            }
            float randAngle = (float) (it.next() * Math.sin(a1) * Math.cos(a2));

            // multiply through by sines/cosines
            float sin = (float) Math.sin(randAngle);
            for (int k = 0; k < j; k++)
                grad[k] *= sin;
            grad[j] = (float) Math.cos(randAngle);
        }
    }
    protected void computeGradient(int dim, @NotNull long[] indices, @NotNull double[] grad, @NotNull Interator it) {
        // hash corners for indices
        computeHashKeys(indices);

        // compute random gradient's angles + gradient elements
        grad[0] = 1.0;
        it.reset();

        for (int j = 1; j < dim; j++) {
            // get random angle
            double a1 = it.next(), a2 = it.next();
            for (long cornerHash : indices) {
                a1 += it.next() * cornerHash;
                a2 += it.next() * cornerHash;
            }
            double randAngle = it.next() * Math.sin(a1) * Math.cos(a2);

            // multiply through by sines/cosines
            double sin = Math.sin(randAngle);
            for (int k = 0; k < j; k++)
                grad[k] *= sin;
            grad[j] = Math.cos(randAngle);
        }
    }
}
