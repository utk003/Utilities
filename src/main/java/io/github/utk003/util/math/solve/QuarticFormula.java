/*
MIT License

Copyright (c) 2021 Utkarsh Priyam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package io.github.utk003.util.math.solve;

import io.github.utk003.util.math.complex.ComplexDouble;
import io.github.utk003.util.math.complex.ComplexFloat;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;

import static io.github.utk003.util.math.solve.QuarticSolution.newDoubleSolution;
import static io.github.utk003.util.math.solve.QuarticSolution.newFloatSolution;

// <a href="https://mathworld.wolfram.com/QuarticEquation.html" target="_top">quartic equation</a>
@ScheduledForRelease(inVersion = "v3.0.0")
@RequiresDocumentation
public final class QuarticFormula {
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
    private static void findGeneralSolutionRootPair(@NotNull ComplexDouble cd1, @NotNull ComplexDouble cd2,
                                                    double a, double b, double p, double q) {
        double real = cd2.real + b / (4 * a), imag = cd2.imag;
        cd2.square().scale(4);

        if (q == 0.0) cd1.zero();
        else cd1.invert().scale(q);
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

    private static @NotNull QuarticSolution<ComplexDouble> solveInto(@NotNull QuarticSolution<ComplexDouble> roots,
                                                                     double a, double e) {
        // ax^4 + e = 0 --> x^4 = -e/a
        roots.root1.set(-e / a, 0.0).sqrt().sqrt(); // 1/4 power = 4th root
        roots.root2.copy(roots.root1).ccw90();  // rot90
        roots.root3.copy(roots.root1).rot180(); // rot180
        roots.root4.copy(roots.root1).cw90();   // rot270
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
            real = d0 != 0.0 ? (d1 + Math.sqrt(discr)) / 2 : d1;
            imag = 0.0;
        } else {
            real = d1 / 2;
            imag = Math.sqrt(-discr) / 2;
        }

        // rotate Q to other 2 possibilities
        roots.root1.set(real, imag).cbrt();
        roots.root2.copy(roots.root1).ccw120();
        roots.root3.copy(roots.root1).cw120();

        // get all 3 Ss
        calculateGeneralSolutionS(roots.root1, d0, a, p);
        calculateGeneralSolutionS(roots.root2, d0, a, p);
        calculateGeneralSolutionS(roots.root3, d0, a, p);

        // pick right S
        ComplexDouble s;
        if (!roots.root1.isZero())
            s = roots.root1;
        else if (!roots.root2.isZero())
            s = roots.root2;
        else
            s = roots.root3; // if all 3 S's are 0, we have a special case --> we can accept S = 0

        // save S
        roots.root1.copy(s);
        roots.root2.copy(s);
        s.negate();
        roots.root3.copy(s);
        roots.root4.copy(s);
        s.negate();

        // get roots
        findGeneralSolutionRootPair(roots.root1, roots.root2, a, b, p, q);
        findGeneralSolutionRootPair(roots.root3, roots.root4, a, b, p, q);

        // return roots
        return roots;
    }

    private final @NotNull QuarticSolution<ComplexFloat> FLOAT_ROOTS = newFloatSolution();
    private final @NotNull QuarticSolution<ComplexDouble> DOUBLE_ROOTS = newDoubleSolution();

    private static @NotNull QuarticSolution<ComplexFloat> copy(@NotNull QuarticSolution<ComplexDouble> from,
                                                               @NotNull QuarticSolution<ComplexFloat> into) {
        into.root1.set((float) from.root1.real, (float) from.root1.imag);
        into.root2.set((float) from.root2.real, (float) from.root2.imag);
        into.root3.set((float) from.root3.real, (float) from.root3.imag);
        into.root4.set((float) from.root4.real, (float) from.root4.imag);
        return into;
    }

    public @NotNull QuarticSolution<ComplexFloat> solve(float a, float e) {
        solveInto(DOUBLE_ROOTS, a, e);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }
    public @NotNull QuarticSolution<ComplexFloat> solve(float a, float d, float e) {
        solveInto(DOUBLE_ROOTS, a, 0.0f, 0.0f, d, e);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }

    public @NotNull QuarticSolution<ComplexFloat> solve(float a, float c, float d, float e) {
        solveInto(DOUBLE_ROOTS, a, 0.0f, c, d, e);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }
    public @NotNull QuarticSolution<ComplexFloat> solve(float a, float b, float c, float d, float e) {
        solveInto(DOUBLE_ROOTS, a, b, c, d, e);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }

    public @NotNull QuarticSolution<ComplexDouble> solve(double a, double e) {
        return solveInto(DOUBLE_ROOTS, a, e);
    }
    public @NotNull QuarticSolution<ComplexDouble> solve(double a, double d, float e) {
        return solveInto(DOUBLE_ROOTS, a, 0.0, 0.0, d, e);
    }
    public @NotNull QuarticSolution<ComplexDouble> solve(double a, double c, double d, float e) {
        return solveInto(DOUBLE_ROOTS, a, 0.0, c, d, e);
    }
    public @NotNull QuarticSolution<ComplexDouble> solve(double a, double b, double c, double d, float e) {
        return solveInto(DOUBLE_ROOTS, a, b, c, d, e);
    }

    public static @NotNull QuarticSolution<ComplexFloat> solveQuartic(float a, float e) {
        QuarticSolution<ComplexDouble> roots = solveInto(newDoubleSolution(), a, e);
        return copy(roots, newFloatSolution());
    }
    public static @NotNull QuarticSolution<ComplexFloat> solveQuartic(float a, float d, float e) {
        QuarticSolution<ComplexDouble> roots = solveInto(newDoubleSolution(), a, 0.0f, 0.0f, d, e);
        return copy(roots, newFloatSolution());
    }
    public static @NotNull QuarticSolution<ComplexFloat> solveQuartic(float a, float c, float d, float e) {
        QuarticSolution<ComplexDouble> roots = solveInto(newDoubleSolution(), a, 0.0f, c, d, e);
        return copy(roots, newFloatSolution());
    }
    public static @NotNull QuarticSolution<ComplexFloat> solveQuartic(float a, float b, float c, float d, float e) {
        QuarticSolution<ComplexDouble> roots = solveInto(newDoubleSolution(), a, b, c, d, e);
        return copy(roots, newFloatSolution());
    }

    public static @NotNull QuarticSolution<ComplexDouble> solveQuartic(double a, double e) {
        return solveInto(newDoubleSolution(), a, e);
    }
    public static @NotNull QuarticSolution<ComplexDouble> solveQuartic(double a, double d, float e) {
        return solveInto(newDoubleSolution(), a, 0.0, 0.0, d, e);
    }
    public static @NotNull QuarticSolution<ComplexDouble> solveQuartic(double a, double c, double d, float e) {
        return solveInto(newDoubleSolution(), a, 0.0, c, d, e);
    }
    public static @NotNull QuarticSolution<ComplexDouble> solveQuartic(double a, double b, double c, double d, float e) {
        return solveInto(newDoubleSolution(), a, b, c, d, e);
    }
}
