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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static io.github.utk003.util.math.solve.CubicSolution.newDoubleSolution;
import static io.github.utk003.util.math.solve.CubicSolution.newFloatSolution;

/**
 * A <a href="https://mathworld.wolfram.com/CubicEquation.html" target="_top">cubic equation</a> solver.
 * <p>
 * This solver finds solutions to cubic equations using the
 * <a href="https://mathworld.wolfram.com/CubicFormula.html" target="_top">cubic formula</a>.
 * <p>
 * This class provides the same functionality as class and instance methods.
 * The instance methods use cached {@link CubicSolution} instances to hold and return
 * solutions to cubic equations while the class methods create new instances each time.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 20, 2021
 * @see CubicSolution
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public final class CubicFormula {
    /**
     * Converts the second and third roots of the given {@link CubicSolution} into
     * 120 degree rotations of the first. In other words, the latter two roots
     * become the products of the first root and the
     * <a href="https://mathworld.wolfram.com/PrimitiveRootofUnity.html" target="_top">primitive third roots of unity</a>.
     *
     * @param roots The {@code CubicSolution} instance to modify
     */
    private static void copyR1AndRotateRoots(@NotNull CubicSolution<ComplexDouble> roots) {
        roots.root2.copy(roots.root1).ccw120();
        roots.root3.copy(roots.root1).cw120();
    }
    /**
     * Computes the sum of a complex number and its reciprocal.
     * <p>
     * This method is needed as a part of the cubic formula.
     *
     * @param root  The root to manipulate
     * @param scale A constant multiplier to scale the reciprocal by
     */
    private static void processCubicEdgeCase(@NotNull ComplexDouble root, double scale) {
        if (scale == 0.0) return;

        double real = root.real, imag = root.imag;
        root.invert().scale(scale);
        root.real += real;
        root.imag += imag;
        root.updateMembers();
    }

    /**
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + d = 0}.
     *
     * @param roots The {@link CubicSolution} of {@link ComplexDouble}s to store the roots into
     * @param a     The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param d     The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + d = 0}, stored in a {@code CubicSolution}
     */
    private static @NotNull CubicSolution<ComplexDouble> solveInto(@NotNull CubicSolution<ComplexDouble> roots,
                                                                   double a, double d) {
        // ax^3 + d = 0 --> x^3 = -d/a
        roots.root1.set(Math.cbrt(-d / a), 0.0);
        copyR1AndRotateRoots(roots);
        return roots;
    }
    /**
     * Solves the depressed cubic equation {@code ax}<sup>{@code 3}</sup> {@code + cx + d = 0}.
     *
     * @param roots The {@link CubicSolution} of {@link ComplexDouble}s to store the roots into
     * @param a     The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param c     The coefficient of {@code x}
     * @param d     The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + cx + d = 0}, stored in a {@code CubicSolution}
     */
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
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + bx}<sup>{@code 2}</sup> {@code + cx + d = 0}.
     *
     * @param roots The {@link CubicSolution} of {@link ComplexDouble}s to store the roots into
     * @param a     The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param b     The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c     The coefficient of {@code x}
     * @param d     The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + bx}<sup>{@code 2}</sup> {@code + cx + d = 0},
     * stored in a {@code CubicSolution}
     */
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

    /**
     * A cached instance of a {@link CubicSolution} of {@link ComplexFloat}s.
     */
    private final @NotNull CubicSolution<ComplexFloat> FLOAT_ROOTS = newFloatSolution();
    /**
     * A cached instance of a {@link CubicSolution} of {@link ComplexDouble}s.
     */
    private final @NotNull CubicSolution<ComplexDouble> DOUBLE_ROOTS = newDoubleSolution();

    /**
     * Copies the values from a {@link CubicSolution} of
     * {@link ComplexDouble}s into a  {@code CubicSolution} of {@link ComplexFloat}s.
     *
     * @param from The {@code CubicSolution&lt;ComplexDouble&gt;} to copy the complex numbers from
     * @param into The {@code CubicSolution&lt;ComplexFloat&gt;} to save the complex numbers into
     * @return The {@code CubicSolution&lt;ComplexFloat&gt;} holding the complex numbers
     */
    @Contract("_, _ -> param2")
    private static @NotNull CubicSolution<ComplexFloat> copy(@NotNull CubicSolution<ComplexDouble> from,
                                                             @NotNull CubicSolution<ComplexFloat> into) {
        into.root1.set((float) from.root1.real, (float) from.root1.imag);
        into.root2.set((float) from.root2.real, (float) from.root2.imag);
        into.root3.set((float) from.root3.real, (float) from.root3.imag);
        return into;
    }

    /**
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + d = 0}, stored in a {@link CubicSolution}
     */
    public @NotNull CubicSolution<ComplexFloat> solve(float a, float d) {
        solveInto(DOUBLE_ROOTS, a, d);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }
    /**
     * Solves the depressed cubic equation {@code ax}<sup>{@code 3}</sup> {@code + cx + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param c The coefficient of {@code x}
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + cx + d = 0}, stored in a {@link CubicSolution}
     */
    public @NotNull CubicSolution<ComplexFloat> solve(float a, float c, float d) {
        solveInto(DOUBLE_ROOTS, a, c, d);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + bx}<sup>{@code 2}</sup> {@code + cx + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param b The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c The coefficient of {@code x}
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + bx}<sup>{@code 2}</sup> {@code + cx + d = 0},
     * stored in a {@link CubicSolution}
     */
    public @NotNull CubicSolution<ComplexFloat> solve(float a, float b, float c, float d) {
        solveInto(DOUBLE_ROOTS, a, b, c, d);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }

    /**
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + d = 0}, stored in a {@link CubicSolution}
     */
    public @NotNull CubicSolution<ComplexDouble> solve(double a, double d) {
        return solveInto(DOUBLE_ROOTS, a, d);
    }
    /**
     * Solves the depressed cubic equation {@code ax}<sup>{@code 3}</sup> {@code + cx + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param c The coefficient of {@code x}
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + cx + d = 0}, stored in a {@link CubicSolution}
     */
    public @NotNull CubicSolution<ComplexDouble> solve(double a, double c, double d) {
        return solveInto(DOUBLE_ROOTS, a, c, d);
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + bx}<sup>{@code 2}</sup> {@code + cx + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param b The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c The coefficient of {@code x}
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + bx}<sup>{@code 2}</sup> {@code + cx + d = 0},
     * stored in a {@link CubicSolution}
     */
    public @NotNull CubicSolution<ComplexDouble> solve(double a, double b, double c, double d) {
        return solveInto(DOUBLE_ROOTS, a, b, c, d);
    }

    /**
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + d = 0}, stored in a {@link CubicSolution}
     */
    public static @NotNull CubicSolution<ComplexFloat> solveCubic(float a, float d) {
        CubicSolution<ComplexDouble> roots = solveInto(newDoubleSolution(), a, d);
        return copy(roots, newFloatSolution());
    }
    /**
     * Solves the depressed cubic equation {@code ax}<sup>{@code 3}</sup> {@code + cx + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param c The coefficient of {@code x}
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + cx + d = 0}, stored in a {@link CubicSolution}
     */
    public static @NotNull CubicSolution<ComplexFloat> solveCubic(float a, float c, float d) {
        CubicSolution<ComplexDouble> roots = solveInto(newDoubleSolution(), a, c, d);
        return copy(roots, newFloatSolution());
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + bx}<sup>{@code 2}</sup> {@code + cx + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param b The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c The coefficient of {@code x}
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + bx}<sup>{@code 2}</sup> {@code + cx + d = 0},
     * stored in a {@link CubicSolution}
     */
    public static @NotNull CubicSolution<ComplexFloat> solveCubic(float a, float b, float c, float d) {
        CubicSolution<ComplexDouble> roots = solveInto(newDoubleSolution(), a, b, c, d);
        return copy(roots, newFloatSolution());
    }

    /**
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + d = 0}, stored in a {@link CubicSolution}
     */
    public static @NotNull CubicSolution<ComplexDouble> solveCubic(double a, double d) {
        return solveInto(newDoubleSolution(), a, d);
    }
    /**
     * Solves the depressed cubic equation {@code ax}<sup>{@code 3}</sup> {@code + cx + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param c The coefficient of {@code x}
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + cx + d = 0}, stored in a {@link CubicSolution}
     */
    public static @NotNull CubicSolution<ComplexDouble> solveCubic(double a, double c, double d) {
        return solveInto(newDoubleSolution(), a, c, d);
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + bx}<sup>{@code 2}</sup> {@code + cx + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param b The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c The coefficient of {@code x}
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + bx}<sup>{@code 2}</sup> {@code + cx + d = 0},
     * stored in a {@link CubicSolution}
     */
    public static @NotNull CubicSolution<ComplexDouble> solveCubic(double a, double b, double c, double d) {
        return solveInto(newDoubleSolution(), a, b, c, d);
    }
}
