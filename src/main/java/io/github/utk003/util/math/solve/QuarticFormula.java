package io.github.utk003.util.math.solve;

import io.github.utk003.util.math.complex.ComplexDouble;
import io.github.utk003.util.math.complex.ComplexFloat;
import io.github.utk003.util.math.complex.ComplexNumber;
import org.jetbrains.annotations.NotNull;

public final class QuarticFormula {
    public static class QuarticSolution<CN extends ComplexNumber<CN>> {
        public final @NotNull CN root1, root2, root3, root4;
        private QuarticSolution(@NotNull CN cn1, @NotNull CN cn2, @NotNull CN cn3, @NotNull CN cn4) {
            root1 = cn1;
            root2 = cn2;
            root3 = cn3;
            root4 = cn4;
        }

        @Override
        public @NotNull String toString() {
            return "{" + root1 + ", " + root2 + ", " + root3 + ", " + root4 + "}";
        }

        @Override
        public int hashCode() {
            return root1.hashCode() ^ root2.hashCode() ^ root3.hashCode() ^ root4.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof QuarticSolution))
                return false;

            QuarticSolution<?> qs = (QuarticSolution<?>) o;
            return root1.equals(qs.root1) && root2.equals(qs.root2) && root3.equals(qs.root3) && root4.equals(qs.root4);
        }
    }

    private static @NotNull QuarticSolution<ComplexFloat> newFloatSolution() {
        return new QuarticSolution<>(new ComplexFloat(), new ComplexFloat(), new ComplexFloat(), new ComplexFloat());
    }
    private static @NotNull QuarticSolution<ComplexDouble> newDoubleSolution() {
        return new QuarticSolution<>(new ComplexDouble(), new ComplexDouble(), new ComplexDouble(), new ComplexDouble());
    }

    private static <CN extends ComplexNumber<CN>> void copyR1AndRotateFloatRoots(@NotNull QuarticSolution<CN> roots) {
        roots.root2.copy(roots.root1).ccw90();  // rot90
        roots.root3.copy(roots.root1).rot180(); // rot180
        roots.root4.copy(roots.root1).cw90();   // rot270
    }

    private static void calculateGeneralSolutionS(@NotNull ComplexFloat cf, float d0, float a, float p) {
        if (d0 != 0.0f) {
            float real = cf.real, imag = cf.imag;
            cf.invert().scale(d0);
            cf.real += real;
            cf.imag += imag;
            cf.updateMembers();
        }

        cf.scale(1 / (3 * a));
        cf.real -= (2 * p) / 3;
        cf.updateMembers().sqrt().scale(0.5f);
    }
    private static void calculateGeneralSolutionS(@NotNull ComplexDouble cd, double d0, double a, double p) {
        if (d0 != 0.0) {
            double real = cd.real, imag = cd.imag;
            cd.invert().scale(d0);
            cd.real += real;
            cd.imag += imag;
            cd.updateMembers();
        }

        cd.scale(1 / (3 * a));
        cd.real -= (2 * p) / 3;
        cd.updateMembers().sqrt().scale(0.5);
    }

    private static void findGeneralSolutionRootPair(@NotNull ComplexFloat cf1, @NotNull ComplexFloat cf2,
                                                    float a, float b, float p, float q) {
        float real = cf2.real + b / (4 * a), imag = cf2.imag;
        cf2.square().scale(4);

        cf1.invert().scale(q);
        cf1.real -= 2 * p;
        cf1.updateMembers().subtract(cf2);

        cf1.sqrt().scale(0.5f);
        cf2.copy(cf1).negate();

        cf1.real -= real;
        cf1.imag -= imag;
        cf1.updateMembers();

        cf2.real -= real;
        cf2.imag -= imag;
        cf2.updateMembers();
    }
    private static void findGeneralSolutionRootPair(@NotNull ComplexDouble cd1, @NotNull ComplexDouble cd2,
                                                    double a, double b, double p, double q) {
        double real = cd2.real + b / (4 * a), imag = cd2.imag;
        cd2.square().scale(4);

        cd1.invert().scale(q);
        cd1.real -= 2 * p;
        cd1.updateMembers().subtract(cd2);

        cd1.sqrt().scale(0.5);
        cd2.copy(cd1).negate();

        cd1.real -= real;
        cd1.imag -= imag;
        cd1.updateMembers();

        cd2.real -= real;
        cd2.imag -= imag;
        cd2.updateMembers();
    }

    private static @NotNull QuarticSolution<ComplexFloat> solveInto(@NotNull QuarticSolution<ComplexFloat> roots,
                                                                    float a, float e) {
        // ax^4 + e = 0 --> x^4 = -e/a
        roots.root1.real = -e / a;
        roots.root1.imag = 0.0f;
        roots.root1.updateMembers().sqrt().sqrt(); // 1/4 power = 4th root
        copyR1AndRotateFloatRoots(roots);
        return roots;
    }
    private static @NotNull QuarticSolution<ComplexFloat> solveInto(@NotNull QuarticSolution<ComplexFloat> roots,
                                                                    float a, float b, float c, float d, float e) {
        // General solution: https://en.wikipedia.org/wiki/Quartic_function#General_formula_for_roots
        // ax^4 + bx^3 + cx^2 + dx + e
        float aa = a * a, bb = b * b, cc = c * c, dd = d * d, ac = a * c, ae = a * e, bd = b * d;

        float d0 = cc - 3 * bd + 12 * ae;
        float d1 = 2 * c * cc - 9 * c * bd + 27 * bb * e + 27 * a * dd - 72 * ae * c;
        float discr = d1 * d1 - 4 * d0 * d0 * d0;

        float p = (8 * ac - 3 * bb) / (8 * aa);
        float q = (b * bb - 4 * ac * b + 8 * aa * d) / (8 * a * aa);

        // find first Q
        float real, imag;
        if (discr > 0.0f) {
            real = (float) Math.cbrt(d0 != 0.0f ? (d1 + (float) Math.sqrt(discr)) / 2 : d1);
            imag = 0.0f;
        } else {
            real = d1 / 2;
            imag = (float) Math.sqrt(-discr) / 2;
        }

        // rotate Q to other 2 possibilities
        roots.root1.set(real, imag);
        roots.root2.copy(roots.root1).ccw120();
        roots.root3.copy(roots.root1).cw120();

        // get all 3 Ss
        calculateGeneralSolutionS(roots.root1, d0, a, p);
        calculateGeneralSolutionS(roots.root2, d0, a, p);
        calculateGeneralSolutionS(roots.root3, d0, a, p);

        // pick right S
        ComplexFloat s;
        if (roots.root1.lengthSquared != 0.0f)
            s = roots.root1;
        else if (roots.root2.lengthSquared != 0.0f)
            s = roots.root2;
        else
            s = roots.root3; // if all 3 S's are 0, we have a special case --> we can accept S = 0

        // save S
        roots.root1.copy(s);
        roots.root2.copy(s);
        s.negate();
        roots.root3.copy(s);
        roots.root4.copy(s);

        // get roots
        findGeneralSolutionRootPair(roots.root1, roots.root2, a, b, p, q);
        findGeneralSolutionRootPair(roots.root3, roots.root4, a, b, p, q);

        // return roots
        return roots;
    }

    private static @NotNull QuarticSolution<ComplexDouble> solveInto(@NotNull QuarticSolution<ComplexDouble> roots,
                                                                     double a, double e) {
        // ax^4 + e = 0 --> x^4 = -e/a
        roots.root1.real = -e / a;
        roots.root1.imag = 0.0;
        roots.root1.updateMembers().sqrt().sqrt(); // 1/4 power = 4th root
        copyR1AndRotateFloatRoots(roots);
        return roots;
    }
    private static @NotNull QuarticSolution<ComplexDouble> solveInto(@NotNull QuarticSolution<ComplexDouble> roots,
                                                                     double a, double b, double c, double d, double e) {
        // General solution: https://en.wikipedia.org/wiki/Quartic_function#General_formula_for_roots
        // ax^4 + bx^3 + cx^2 + dx + e
        double aa = a * a, bb = b * b, cc = c * c, dd = d * d, ac = a * c, ae = a * e, bd = b * d;

        double d0 = cc - 3 * bd + 12 * ae;
        double d1 = 2 * c * cc - 9 * c * bd + 27 * bb * e + 27 * a * dd - 72 * ae * c;
        double discr = d1 * d1 - 4 * d0 * d0 * d0;

        double p = (8 * ac - 3 * bb) / (8 * aa);
        double q = (b * bb - 4 * ac * b + 8 * aa * d) / (8 * a * aa);

        // find first Q
        double real, imag;
        if (discr > 0.0) {
            real = Math.cbrt(d0 != 0.0 ? (d1 + Math.sqrt(discr)) / 2 : d1);
            imag = 0.0;
        } else {
            real = d1 / 2;
            imag = Math.sqrt(-discr) / 2;
        }

        // rotate Q to other 2 possibilities
        roots.root1.set(real, imag);
        roots.root2.copy(roots.root1).ccw120();
        roots.root3.copy(roots.root1).cw120();

        // get all 3 Ss
        calculateGeneralSolutionS(roots.root1, d0, a, p);
        calculateGeneralSolutionS(roots.root2, d0, a, p);
        calculateGeneralSolutionS(roots.root3, d0, a, p);

        // pick right S
        ComplexDouble s;
        if (roots.root1.lengthSquared != 0.0)
            s = roots.root1;
        else if (roots.root2.lengthSquared != 0.0)
            s = roots.root2;
        else
            s = roots.root3; // if all 3 S's are 0, we have a special case --> we can accept S = 0

        // save S
        roots.root1.copy(s);
        roots.root2.copy(s);
        s.negate();
        roots.root3.copy(s);
        roots.root4.copy(s);

        // get roots
        findGeneralSolutionRootPair(roots.root1, roots.root2, a, b, p, q);
        findGeneralSolutionRootPair(roots.root3, roots.root4, a, b, p, q);

        // return roots
        return roots;
    }

    private final @NotNull QuarticSolution<ComplexFloat> FLOAT_ROOTS = newFloatSolution();
    private final @NotNull QuarticSolution<ComplexDouble> DOUBLE_ROOTS = newDoubleSolution();

    public @NotNull QuarticSolution<ComplexFloat> solve(float a, float e) {
        return solveInto(FLOAT_ROOTS, a, e);
    }
    public @NotNull QuarticSolution<ComplexFloat> solve(float a, float c, float d, float e) {
        return solveInto(FLOAT_ROOTS, a, 0.0f, c, d, e);
    }
    public @NotNull QuarticSolution<ComplexFloat> solve(float a, float b, float c, float d, float e) {
        return solveInto(FLOAT_ROOTS, a, b, c, d, e);
    }

    public @NotNull QuarticSolution<ComplexDouble> solve(double a, double e) {
        return solveInto(DOUBLE_ROOTS, a, e);
    }
    public @NotNull QuarticSolution<ComplexDouble> solve(double a, double c, double d, float e) {
        return solveInto(DOUBLE_ROOTS, a, 0.0, c, d, e);
    }
    public @NotNull QuarticSolution<ComplexDouble> solve(double a, double b, double c, double d, float e) {
        return solveInto(DOUBLE_ROOTS, a, b, c, d, e);
    }

    public static @NotNull QuarticSolution<ComplexFloat> solveQuartic(float a, float e) {
        return solveInto(newFloatSolution(), a, e);
    }
    public static @NotNull QuarticSolution<ComplexFloat> solveQuartic(float a, float c, float d, float e) {
        return solveInto(newFloatSolution(), a, 0.0f, c, d, e);
    }
    public static @NotNull QuarticSolution<ComplexFloat> solveQuartic(float a, float b, float c, float d, float e) {
        return solveInto(newFloatSolution(), a, b, c, d, e);
    }

    public static @NotNull QuarticSolution<ComplexDouble> solveQuartic(double a, double e) {
        return solveInto(newDoubleSolution(), a, e);
    }
    public static @NotNull QuarticSolution<ComplexDouble> solveQuartic(double a, double c, double d, float e) {
        return solveInto(newDoubleSolution(), a, 0.0, c, d, e);
    }
    public static @NotNull QuarticSolution<ComplexDouble> solveQuartic(double a, double b, double c, double d, float e) {
        return solveInto(newDoubleSolution(), a, b, c, d, e);
    }
}
