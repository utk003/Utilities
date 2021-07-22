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
import io.github.utk003.util.math.solve.QuarticSolution;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static io.github.utk003.util.math.solve.QuarticSolution.newDoubleSolution;
import static io.github.utk003.util.math.solve.QuarticSolution.newFloatSolution;

/**
 * A <a href="https://mathworld.wolfram.com/QuarticEquation.html" target="_top">quartic equation</a> solver.
 * <p>
 * This class provides the same functionality as class and instance methods.
 * The instance methods use cached {@link QuarticSolution} instances to hold and return
 * solutions to cubic equations while the class methods create new instances each time.
 * <p>
 * This class is for equations whose coefficients are complex. For a specialized solver
 * that only works with real coefficients, use {@link io.github.utk003.util.math.solve.QuarticFormula}.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 22, 2021
 * @see QuarticSolution
 * @see io.github.utk003.util.math.solve.QuarticFormula
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public final class ComplexQuarticFormula {
    /**
     * Computes the value of {@code S} in
     * <a href="https://en.wikipedia.org/wiki/Quartic_function#General_formula_for_roots" target="_top">this formula</a>.
     * <p>
     * The value of {@code cd} is initially {@code Q} (from the same formula).
     *
     * @param cd The value {@code Q} as well as the {@link ComplexDouble} to store the result in
     * @param d0 The value of {@code d0} from the formula
     * @param a  The leading coefficient of the quartic equation
     * @param p  The value of {@code p} from the formula
     * @param w1 Cached working {@code ComplexDouble} instance #1
     */
    private static void calculateGeneralSolutionS(@NotNull ComplexDouble cd, @NotNull ComplexDouble d0,
                                                  @NotNull ComplexDouble a, @NotNull ComplexDouble p,
                                                  @NotNull ComplexDouble w1) {
        if (!d0.isZero()) {
            // WWW = cd + d0 / cd
            w1.copy(d0).divide(cd);
            cd.add(w1);
        }
        cd.divide(a).divide(2);
        cd.subtract(p).divide(6).sqrt();
    }
    /**
     * Computes two roots of the quartic given the value of {@code S} in
     * <a href="https://en.wikipedia.org/wiki/Quartic_function#General_formula_for_roots" target="_top">this formula</a>.
     * <p>
     * Initially, each of {@code cd1} and {@code cd2} hold the same complex number {@code S}
     *
     * @param cd1 The {@link ComplexDouble} to store the first root in
     * @param cd2 The {@code ComplexDouble} to store the second root in
     * @param a   The leading coefficient of the quartic equation
     * @param b   The second coefficient of the quartic equation
     * @param p   The value of {@code p} from the formula
     * @param q   The value of {@code q} from the formula
     * @param w1  Cached working {@code ComplexDouble} instance #1
     */
    private static void findGeneralSolutionRootPair(@NotNull ComplexDouble cd1, @NotNull ComplexDouble cd2,
                                                    @NotNull ComplexDouble a, @NotNull ComplexDouble b,
                                                    @NotNull ComplexDouble p, @NotNull ComplexDouble q,
                                                    @NotNull ComplexDouble w1) {
        // cd1 = cd2 = S
        if (q.isZero())
            cd1.copy(p).divide(-2).sqrt();
        else {
            w1.copy(cd2).multiply(2);
            cd1.copy(q).divide(w1).add(p); // p + q / 2s
            w1.multiply(cd2);
            cd1.add(w1).divide(-2).sqrt();
        }
        // cd1 = (1/2) √(-4S^2 - 2p - q/S)

        w1.copy(b).divide(a).divide(-4).add(cd2); // -b/(4a) + S
        cd2.copy(w1).subtract(cd1);
        // cd2 = -b/(4a) + S - (1/2) √(-4S^2 - 2p - q/S)

        cd1.add(w1);
        // cd1 = -b/(4a) + S + (1/2) √(-4S^2 - 2p - q/S)
    }

    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + e = 0}.
     *
     * @param roots The {@link QuarticSolution} of {@link ComplexDouble}s to store the roots into
     * @param a     The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param e     The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + e = 0}, stored in a {@code QuarticSolution}
     */
    private static @NotNull QuarticSolution<ComplexDouble> solveInto(@NotNull QuarticSolution<ComplexDouble> roots,
                                                                     @NotNull ComplexDouble a, @NotNull ComplexDouble e) {
        // ax^4 + e = 0 --> x^4 = -e/a
        roots.root1.copy(e).negate().divide(a).sqrt().sqrt(); // 1/4 power = 4th root
        roots.root2.copy(roots.root1).ccw90();  // rot90
        roots.root3.copy(roots.root1).rot180(); // rot180
        roots.root4.copy(roots.root1).cw90();   // rot270
        return roots;
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + bx}<sup>{@code 3}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}.
     *
     * @param roots The {@link QuarticSolution} of {@link ComplexDouble}s to store the roots into
     * @param a     The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param b     The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param c     The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param d     The coefficient of {@code x}
     * @param e     The coefficient of {@code 1}
     * @param w1    Cached working {@code ComplexDouble} instance #1
     * @param w2    Cached working {@code ComplexDouble} instance #2
     * @param w3    Cached working {@code ComplexDouble} instance #3
     * @param w4    Cached working {@code ComplexDouble} instance #4
     * @param w5    Cached working {@code ComplexDouble} instance #5
     * @param w6    Cached working {@code ComplexDouble} instance #6
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + bx}<sup>{@code 3}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}, stored in a {@code QuarticSolution}
     */
    private static @NotNull QuarticSolution<ComplexDouble> solveInto(@NotNull QuarticSolution<ComplexDouble> roots,
                                                                     @NotNull ComplexDouble a, @NotNull ComplexDouble b,
                                                                     @NotNull ComplexDouble c, @NotNull ComplexDouble d,
                                                                     @NotNull ComplexDouble e, @NotNull ComplexDouble w1,
                                                                     @NotNull ComplexDouble w2, @NotNull ComplexDouble w3,
                                                                     @NotNull ComplexDouble w4, @NotNull ComplexDouble w5,
                                                                     @NotNull ComplexDouble w6) {
        // General solution: https://en.wikipedia.org/wiki/Quartic_function#General_formula_for_roots
        // ax^4 + bx^3 + cx^2 + dx + e

        ComplexDouble d0, d1, discr, p, q;
        {
            ComplexDouble cc = w2.copy(c).square(), // c^2
                    bd3 = w3.copy(b).multiply(d).multiply(3), // 3bd
                    ae12 = w4.copy(a).multiply(e).multiply(12); // 12ae
            d0 = w1.copy(cc).subtract(bd3).add(ae12);
            // d0 = w1

            ComplexDouble cc2 = cc.multiply(2), // 2c^2
                    bd9 = bd3.multiply(3), // 9bd
                    ae72 = ae12.multiply(6); // 72ae
            d1 = cc2.subtract(bd9).subtract(ae72).multiply(c); // 2c^3 - 9bcd - 72ace
            ComplexDouble bbe = w3.copy(b).square().multiply(e), // b^2e
                    add = w4.copy(d).square().multiply(a); // ad^2
            bbe.add(add).multiply(27); // 27b^2e + 27ad^2
            d1.add(w3); // 2c^3 - 9bcd + 27b^2e + 27ad^2 - 72ace
            // d1 = w2

            ComplexDouble d1d1 = w3.copy(d1).square(), // d1^2
                    d0d0d04 = w4.copy(d0).cube().multiply(4); // 4d0^3
            discr = d1d1.subtract(d0d0d04); // d1^2 - 4d0^3
            // discr = w3

            ComplexDouble ca = w6.copy(c).divide(a), // c / a
                    ba2 = w5.copy(b).divide(a).divide(2); // b / (2a)
            p = w4.copy(ba2).square().multiply(-3, 2).add(ca); // c/a - 3/2 (b/(2a))^2 = (8ac - 3b^2) / (8a^2)
            // p = w4

            ComplexDouble pba2 = w6.copy(p).multiply(ba2), // (4abc - 3/2 b^3) / (8a^3)
                    nba232 = ba2.cube().divide(-2); // - b^3 / (16 a^3)
            q = nba232.subtract(pba2);
            // q = - b^3 / (16 a^3) - [4abc / (8a^3) - 3/16 b^3 / (a^3)]
            //   = 1/8 b^3 / a^3 - 4abc / (8a^3) = [b^3 - 4abc] / (8a^3)
            ComplexDouble da = w6.copy(d).divide(a); // d / a
            q.add(da); // [b^3 - 4abc] / (8a^3) + (d/a) = [b^3 - 4abc + 8a^2d] / (8a^3)
            // q = w5
        }
        // w6 is a free worker instance

        // find first Q
        if (d0.isZero())
            roots.root1.copy(d1).cbrt();
        else
            roots.root1.copy(discr).sqrt().add(d1).divide(2).cbrt();

        // rotate Q to other 2 possibilities
        roots.root2.copy(roots.root1).ccw120();
        roots.root3.copy(roots.root1).cw120();

        // get all 3 Ss
        calculateGeneralSolutionS(roots.root1, d0, a, p, w6);
        calculateGeneralSolutionS(roots.root2, d0, a, p, w6);
        calculateGeneralSolutionS(roots.root3, d0, a, p, w6);

        // pick right S
        ComplexDouble s = roots.root4;
        if (!roots.root1.isZero())
            s.copy(roots.root1);
        else if (!roots.root2.isZero())
            s.copy(roots.root2);
        else
            s.copy(roots.root3); // if all 3 S's are 0, we have a special case --> we can accept S = 0

        // save S
        roots.root1.copy(s);
        roots.root2.copy(s);
        s.negate();
        roots.root3.copy(s);
        // roots.root4.copy(s); // already true

        // get roots
        findGeneralSolutionRootPair(roots.root1, roots.root2, a, b, p, q, w6);
        findGeneralSolutionRootPair(roots.root3, roots.root4, a, b, p, q, w6);

        // return roots
        return roots;
    }

    /**
     * A cached instance of a {@link QuarticSolution} of {@link ComplexFloat}s.
     */
    private final @NotNull QuarticSolution<ComplexFloat> FLOAT_ROOTS = newFloatSolution();
    /**
     * A cached instance of a {@link QuarticSolution} of {@link ComplexDouble}s.
     */
    private final @NotNull QuarticSolution<ComplexDouble> DOUBLE_ROOTS = newDoubleSolution();
    /**
     * Cached instances of {@link ComplexDouble}s to hold coefficients.
     */
    private final @NotNull ComplexDouble A = new ComplexDouble(), B = new ComplexDouble(),
            C = new ComplexDouble(), D = new ComplexDouble(), E = new ComplexDouble();
    /**
     * Cached working instances of {@link ComplexDouble}s.
     * These are used to hold intermediate values.
     */
    private final @NotNull ComplexDouble W1 = new ComplexDouble(), W2 = new ComplexDouble(),
            W3 = new ComplexDouble(), W4 = new ComplexDouble(), W5 = new ComplexDouble(), W6 = new ComplexDouble();
    /**
     * Cached "zero" instance of {@link ComplexDouble}.
     */
    private static final @NotNull ComplexDouble ZERO = new ComplexDouble().zero();

    /**
     * Copies the values from a {@link QuarticSolution} of
     * {@link ComplexDouble}s into a  {@code QuarticSolution} of {@link ComplexFloat}s.
     *
     * @param from The {@code QuarticSolution&lt;ComplexDouble&gt;} to copy the complex numbers from
     * @param into The {@code QuarticSolution&lt;ComplexFloat&gt;} to save the complex numbers into
     * @return The {@code QuarticSolution&lt;ComplexFloat&gt;} holding the complex numbers
     */
    @Contract("_, _ -> param2")
    private static @NotNull QuarticSolution<ComplexFloat> copy(@NotNull QuarticSolution<ComplexDouble> from,
                                                               @NotNull QuarticSolution<ComplexFloat> into) {
        into.root1.set((float) from.root1.real, (float) from.root1.imag);
        into.root2.set((float) from.root2.real, (float) from.root2.imag);
        into.root3.set((float) from.root3.real, (float) from.root3.imag);
        into.root4.set((float) from.root4.real, (float) from.root4.imag);
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
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + e = 0}, stored in a {@link QuarticSolution}
     */
    public @NotNull QuarticSolution<ComplexFloat> solve(@NotNull ComplexFloat a, @NotNull ComplexFloat e) {
        solveInto(DOUBLE_ROOTS, copy(a, A), copy(e, E));
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + dx + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param d The coefficient of {@code x}
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + dx + e = 0}, stored in a {@link QuarticSolution}
     */
    public @NotNull QuarticSolution<ComplexFloat> solve(@NotNull ComplexFloat a, @NotNull ComplexFloat d,
                                                        @NotNull ComplexFloat e) {
        solveInto(DOUBLE_ROOTS, copy(a, A), ZERO, ZERO, copy(d, D), copy(e, E), W1, W2, W3, W4, W5, W6);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param c The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param d The coefficient of {@code x}
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}, stored in a {@link QuarticSolution}
     */
    public @NotNull QuarticSolution<ComplexFloat> solve(@NotNull ComplexFloat a, @NotNull ComplexFloat c,
                                                        @NotNull ComplexFloat d, @NotNull ComplexFloat e) {
        solveInto(DOUBLE_ROOTS, copy(a, A), ZERO, copy(c, C), copy(d, D), copy(e, E), W1, W2, W3, W4, W5, W6);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + bx}<sup>{@code 3}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param b The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param c The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param d The coefficient of {@code x}
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + bx}<sup>{@code 3}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}, stored in a {@link QuarticSolution}
     */
    public @NotNull QuarticSolution<ComplexFloat> solve(@NotNull ComplexFloat a, @NotNull ComplexFloat b,
                                                        @NotNull ComplexFloat c, @NotNull ComplexFloat d,
                                                        @NotNull ComplexFloat e) {
        solveInto(DOUBLE_ROOTS, copy(a, A), copy(b, B), copy(c, C), copy(d, D), copy(e, E), W1, W2, W3, W4, W5, W6);
        return copy(DOUBLE_ROOTS, FLOAT_ROOTS);
    }

    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + e = 0}, stored in a {@link QuarticSolution}
     */
    public @NotNull QuarticSolution<ComplexDouble> solve(@NotNull ComplexDouble a, @NotNull ComplexDouble e) {
        return solveInto(DOUBLE_ROOTS, a, e);
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + dx + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param d The coefficient of {@code x}
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + dx + e = 0}, stored in a {@link QuarticSolution}
     */
    public @NotNull QuarticSolution<ComplexDouble> solve(@NotNull ComplexDouble a, @NotNull ComplexDouble d,
                                                         @NotNull ComplexDouble e) {
        return solveInto(DOUBLE_ROOTS, a, ZERO, ZERO, d, e, W1, W2, W3, W4, W5, W6);
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param c The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param d The coefficient of {@code x}
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}, stored in a {@link QuarticSolution}
     */
    public @NotNull QuarticSolution<ComplexDouble> solve(@NotNull ComplexDouble a, @NotNull ComplexDouble c,
                                                         @NotNull ComplexDouble d, @NotNull ComplexDouble e) {
        return solveInto(DOUBLE_ROOTS, a, ZERO, c, d, e, W1, W2, W3, W4, W5, W6);
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + bx}<sup>{@code 3}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param b The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param c The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param d The coefficient of {@code x}
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + bx}<sup>{@code 3}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}, stored in a {@link QuarticSolution}
     */
    public @NotNull QuarticSolution<ComplexDouble> solve(@NotNull ComplexDouble a, @NotNull ComplexDouble b,
                                                         @NotNull ComplexDouble c, @NotNull ComplexDouble d,
                                                         @NotNull ComplexDouble e) {
        return solveInto(DOUBLE_ROOTS, a, b, c, d, e, W1, W2, W3, W4, W5, W6);
    }

    /**
     * Creates a new {@link ComplexDouble}.
     *
     * @return A new {@code ComplexDouble} instance
     */
    private static @NotNull ComplexDouble newCD() {
        return new ComplexDouble();
    }

    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + e = 0}, stored in a {@link QuarticSolution}
     */
    public static @NotNull QuarticSolution<ComplexFloat> solveQuartic(@NotNull ComplexFloat a, @NotNull ComplexFloat e) {
        QuarticSolution<ComplexDouble> roots = solveInto(
                newDoubleSolution(),
                copy(a, newCD()), copy(e, newCD())
        );
        return copy(roots, newFloatSolution());
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + dx + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param d The coefficient of {@code x}
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + dx + e = 0}, stored in a {@link QuarticSolution}
     */
    public static @NotNull QuarticSolution<ComplexFloat> solveQuartic(@NotNull ComplexFloat a, @NotNull ComplexFloat d,
                                                                      @NotNull ComplexFloat e) {
        QuarticSolution<ComplexDouble> roots = solveInto(
                newDoubleSolution(),
                copy(a, newCD()), ZERO, ZERO, copy(d, newCD()), copy(e, newCD()),
                newCD(), newCD(), newCD(), newCD(), newCD(), newCD()
        );
        return copy(roots, newFloatSolution());
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param c The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param d The coefficient of {@code x}
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}, stored in a {@link QuarticSolution}
     */
    public static @NotNull QuarticSolution<ComplexFloat> solveQuartic(@NotNull ComplexFloat a, @NotNull ComplexFloat c,
                                                                      @NotNull ComplexFloat d, @NotNull ComplexFloat e) {
        QuarticSolution<ComplexDouble> roots = solveInto(
                newDoubleSolution(),
                copy(a, newCD()), ZERO, copy(c, newCD()), copy(d, newCD()), copy(e, newCD()),
                newCD(), newCD(), newCD(), newCD(), newCD(), newCD()
        );
        return copy(roots, newFloatSolution());
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + bx}<sup>{@code 3}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param b The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param c The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param d The coefficient of {@code x}
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + bx}<sup>{@code 3}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}, stored in a {@link QuarticSolution}
     */
    public static @NotNull QuarticSolution<ComplexFloat> solveQuartic(@NotNull ComplexFloat a, @NotNull ComplexFloat b,
                                                                      @NotNull ComplexFloat c, @NotNull ComplexFloat d,
                                                                      @NotNull ComplexFloat e) {
        QuarticSolution<ComplexDouble> roots = solveInto(
                newDoubleSolution(),
                copy(a, newCD()), copy(b, newCD()), copy(c, newCD()), copy(d, newCD()), copy(e, newCD()),
                newCD(), newCD(), newCD(), newCD(), newCD(), newCD()
        );
        return copy(roots, newFloatSolution());
    }

    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + e = 0}, stored in a {@link QuarticSolution}
     */
    public static @NotNull QuarticSolution<ComplexDouble> solveQuartic(@NotNull ComplexDouble a, @NotNull ComplexDouble e) {
        return solveInto(newDoubleSolution(), a, e);
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + dx + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param d The coefficient of {@code x}
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + dx + e = 0}, stored in a {@link QuarticSolution}
     */
    public static @NotNull QuarticSolution<ComplexDouble> solveQuartic(@NotNull ComplexDouble a, @NotNull ComplexDouble d,
                                                                       @NotNull ComplexDouble e) {
        return solveInto(
                newDoubleSolution(),
                a, ZERO, ZERO, d, e,
                newCD(), newCD(), newCD(), newCD(), newCD(), newCD()
        );
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param c The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param d The coefficient of {@code x}
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}, stored in a {@link QuarticSolution}
     */
    public static @NotNull QuarticSolution<ComplexDouble> solveQuartic(@NotNull ComplexDouble a, @NotNull ComplexDouble c,
                                                                       @NotNull ComplexDouble d, @NotNull ComplexDouble e) {
        return solveInto(
                newDoubleSolution(),
                a, ZERO, c, d, e,
                newCD(), newCD(), newCD(), newCD(), newCD(), newCD()
        );
    }
    /**
     * Solves the cubic equation {@code ax}<sup>{@code 4}</sup> {@code + bx}<sup>{@code 3}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}.
     *
     * @param a The coefficient of {@code x}<sup>{@code 4}</sup>
     * @param b The coefficient of {@code x}<sup>{@code 3}</sup>
     * @param c The coefficient of {@code x}<sup>{@code 2}</sup>
     * @param d The coefficient of {@code x}
     * @param e The coefficient of {@code 1}
     * @return The roots of {@code ax}<sup>{@code 4}</sup> {@code + bx}<sup>{@code 3}</sup>
     * {@code + cx}<sup>{@code 2}</sup> {@code + dx + e = 0}, stored in a {@link QuarticSolution}
     */
    public static @NotNull QuarticSolution<ComplexDouble> solveQuartic(@NotNull ComplexDouble a, @NotNull ComplexDouble b,
                                                                       @NotNull ComplexDouble c, @NotNull ComplexDouble d,
                                                                       @NotNull ComplexDouble e) {
        return solveInto(
                newDoubleSolution(),
                a, b, c, d, e,
                newCD(), newCD(), newCD(), newCD(), newCD(), newCD()
        );
    }
}
