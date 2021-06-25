package io.github.utk003.util.math.solve;

import io.github.utk003.util.math.complex.ComplexDouble;
import io.github.utk003.util.math.complex.ComplexFloat;
import io.github.utk003.util.math.complex.ComplexNumber;
import org.jetbrains.annotations.NotNull;

public final class CubicFormula {
    public static class CubicSolution<CN extends ComplexNumber<CN>> {
        public final @NotNull CN root1, root2, root3;
        private CubicSolution(@NotNull CN cn1, @NotNull CN cn2, @NotNull CN cn3) {
            root1 = cn1;
            root2 = cn2;
            root3 = cn3;
        }

        @Override
        public @NotNull String toString() {
            return "{" + root1 + ", " + root2 + ", " + root3 + "}";
        }

        @Override
        public int hashCode() {
            return root1.hashCode() ^ root2.hashCode() ^ root3.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof CubicSolution))
                return false;

            CubicSolution<?> cs = (CubicSolution<?>) o;
            return root1.equals(cs.root1) && root2.equals(cs.root2) && root3.equals(cs.root3);
        }
    }

    private static @NotNull CubicSolution<ComplexFloat> newFloatSolution() {
        return new CubicSolution<>(new ComplexFloat(), new ComplexFloat(), new ComplexFloat());
    }
    private static @NotNull CubicSolution<ComplexDouble> newDoubleSolution() {
        return new CubicSolution<>(new ComplexDouble(), new ComplexDouble(), new ComplexDouble());
    }

    private static <CN extends ComplexNumber<CN>> void copyR1AndRotateRoots(@NotNull CubicSolution<CN> roots) {
        roots.root2.copy(roots.root1).ccw120();
        roots.root3.copy(roots.root1).cw120();
    }

    private static void processCubicEdgeCase(@NotNull ComplexFloat root, float scale) {
        if (scale == 0.0f) return;

        float real = root.real, imag = root.imag;
        root.invert().scale(scale);
        root.real += real;
        root.imag += imag;
        root.updateMembers();
    }
    private static void processCubicEdgeCase(@NotNull ComplexDouble root, double scale) {
        if (scale == 0.0) return;

        double real = root.real, imag = root.imag;
        root.invert().scale(scale);
        root.real += real;
        root.imag += imag;
        root.updateMembers();
    }

    private static @NotNull CubicSolution<ComplexFloat> solveInto(@NotNull CubicSolution<ComplexFloat> roots,
                                                                  float a, float d) {
        // ax^3 + d = 0 --> x^3 = -d/a
        roots.root1.set((float) Math.cbrt(-d / a), 0.0f);
        copyR1AndRotateRoots(roots);
        return roots;
    }
    private static @NotNull CubicSolution<ComplexFloat> solveInto(@NotNull CubicSolution<ComplexFloat> roots,
                                                                  float a, float c, float d) {
        // Cardano's formula for depressed cubics (https://en.wikipedia.org/wiki/Cubic_equation#Cardano's_formula)
        float p = c / a;
        float q = d / a;

        // x^3 + px + q = 0
        float discr = p * p * p / 27 + q * q / 4;
        if (discr > 0.0f) {
            // simplest case -- 2 roots are imaginary
            float sub = (float) Math.sqrt(discr),
                    real = (float) (Math.cbrt(-q / 2 + sub) + Math.cbrt(-q / 2 - sub));
            roots.root1.set(real, 0.0f);
            copyR1AndRotateRoots(roots);
        } else {
            // all 3 roots are real
            roots.root1.set(-q / 2, (float) Math.sqrt(-discr)).cbrt();
            copyR1AndRotateRoots(roots);

            float p3 = -p / 3;
            processCubicEdgeCase(roots.root1, p3);
            processCubicEdgeCase(roots.root2, p3);
            processCubicEdgeCase(roots.root3, p3);
        }
        return roots;
    }
    private static @NotNull CubicSolution<ComplexFloat> solveInto(@NotNull CubicSolution<ComplexFloat> roots,
                                                                  float a, float b, float c, float d) {
        // ax^3 + bx^2 + cx + d (https://en.wikipedia.org/wiki/Cubic_equation#General_cubic_formula)
        float bb = b * b, ac = a * c;
        float d0 = bb - 3 * ac;
        float d1 = 2 * bb * b - 9 * ac * b + 27 * a * a * d;

        float discr = d1 * d1 - 4 * d0 * d0 * d0;
        if (discr > 0.0f) {
            float real;
            if (d0 == 0.0f)
                real = d1;
            else
                real = (d1 + (float) Math.sqrt(discr)) / 2;
            roots.root1.set((float) Math.cbrt(real), 0.0f);
        } else
            roots.root1.set(d1 / 2, (float) Math.sqrt(-discr) / 2).cbrt();
        copyR1AndRotateRoots(roots);

        processCubicEdgeCase(roots.root1, d0);
        processCubicEdgeCase(roots.root2, d0);
        processCubicEdgeCase(roots.root3, d0);

        roots.root1.real += b;
        roots.root2.real += b;
        roots.root3.real += b;

        float scale = -1 / (3 * a);
        roots.root1.updateMembers().scale(scale);
        roots.root2.updateMembers().scale(scale);
        roots.root3.updateMembers().scale(scale);

        return roots;
    }

    private static @NotNull CubicSolution<ComplexDouble> solveInto(@NotNull CubicSolution<ComplexDouble> roots,
                                                                   double a, double d) {
        // ax^3 + d = 0 --> x^3 = -d/a
        roots.root1.set(Math.cbrt(-d / a), 0.0);
        copyR1AndRotateRoots(roots);
        return roots;
    }
    private static @NotNull CubicSolution<ComplexDouble> solveInto(@NotNull CubicSolution<ComplexDouble> roots,
                                                                   double a, double c, double d) {
        // Cardano's formula for depressed cubics (https://en.wikipedia.org/wiki/Cubic_equation#Cardano's_formula)
        double p = c / a;
        double q = d / a;

        // x^3 + px + q = 0
        double discr = p * p * p / 27 + q * q / 4;
        if (discr > 0.0) {
            // simplest case -- 2 roots are imaginary
            double sub = Math.sqrt(discr), real = Math.cbrt(-q / 2 + sub) + Math.cbrt(-q / 2 - sub);
            roots.root1.set(real, 0.0);
            copyR1AndRotateRoots(roots);
        } else {
            // all 3 roots are real
            roots.root1.set(-q / 2, Math.sqrt(-discr)).cbrt();
            copyR1AndRotateRoots(roots);

            double p3 = -p / 3;
            processCubicEdgeCase(roots.root1, p3);
            processCubicEdgeCase(roots.root2, p3);
            processCubicEdgeCase(roots.root3, p3);
        }
        return roots;
    }
    private static @NotNull CubicSolution<ComplexDouble> solveInto(@NotNull CubicSolution<ComplexDouble> roots,
                                                                   double a, double b, double c, double d) {
        // ax^3 + bx^2 + cx + d (https://en.wikipedia.org/wiki/Cubic_equation#General_cubic_formula)
        double bb = b * b, ac = a * c;
        double d0 = bb - 3 * ac;
        double d1 = 2 * bb * b - 9 * ac * b + 27 * a * a * d;

        double discr = d1 * d1 - 4 * d0 * d0 * d0;
        if (discr > 0.0) {
            double real;
            if (d0 == 0.0)
                real = d1;
            else
                real = (d1 + Math.sqrt(discr)) / 2;
            roots.root1.set(Math.cbrt(real), 0.0f);
        } else
            roots.root1.set(d1 / 2, Math.sqrt(-discr) / 2).cbrt();
        copyR1AndRotateRoots(roots);

        processCubicEdgeCase(roots.root1, d0);
        processCubicEdgeCase(roots.root2, d0);
        processCubicEdgeCase(roots.root3, d0);

        roots.root1.real += b;
        roots.root2.real += b;
        roots.root3.real += b;

        double scale = -1 / (3 * a);
        roots.root1.updateMembers().scale(scale);
        roots.root2.updateMembers().scale(scale);
        roots.root3.updateMembers().scale(scale);

        return roots;
    }

    private final @NotNull CubicSolution<ComplexFloat> FLOAT_ROOTS = newFloatSolution();
    private final @NotNull CubicSolution<ComplexDouble> DOUBLE_ROOTS = newDoubleSolution();

    public @NotNull CubicSolution<ComplexFloat> solve(float a, float d) {
        return solveInto(FLOAT_ROOTS, a, d);
    }
    public @NotNull CubicSolution<ComplexFloat> solve(float a, float c, float d) {
        return solveInto(FLOAT_ROOTS, a, c, d);
    }
    public @NotNull CubicSolution<ComplexFloat> solve(float a, float b, float c, float d) {
        return solveInto(FLOAT_ROOTS, a, b, c, d);
    }

    public @NotNull CubicSolution<ComplexDouble> solve(double a, double d) {
        return solveInto(DOUBLE_ROOTS, a, d);
    }
    public @NotNull CubicSolution<ComplexDouble> solve(double a, double c, double d) {
        return solveInto(DOUBLE_ROOTS, a, c, d);
    }
    public @NotNull CubicSolution<ComplexDouble> solve(double a, double b, double c, double d) {
        return solveInto(DOUBLE_ROOTS, a, b, c, d);
    }

    public static @NotNull CubicSolution<ComplexFloat> solveCubic(float a, float d) {
        return solveInto(newFloatSolution(), a, d);
    }
    public static @NotNull CubicSolution<ComplexFloat> solveCubic(float a, float c, float d) {
        return solveInto(newFloatSolution(), a, c, d);
    }
    public static @NotNull CubicSolution<ComplexFloat> solveCubic(float a, float b, float c, float d) {
        return solveInto(newFloatSolution(), a, b, c, d);
    }

    public static @NotNull CubicSolution<ComplexDouble> solveCubic(double a, double d) {
        return solveInto(newDoubleSolution(), a, d);
    }
    public static @NotNull CubicSolution<ComplexDouble> solveCubic(double a, double c, double d) {
        return solveInto(newDoubleSolution(), a, c, d);
    }
    public static @NotNull CubicSolution<ComplexDouble> solveCubic(double a, double b, double c, double d) {
        return solveInto(newDoubleSolution(), a, b, c, d);
    }
}
