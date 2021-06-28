package io.github.utk003.util.math.solve;

import io.github.utk003.util.math.complex.ComplexDouble;
import io.github.utk003.util.math.complex.ComplexFloat;
import io.github.utk003.util.math.complex.ComplexNumber;
import org.jetbrains.annotations.NotNull;

public final class QuadraticFormula {
    public static class QuadraticSolution<CN extends ComplexNumber<CN>> {
        public final @NotNull CN root1, root2;
        private QuadraticSolution(@NotNull CN cn1, @NotNull CN cn2) {
            root1 = cn1;
            root2 = cn2;
        }

        @Override
        public @NotNull String toString() {
            return "{" + root1 + ", " + root2 + "}";
        }

        @Override
        public int hashCode() {
            return root1.hashCode() ^ root2.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof QuadraticSolution))
                return false;

            QuadraticSolution<?> qs = (QuadraticSolution<?>) o;
            return root1.equals(qs.root1) && root2.equals(qs.root2);
        }
    }

    private static @NotNull QuadraticSolution<ComplexFloat> newFloatSolution() {
        return new QuadraticSolution<>(new ComplexFloat(), new ComplexFloat());
    }
    private static @NotNull QuadraticSolution<ComplexDouble> newDoubleSolution() {
        return new QuadraticSolution<>(new ComplexDouble(), new ComplexDouble());
    }

    private static @NotNull QuadraticSolution<ComplexDouble> solveInto(@NotNull QuadraticSolution<ComplexDouble> roots,
                                                                       double a, double c) {
        // ax^2 + c = 0 --> x^2 = -c/a --> x = ±√(-c/a)
        double sqrd = -c / a;
        if (sqrd < 0.0) {
            // imaginary solutions
            double imag = Math.sqrt(-sqrd);

            roots.root1.set(0.0, imag);
            roots.root2.set(0.0, -imag);
        } else {
            // real solutions
            double real = Math.sqrt(sqrd);

            roots.root1.set(real, 0.0);
            roots.root2.set(-real, 0.0);
        }
        return roots;
    }
    private static @NotNull QuadraticSolution<ComplexDouble> solveInto(@NotNull QuadraticSolution<ComplexDouble> roots,
                                                                       double a, double b, double c) {
        // ax^2 + bx + c = 0--> x = [-b ± √(b^2 - 4ac)] / (2a)
        double discr = b * b - 4 * a * c; // discriminant
        double a2 = 2 * a, real = -b / a2;
        if (discr < 0.0) {
            // imaginary solution
            double imag = Math.sqrt(-discr) / a2;

            roots.root1.set(real, imag);
            roots.root2.set(real, -imag);
        } else {
            // real solutions
            double real2 = Math.sqrt(discr) / a2;

            roots.root1.set(real + real2, 0.0);
            roots.root2.set(real - real2, 0.0);
        }
        return roots;
    }

    private final @NotNull QuadraticSolution<ComplexFloat> FLOAT_ROOTS = newFloatSolution();
    private final @NotNull QuadraticSolution<ComplexDouble> DOUBLE_ROOTS = newDoubleSolution();

    private static @NotNull QuadraticSolution<ComplexFloat> copy(@NotNull QuadraticSolution<ComplexDouble> from,
                                                                 @NotNull QuadraticSolution<ComplexFloat> into) {
        into.root1.set((float) from.root1.real, (float) from.root1.imag);
        into.root2.set((float) from.root2.real, (float) from.root2.imag);
        return into;
    }

    public @NotNull QuadraticSolution<ComplexFloat> solve(float a, float c) {
        solveInto(DOUBLE_ROOTS, a, c);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }
    public @NotNull QuadraticSolution<ComplexFloat> solve(float a, float b, float c) {
        solveInto(DOUBLE_ROOTS, a, b, c);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }
    public @NotNull QuadraticSolution<ComplexDouble> solve(double a, double c) {
        return solveInto(DOUBLE_ROOTS, a, c);
    }
    public @NotNull QuadraticSolution<ComplexDouble> solve(double a, double b, double c) {
        return solveInto(DOUBLE_ROOTS, a, b, c);
    }

    public static @NotNull QuadraticSolution<ComplexFloat> solveQuadratic(float a, float c) {
        QuadraticSolution<ComplexDouble> roots = solveInto(newDoubleSolution(), a, c);
        return copy(roots, newFloatSolution());
    }
    public static @NotNull QuadraticSolution<ComplexFloat> solveQuadratic(float a, float b, float c) {
        QuadraticSolution<ComplexDouble> roots = solveInto(newDoubleSolution(), a, b, c);
        return copy(roots, newFloatSolution());
    }

    public static @NotNull QuadraticSolution<ComplexDouble> solveQuadratic(double a, double c) {
        return solveInto(newDoubleSolution(), a, c);
    }
    public static @NotNull QuadraticSolution<ComplexDouble> solveQuadratic(double a, double b, double c) {
        return solveInto(newDoubleSolution(), a, b, c);
    }
}
