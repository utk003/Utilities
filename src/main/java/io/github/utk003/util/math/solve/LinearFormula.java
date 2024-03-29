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

import io.github.utk003.util.math.complex.solve.ComplexLinearFormula;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;

/**
 * A <a href="https://mathworld.wolfram.com/LinearEquation.html" target="_top">linear equation</a> solver.
 * <p>
 * This class is only for equations whose coefficients are all real. For a similar solver
 * that works with complex coefficients, use {@link ComplexLinearFormula}.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 20, 2021
 * @see ComplexLinearFormula
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public final class LinearFormula {
    /**
     * Solves the linear equation {@code ax + b = 0}.
     *
     * @param a The coefficient of {@code x}
     * @param b The coefficient of {@code 1}
     * @return The root of {@code ax + b = 0}
     */
    public float solve(float a, float b) {
        // ax + b = 0 --> x = -b / a
        return -b / a;
    }
    /**
     * Solves the linear equation {@code ax + b = 0}.
     *
     * @param a The coefficient of {@code x}
     * @param b The coefficient of {@code 1}
     * @return The root of {@code ax + b = 0}
     */
    public double solve(double a, double b) {
        // ax + b = 0 --> x = -b / a
        return -b / a;
    }

    /**
     * Solves the linear equation {@code ax + b = 0}.
     *
     * @param a The coefficient of {@code x}
     * @param b The coefficient of {@code 1}
     * @return The root of {@code ax + b = 0}
     */
    public static float solveLinear(float a, float b) {
        // ax + b = 0 --> x = -b / a
        return -b / a;
    }
    /**
     * Solves the linear equation {@code ax + b = 0}.
     *
     * @param a The coefficient of {@code x}
     * @param b The coefficient of {@code 1}
     * @return The root of {@code ax + b = 0}
     */
    public static double solveLinear(double a, double b) {
        // ax + b = 0 --> x = -b / a
        return -b / a;
    }
}
