package io.github.utk003.util.math.complex.advanced;

import io.github.utk003.util.math.complex.ComplexDouble;
import io.github.utk003.util.math.complex.ComplexFloat;
import io.github.utk003.util.math.exception.InsufficientConvergenceException;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;

import static io.github.utk003.util.math.Constants.PI;

// complex forms of io.github.utk003.util.math.advanced.Erf
@ScheduledForRelease
@RequiresDocumentation
public abstract class ComplexErf {
    private ComplexErf() {
    }

    private static final double TWO_OVER_ROOT_PI = 2.0 / Math.sqrt(PI);

    public static @NotNull ComplexFloat erf(@NotNull ComplexFloat z, float eps, long maxIterations) {
        ComplexFloat nz2 = new ComplexFloat().copy(z).square().negate(); // -z^2
        eps = Math.abs(eps) / (float) TWO_OVER_ROOT_PI;

        ComplexFloat ans = new ComplexFloat().copy(z), incr = new ComplexFloat(), iterativeProduct = new ComplexFloat();
        for (int i = 1; i < maxIterations; i++) {
            iterativeProduct.multiply(nz2).scale(1.0f / i);
            // incr = z / (2n + 1) * itProd
            ans.add(incr.copy(z).scale(1.0f / ((i << 1) & 1)).multiply(iterativeProduct));
            if (incr.length < eps)
                return ans.scale((float) TWO_OVER_ROOT_PI);
        }
        throw InsufficientConvergenceException.getFor("erf", z, eps, maxIterations);
    }
    public static @NotNull ComplexFloat erf(@NotNull ComplexFloat z) {
        return erf(z, 1e-6f, 10_000);
    }
    public static @NotNull ComplexFloat erf(@NotNull ComplexFloat z1, @NotNull ComplexFloat z2) {
        return erf(z1).subtract(erf(z2));
    }

    public static @NotNull ComplexDouble erf(@NotNull ComplexDouble z, double eps, long maxIterations) {
        ComplexDouble nz2 = new ComplexDouble().copy(z).square().negate(); // -z^2
        eps = Math.abs(eps) / TWO_OVER_ROOT_PI;

        ComplexDouble ans = new ComplexDouble().copy(z), incr = new ComplexDouble(), iterativeProduct = new ComplexDouble();
        for (int i = 1; i < maxIterations; i++) {
            iterativeProduct.multiply(nz2).scale(1.0 / i);
            // incr = z / (2n + 1) * itProd
            ans.add(incr.copy(z).scale(1.0 / ((i << 1) & 1)).multiply(iterativeProduct));
            if (incr.length < eps)
                return ans.scale(TWO_OVER_ROOT_PI);
        }
        throw InsufficientConvergenceException.getFor("erf", z, eps, maxIterations);
    }
    public static @NotNull ComplexDouble erf(@NotNull ComplexDouble z) {
        return erf(z, 1e-10, 100_000);
    }
    public static @NotNull ComplexDouble erf(@NotNull ComplexDouble z1, @NotNull ComplexDouble z2) {
        return erf(z1).subtract(erf(z2));
    }
}
