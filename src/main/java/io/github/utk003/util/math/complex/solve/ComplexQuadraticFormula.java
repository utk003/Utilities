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

package io.github.utk003.util.math.complex.solve;

import io.github.utk003.util.math.complex.ComplexDouble;
import io.github.utk003.util.math.complex.ComplexFloat;
import io.github.utk003.util.math.solve.QuadraticSolution;
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
 * <p>
 * This class is for equations whose coefficients are complex. For a specialized solver
 * that only works with real coefficients, use {@link io.github.utk003.util.math.solve.QuadraticFormula}.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 21, 2021
 * @see QuadraticSolution
 * @see io.github.utk003.util.math.solve.QuadraticFormula
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public final class ComplexQuadraticFormula {
    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + c = 0}.
     *
     * @param roots The {@link QuadraticSolution} of {@link ComplexDouble}s to store the roots into
     * @param a     The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c     The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + c = 0}, stored in a {@code QuadraticSolution}
     */
    private static @NotNull QuadraticSolution<ComplexDouble> solveInto(@NotNull QuadraticSolution<ComplexDouble> roots,
                                                                       @NotNull ComplexDouble a, @NotNull ComplexDouble c) {
        // ax^2 + c = 0 --> x^2 = -c/a --> x = ±√(-c/a)
        roots.root1.copy(c).negate().divide(a).sqrt();
        roots.root2.copy(roots.root1).negate();
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
                                                                       @NotNull ComplexDouble a, @NotNull ComplexDouble b,
                                                                       @NotNull ComplexDouble c) {
        // ax^2 + bx + c = 0--> x = [-b ± √(b^2 - 4ac)] / (2a)

        // r1 = b^2
        roots.root1.copy(b).square();

        // r2 = 4ac
        roots.root2.copy(a).multiply(c).multiply(4);

        // r1 = √(b^2 - 4ac) / a
        roots.root1.subtract(roots.root2).sqrt().divide(a);

        // r2 = -b / a
        roots.root2.copy(b).negate().divide(a);

        // r1 = [-b + √(b^2 - 4ac)] / (2a)
        roots.root1.add(roots.root2).divide(2);

        // r2 = [-b - √(b^2 - 4ac)] / (2a)
        roots.root2.subtract(roots.root1);
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
     * Cached instances of {@link ComplexDouble}s to hold coefficients.
     */
    private final @NotNull ComplexDouble A = new ComplexDouble(), B = new ComplexDouble(), C = new ComplexDouble();

    /**
     * Copies the values from a {@link QuadraticSolution} of
     * {@link ComplexDouble}s into a {@code QuadraticSolution} of {@link ComplexFloat}s.
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
     * Copies the complex number from a {@link ComplexFloat} to a {@link ComplexDouble}.
     *
     * @param from The {@code ComplexFloat} to copy the complex number from
     * @param into The {@code ComplexDouble} to save the complex number into
     * @return The {@code ComplexDouble} holding the complex number
     */
    @Contract("_, _ -> param2")
    private static @NotNull ComplexDouble copy(@NotNull ComplexFloat from, @NotNull ComplexDouble into) {
        return into.set(from.real, from.imag);
    }

    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + c = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + c = 0}, stored in a {@link QuadraticSolution}
     */
    public @NotNull QuadraticSolution<ComplexFloat> solve(@NotNull ComplexFloat a, @NotNull ComplexFloat c) {
        solveInto(DOUBLE_ROOTS, copy(a, A), copy(c, C));
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
    public @NotNull QuadraticSolution<ComplexFloat> solve(@NotNull ComplexFloat a, @NotNull ComplexFloat b, @NotNull ComplexFloat c) {
        solveInto(DOUBLE_ROOTS, copy(a, A), copy(b, B), copy(c, C));
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }
    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + c = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + c = 0}, stored in a {@link QuadraticSolution}
     */
    public @NotNull QuadraticSolution<ComplexDouble> solve(@NotNull ComplexDouble a, @NotNull ComplexDouble c) {
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
    public @NotNull QuadraticSolution<ComplexDouble> solve(@NotNull ComplexDouble a, @NotNull ComplexDouble b, @NotNull ComplexDouble c) {
        return solveInto(DOUBLE_ROOTS, a, b, c);
    }

    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + c = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + c = 0}, stored in a {@link QuadraticSolution}
     */
    public static @NotNull QuadraticSolution<ComplexFloat> solveQuadratic(@NotNull ComplexFloat a, @NotNull ComplexFloat c) {
        QuadraticSolution<ComplexDouble> roots = solveInto(
                newDoubleSolution(),
                copy(a, new ComplexDouble()),
                copy(c, new ComplexDouble())
        );
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
    public static @NotNull QuadraticSolution<ComplexFloat> solveQuadratic(@NotNull ComplexFloat a, @NotNull ComplexFloat b, @NotNull ComplexFloat c) {
        QuadraticSolution<ComplexDouble> roots = solveInto(
                newDoubleSolution(),
                copy(a, new ComplexDouble()),
                copy(b, new ComplexDouble()),
                copy(c, new ComplexDouble())
        );
        return copy(roots, newFloatSolution());
    }
    /**
     * Solves the quadratic equation {@code ax}<sup>{@code 2}</sup> {@code + c = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param c The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 2}</sup> {@code + c = 0}, stored in a {@link QuadraticSolution}
     */
    public static @NotNull QuadraticSolution<ComplexDouble> solveQuadratic(@NotNull ComplexDouble a, @NotNull ComplexDouble c) {
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
    public static @NotNull QuadraticSolution<ComplexDouble> solveQuadratic(@NotNull ComplexDouble a, @NotNull ComplexDouble b, @NotNull ComplexDouble c) {
        return solveInto(newDoubleSolution(), a, b, c);
    }
}
