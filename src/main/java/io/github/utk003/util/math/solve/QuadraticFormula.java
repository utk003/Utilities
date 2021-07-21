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

import static io.github.utk003.util.math.solve.QuadraticSolution.newDoubleSolution;
import static io.github.utk003.util.math.solve.QuadraticSolution.newFloatSolution;

/**
 * A <a href="https://mathworld.wolfram.com/QuadraticEquation.html" target="_top">quadratic equation</a> solver.
 * <p>
 * This solver finds solutions to quadratic equations using the
 * <a href="https://mathworld.wolfram.com/QuadraticFormula.html" target="_top">quadratic formula</a>.
 * <p>
 * This class provides the same functionality as class and instance methods.
 * The instance methods use cached {@link QuadraticSolution} instances to hold and return
 * solutions to cubic equations while the class methods create new instances each time.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 20, 2021
 * @see QuadraticSolution
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public final class QuadraticFormula {
    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + c = 0}.
     *
     * @param roots The {@link QuadraticSolution} of {@link ComplexDouble}s to store the roots into
     * @param a     The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c     The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + c = 0}, stored in a {@code QuadraticSolution}
     */
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
    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + bx + c = 0}.
     *
     * @param roots The {@link QuadraticSolution} of {@link ComplexDouble}s to store the roots into
     * @param a     The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param b     The coefficient of {@code x}
     * @param c     The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + bx + c = 0}, stored in a {@code QuadraticSolution}
     */
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

    /**
     * A cached instance of a {@link QuadraticSolution} of {@link ComplexFloat}s.
     */
    private final @NotNull QuadraticSolution<ComplexFloat> FLOAT_ROOTS = newFloatSolution();
    /**
     * A cached instance of a {@link QuadraticSolution} of {@link ComplexDouble}s.
     */
    private final @NotNull QuadraticSolution<ComplexDouble> DOUBLE_ROOTS = newDoubleSolution();

    /**
     * Copies the values from a {@link QuadraticSolution} of
     * {@link ComplexDouble}s into a  {@code QuadraticSolution} of {@link ComplexFloat}s.
     *
     * @param from The {@code QuadraticSolution&lt;ComplexDouble&gt;} to copy the complex numbers from
     * @param into The {@code QuadraticSolution&lt;ComplexFloat&gt;} to save the complex numbers into
     * @return The {@code QuadraticSolution&lt;ComplexFloat&gt;} holding the complex numbers
     */
    @Contract("_, _ -> param2")
    private static @NotNull QuadraticSolution<ComplexFloat> copy(@NotNull QuadraticSolution<ComplexDouble> from,
                                                                 @NotNull QuadraticSolution<ComplexFloat> into) {
        into.root1.set((float) from.root1.real, (float) from.root1.imag);
        into.root2.set((float) from.root2.real, (float) from.root2.imag);
        return into;
    }

    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + c = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + c = 0}, stored in a {@link QuadraticSolution}
     */
    public @NotNull QuadraticSolution<ComplexFloat> solve(float a, float c) {
        solveInto(DOUBLE_ROOTS, a, c);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }
    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + bx + c = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param b The coefficient of {@code x}
     * @param c The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + bx + c = 0}, stored in a {@link QuadraticSolution}
     */
    public @NotNull QuadraticSolution<ComplexFloat> solve(float a, float b, float c) {
        solveInto(DOUBLE_ROOTS, a, b, c);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }
    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + c = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + c = 0}, stored in a {@link QuadraticSolution}
     */
    public @NotNull QuadraticSolution<ComplexDouble> solve(double a, double c) {
        return solveInto(DOUBLE_ROOTS, a, c);
    }
    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + bx + c = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param b The coefficient of {@code x}
     * @param c The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + bx + c = 0}, stored in a {@link QuadraticSolution}
     */
    public @NotNull QuadraticSolution<ComplexDouble> solve(double a, double b, double c) {
        return solveInto(DOUBLE_ROOTS, a, b, c);
    }

    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + c = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + c = 0}, stored in a {@link QuadraticSolution}
     */
    public static @NotNull QuadraticSolution<ComplexFloat> solveQuadratic(float a, float c) {
        QuadraticSolution<ComplexDouble> roots = solveInto(newDoubleSolution(), a, c);
        return copy(roots, newFloatSolution());
    }
    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + bx + c = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param b The coefficient of {@code x}
     * @param c The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + bx + c = 0}, stored in a {@link QuadraticSolution}
     */
    public static @NotNull QuadraticSolution<ComplexFloat> solveQuadratic(float a, float b, float c) {
        QuadraticSolution<ComplexDouble> roots = solveInto(newDoubleSolution(), a, b, c);
        return copy(roots, newFloatSolution());
    }
    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + c = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + c = 0}, stored in a {@link QuadraticSolution}
     */
    public static @NotNull QuadraticSolution<ComplexDouble> solveQuadratic(double a, double c) {
        return solveInto(newDoubleSolution(), a, c);
    }
    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + bx + c = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param b The coefficient of {@code x}
     * @param c The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + bx + c = 0}, stored in a {@link QuadraticSolution}
     */
    public static @NotNull QuadraticSolution<ComplexDouble> solveQuadratic(double a, double b, double c) {
        return solveInto(newDoubleSolution(), a, b, c);
    }
}
