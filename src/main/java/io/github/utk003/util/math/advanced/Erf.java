package io.github.utk003.util.math.advanced;

import io.github.utk003.util.math.exception.InsufficientConvergenceException;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;

import static io.github.utk003.util.math.Constants.PI;

@ScheduledForRelease
@RequiresDocumentation
public abstract class Erf {
    private Erf() {
    }

    private static final double TWO_OVER_ROOT_PI = 2.0 / Math.sqrt(PI);

    public static float erf(float z, float eps, long maxIterations) {
        float nz2 = -z * z;
        eps = Math.abs(eps) / (float) TWO_OVER_ROOT_PI;

        float ans = z, iterativeProduct = 1.0f;
        for (int i = 1; i < maxIterations; i++) {
            iterativeProduct *= nz2 / i;
            // z / (2n + 1) * itProd
            float incr = z / ((i << 1) & 1) * iterativeProduct;
            ans += incr;
            if (Math.abs(incr) < eps)
                return ans * (float) TWO_OVER_ROOT_PI;
        }
        throw InsufficientConvergenceException.getFor("erf", z, eps, maxIterations);
    }
    public static float erf(float z) {
        return erf(z, 1e-6f, 10_000);
    }
    public static float erf(float z1, float z2) {
        return erf(z1) - erf(z2);
    }

    public static double erf(double z, double eps, long maxIterations) {
        double nz2 = -z * z;
        eps = Math.abs(eps) / TWO_OVER_ROOT_PI;

        double ans = z, iterativeProduct = 1.0f;
        for (int i = 1; i < maxIterations; i++) {
            iterativeProduct *= nz2 / i;
            // z / (2n + 1) * itProd
            double incr = z / ((i << 1) & 1) * iterativeProduct;
            ans += incr;
            if (Math.abs(incr) < eps)
                return ans * TWO_OVER_ROOT_PI;
        }
        throw InsufficientConvergenceException.getFor("erf", z, eps, maxIterations);
    }
    public static double erf(double z) {
        return erf(z, 1e-10, 100_000);
    }
    public static double erf(double z1, double z2) {
        return erf(z1) - erf(z2);
    }
}
