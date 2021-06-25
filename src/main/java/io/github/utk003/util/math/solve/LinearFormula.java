package io.github.utk003.util.math.solve;

import io.github.utk003.util.math.complex.ComplexDouble;
import io.github.utk003.util.math.complex.ComplexFloat;
import io.github.utk003.util.math.complex.ComplexNumber;
import org.jetbrains.annotations.NotNull;

public final class LinearFormula {
    private static float solveInto(float a, float b) {
        // ax + b = 0 --> x = -b / a
        return -b / a;
    }
    private static double solveInto(double a, double b) {
        // ax + b = 0 --> x = -b / a
        return -b / a;
    }

    public static <CN extends ComplexNumber<CN>> @NotNull CN solveInto(@NotNull CN into, @NotNull CN a, @NotNull CN b) {
        return into.copy(b).negate().divide(a);
    }

    private final @NotNull ComplexFloat FLOAT_SOLUTION = new ComplexFloat();
    private final @NotNull ComplexDouble DOUBLE_SOLUTION = new ComplexDouble();

    public float solve(float a, float b) {
        return solveInto(a, b);
    }
    public double solve(double a, double b) {
        return solveInto(a, b);
    }
    public @NotNull ComplexFloat solve(@NotNull ComplexFloat a, @NotNull ComplexFloat b) {
        return solveInto(FLOAT_SOLUTION, a, b);
    }
    public @NotNull ComplexDouble solve(@NotNull ComplexDouble a, @NotNull ComplexDouble b) {
        return solveInto(DOUBLE_SOLUTION, a, b);
    }

    public static float solveLinear(float a, float b) {
        return solveInto(a, b);
    }
    public static double solveLinear(double a, double b) {
        return solveInto(a, b);
    }
    public static @NotNull ComplexFloat solveLinear(@NotNull ComplexFloat a, @NotNull ComplexFloat b) {
        return solveInto(new ComplexFloat(), a, b);
    }
    public static @NotNull ComplexDouble solveLinear(@NotNull ComplexDouble a, @NotNull ComplexDouble b) {
        return solveInto(new ComplexDouble(), a, b);
    }
}
