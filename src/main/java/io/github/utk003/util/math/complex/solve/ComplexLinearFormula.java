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
import io.github.utk003.util.math.complex.ComplexNumber;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A <a href="https://mathworld.wolfram.com/LinearEquation.html" target="_top">linear equation</a> solver.
 * <p>
 * This class is for equations whose coefficients are complex. For a specialized solver
 * that only works with real coefficients, use {@link io.github.utk003.util.math.solve.LinearFormula}.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 21, 2021
 * @see io.github.utk003.util.math.solve.LinearFormula
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public final class ComplexLinearFormula {
    /**
     * A cached instance of {@link ComplexFloat}.
     */
    private final @NotNull ComplexFloat FLOAT_SOLUTION = new ComplexFloat();
    /**
     * A cached instance of {@link ComplexDouble}.
     */
    private final @NotNull ComplexDouble DOUBLE_SOLUTION = new ComplexDouble();

    /**
     * Solves the linear equation {@code ax + b = 0}.
     *
     * @param <CN> The type of {@code into}
     * @param into The {@code ComplexNumber} to store the root into
     * @param a    The coefficient of {@code x}
     * @param b    The coefficient of {@code 1}
     * @return The root of {@code ax + b = 0} (stored in {@code into})
     */
    @Contract("_, _, _ -> param1")
    public static <CN extends ComplexNumber<CN>> @NotNull CN solveInto(@NotNull CN into, @NotNull CN a, @NotNull CN b) {
        // ax + b = 0 --> x = -b / a
        return into.copy(b).negate().divide(a);
    }

    /**
     * Solves the linear equation {@code ax + b = 0}.
     *
     * @param a The coefficient of {@code x}
     * @param b The coefficient of {@code 1}
     * @return The root of {@code ax + b = 0}
     */
    public @NotNull ComplexFloat solve(@NotNull ComplexFloat a, @NotNull ComplexFloat b) {
        return solveInto(FLOAT_SOLUTION, a, b);
    }
    /**
     * Solves the linear equation {@code ax + b = 0}.
     *
     * @param a The coefficient of {@code x}
     * @param b The coefficient of {@code 1}
     * @return The root of {@code ax + b = 0}
     */
    public @NotNull ComplexDouble solve(@NotNull ComplexDouble a, @NotNull ComplexDouble b) {
        return solveInto(DOUBLE_SOLUTION, a, b);
    }

    /**
     * Solves the linear equation {@code ax + b = 0}.
     *
     * @param a The coefficient of {@code x}
     * @param b The coefficient of {@code 1}
     * @return The root of {@code ax + b = 0}
     */
    public static @NotNull ComplexFloat solveLinear(@NotNull ComplexFloat a, @NotNull ComplexFloat b) {
        return solveInto(new ComplexFloat(), a, b);
    }
    /**
     * Solves the linear equation {@code ax + b = 0}.
     *
     * @param a The coefficient of {@code x}
     * @param b The coefficient of {@code 1}
     * @return The root of {@code ax + b = 0}
     */
    public static @NotNull ComplexDouble solveLinear(@NotNull ComplexDouble a, @NotNull ComplexDouble b) {
        return solveInto(new ComplexDouble(), a, b);
    }
}
