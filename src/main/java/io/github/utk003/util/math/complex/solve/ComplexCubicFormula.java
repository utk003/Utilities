package io.github.utk003.util.math.complex.solve;

import io.github.utk003.util.math.complex.ComplexDouble;
import io.github.utk003.util.math.complex.ComplexFloat;
import io.github.utk003.util.math.solve.CubicSolution;
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
 * <p>
 * This class is for equations whose coefficients are complex. For a specialized solver
 * that only works with real coefficients, use {@link io.github.utk003.util.math.solve.CubicFormula}.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 21, 2021
 * @see CubicSolution
 * @see io.github.utk003.util.math.solve.CubicFormula
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public final class ComplexCubicFormula {
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
     * <p>
     * NOTE: This method modifies {@code scale}.
     *
     * @param root  The root to manipulate
     * @param scale A constant multiplier to scale the reciprocal of the root by
     */
    private static void processCubicEdgeCase(@NotNull ComplexDouble root, @NotNull ComplexDouble scale) {
        if (scale.isZero()) return;

        // WWW = r + s / r
        scale.divide(root); // s / r
        root.add(scale); // r + s / r
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
                                                                   @NotNull ComplexDouble a, @NotNull ComplexDouble d) {
        // ax^3 + d = 0 --> x^3 = -d/a
        roots.root1.copy(d).negate().divide(a).cbrt();
        copyR1AndRotateRoots(roots);
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
     * @param w1    Cached working {@code ComplexDouble} instance #1
     * @param w2    Cached working {@code ComplexDouble} instance #2
     * @param w3    Cached working {@code ComplexDouble} instance #3
     * @param w4    Cached working {@code ComplexDouble} instance #4
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + bx}<sup>{@code 2}</sup> {@code + cx + d = 0},
     * stored in a {@code CubicSolution}
     */
    private static @NotNull CubicSolution<ComplexDouble> solveInto(@NotNull CubicSolution<ComplexDouble> roots,
                                                                   @NotNull ComplexDouble a, @NotNull ComplexDouble b,
                                                                   @NotNull ComplexDouble c, @NotNull ComplexDouble d,
                                                                   @NotNull ComplexDouble w1, @NotNull ComplexDouble w2,
                                                                   @NotNull ComplexDouble w3, @NotNull ComplexDouble w4) {
        // ax^3 + bx^2 + cx + d (https://en.wikipedia.org/wiki/Cubic_equation#General_cubic_formula)
        ComplexDouble d0, d1;
        {
            ComplexDouble bb = w1.copy(b).square(), // bb
                    ac3 = w2.copy(a).multiply(c).multiply(3); // 3ac
            d0 = w3.copy(bb).subtract(ac3); // b^2 - 3ac

            ComplexDouble bbb2 = bb.multiply(b).multiply(2), // 2bbb
                    abc9 = ac3.multiply(b).multiply(3), // 9abc
                    aad27 = w4.copy(a).square().multiply(d).multiply(27); // 27aad
            d1 = aad27.add(bbb2).subtract(abc9); // 2bbb - 9abc + 27aad
        }
        // d0 = w3, d1 = w4

        if (d0.isZero())
            roots.root1.copy(d1).cbrt();
        else {
            w2.copy(d0).cube().multiply(4); // 4 * d0 * d0 * d0
            ComplexDouble discr = w1.copy(d1).square().subtract(w2); // d1 * d1 - 4 * d0 * d0 * d0
            roots.root1.copy(discr).sqrt().add(d1).divide(2).cbrt();
        }
        copyR1AndRotateRoots(roots);

        // w2 is currently unused -- can be modified
        processCubicEdgeCase(roots.root1, w2.copy(d0));
        processCubicEdgeCase(roots.root2, w2.copy(d0));
        processCubicEdgeCase(roots.root3, w2.copy(d0));

        roots.root1.add(b).divide(a).divide(-3);
        roots.root2.add(b).divide(a).divide(-3);
        roots.root3.add(b).divide(a).divide(-3);

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
     * Cached instances of {@link ComplexDouble}s to hold coefficients.
     */
    private final @NotNull ComplexDouble A = new ComplexDouble(), B = new ComplexDouble(),
            C = new ComplexDouble(), D = new ComplexDouble();
    /**
     * Cached working instances of {@link ComplexDouble}s.
     * These are used to hold intermediate values.
     */
    private final @NotNull ComplexDouble W1 = new ComplexDouble(), W2 = new ComplexDouble(),
            W3 = new ComplexDouble(), W4 = new ComplexDouble();
    /**
     * Cached "zero" instance of {@link ComplexDouble}.
     */
    private static final @NotNull ComplexDouble ZERO = new ComplexDouble().zero();

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
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + d = 0}, stored in a {@link CubicSolution}
     */
    public @NotNull CubicSolution<ComplexFloat> solve(@NotNull ComplexFloat a, @NotNull ComplexFloat d) {
        solveInto(DOUBLE_ROOTS, copy(a, A), copy(d, D));
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
    public @NotNull CubicSolution<ComplexFloat> solve(@NotNull ComplexFloat a, @NotNull ComplexFloat c, @NotNull ComplexFloat d) {
        solveInto(DOUBLE_ROOTS, copy(a, A), ZERO, copy(c, C), copy(d, D), W1, W2, W3, W4);
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
    public @NotNull CubicSolution<ComplexFloat> solve(@NotNull ComplexFloat a, @NotNull ComplexFloat b, @NotNull ComplexFloat c, @NotNull ComplexFloat d) {
        solveInto(DOUBLE_ROOTS, copy(a, A), copy(b, B), copy(c, C), copy(d, D), W1, W2, W3, W4);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }

    /**
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + d = 0}, stored in a {@link CubicSolution}
     */
    public @NotNull CubicSolution<ComplexDouble> solve(@NotNull ComplexDouble a, @NotNull ComplexDouble d) {
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
    public @NotNull CubicSolution<ComplexDouble> solve(@NotNull ComplexDouble a, @NotNull ComplexDouble c, @NotNull ComplexDouble d) {
        return solveInto(DOUBLE_ROOTS, a, ZERO, c, d, W1, W2, W3, W4);
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
    public @NotNull CubicSolution<ComplexDouble> solve(@NotNull ComplexDouble a, @NotNull ComplexDouble b, @NotNull ComplexDouble c, @NotNull ComplexDouble d) {
        return solveInto(DOUBLE_ROOTS, a, b, c, d, W1, W2, W3, W4);
    }

    /**
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + d = 0}, stored in a {@link CubicSolution}
     */
    public static @NotNull CubicSolution<ComplexFloat> solveCubic(@NotNull ComplexFloat a, @NotNull ComplexFloat d) {
        CubicSolution<ComplexDouble> roots = solveInto(
                newDoubleSolution(),
                copy(a, new ComplexDouble()),
                copy(d, new ComplexDouble())
        );
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
    public static @NotNull CubicSolution<ComplexFloat> solveCubic(@NotNull ComplexFloat a, @NotNull ComplexFloat c, @NotNull ComplexFloat d) {
        CubicSolution<ComplexDouble> roots = solveInto(
                newDoubleSolution(),
                copy(a, new ComplexDouble()), ZERO, copy(c, new ComplexDouble()), copy(d, new ComplexDouble()),
                new ComplexDouble(), new ComplexDouble(), new ComplexDouble(), new ComplexDouble()
        );
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
    public static @NotNull CubicSolution<ComplexFloat> solveCubic(@NotNull ComplexFloat a, @NotNull ComplexFloat b, @NotNull ComplexFloat c, @NotNull ComplexFloat d) {
        CubicSolution<ComplexDouble> roots = solveInto(
                newDoubleSolution(),
                copy(a, new ComplexDouble()), copy(b, new ComplexDouble()), copy(c, new ComplexDouble()), copy(d, new ComplexDouble()),
                new ComplexDouble(), new ComplexDouble(), new ComplexDouble(), new ComplexDouble()
        );
        return copy(roots, newFloatSolution());
    }

    /**
     * Solves the cubic equation {@code ax}<sup>{@code 3}</sup> {@code + d = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param d The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 3}</sup> {@code + d = 0}, stored in a {@link CubicSolution}
     */
    public static @NotNull CubicSolution<ComplexDouble> solveCubic(@NotNull ComplexDouble a, @NotNull ComplexDouble d) {
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
    public static @NotNull CubicSolution<ComplexDouble> solveCubic(@NotNull ComplexDouble a, @NotNull ComplexDouble c, @NotNull ComplexDouble d) {
        return solveInto(
                newDoubleSolution(),
                a, ZERO, c, d,
                new ComplexDouble(), new ComplexDouble(), new ComplexDouble(), new ComplexDouble()
        );
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
    public static @NotNull CubicSolution<ComplexDouble> solveCubic(@NotNull ComplexDouble a, @NotNull ComplexDouble b, @NotNull ComplexDouble c, @NotNull ComplexDouble d) {
        return solveInto(
                newDoubleSolution(),
                a, b, c, d,
                new ComplexDouble(), new ComplexDouble(), new ComplexDouble(), new ComplexDouble()
        );
    }
}
