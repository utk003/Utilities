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

package io.github.utk003.util.math;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;

/**
 * A collection of useful math constants to very high precision.
 * <p>
 * Most of these constants are not provided by {@link Math}, and the few that are
 * are given to lower precision. Every constant provided by this class is accurate
 * to the 24th decimal place (ie rounded to the nearest {@code 10}<sup>{@code -24}</sup>).
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 20, 2021
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public abstract class Constants {
    private Constants() {
    }

    /**
     * The value of <a href="https://mathworld.wolfram.com/Pi.html" target="_top">{@code π}</a>.
     *
     * @see Math#PI
     */
    public static final double PI = 3.141592653589793238462643;
    /**
     * The value of {@code τ = 2 * π}.
     *
     * @see #PI
     */
    public static final double TAU = 6.283185307179586476925287;

    /**
     * The value of <a href="https://mathworld.wolfram.com/e.html" target="_top">{@code e}</a>
     * (also <em>Euler's Number</em>, <em>Napier's Constant</em>).
     *
     * @see Math#E
     */
    public static final double E = 2.718281828459045235360287;
    /**
     * The value of <a href="https://mathworld.wolfram.com/e.html" target="_top">{@code e}<sup>{@code 2}</sup></a>.
     *
     * @see #E
     */
    public static final double E_SQUARED = 7.389056098930650227230427;

    /**
     * The value of {@code √2} (also
     * <a href="https://mathworld.wolfram.com/PythagorassConstant.html" target="_top"><em>Pythagoras's Constant</em></a>).
     */
    public static final double SQRT_2 = 1.414213562373095048801689;
    /**
     * The value of {@code √3} (also
     * <a href="https://mathworld.wolfram.com/TheodorussConstant.html" target="_top"><em>Theodorus's Constant</em></a>).
     */
    public static final double SQRT_3 = 1.732050807568877293527446;
    /**
     * The value of {@code √5}.
     */
    public static final double SQRT_5 = 2.236067977499789696409174;
    /**
     * The value of {@code √7}.
     */
    public static final double SQRT_7 = 2.645751311064590590501616;

    /**
     * The value of {@code log}<sub>{@code 2}</sub>{@code 3}.
     */
    public static final double LOG2_3 = 1.584962500721156181453739;
    /**
     * The value of {@code log}<sub>{@code 2}</sub>{@code 5}.
     */
    public static final double LOG2_5 = 2.321928094887362347870319;
    /**
     * The value of {@code log}<sub>{@code 2}</sub>{@code 7}.
     */
    public static final double LOG2_7 = 2.807354922057604107441969;

    /**
     * The value of {@code ln2 = log}<sub>{@code e}</sub>{@code 2}.
     *
     * @see <a href="https://mathworld.wolfram.com/NaturalLogarithm.html" target="_top">Natural Logarithm</a>
     */
    public static final double LN_2 = 0.693147180559945309417232;
    /**
     * The value of {@code ln3 = log}<sub>{@code e}</sub>{@code 3}.
     *
     * @see <a href="https://mathworld.wolfram.com/NaturalLogarithm.html" target="_top">Natural Logarithm</a>
     */
    public static final double LN_3 = 1.098612288668109691395245;
    /**
     * The value of {@code ln5 = log}<sub>{@code e}</sub>{@code 5}.
     *
     * @see <a href="https://mathworld.wolfram.com/NaturalLogarithm.html" target="_top">Natural Logarithm</a>
     */
    public static final double LN_5 = 1.609437912434100374600759;
    /**
     * The value of {@code ln7 = log}<sub>{@code e}</sub>{@code 7}.
     *
     * @see <a href="https://mathworld.wolfram.com/NaturalLogarithm.html" target="_top">Natural Logarithm</a>
     */
    public static final double LN_7 = 1.945910149055313305105353;

    /**
     * The value of {@code log2 = log}<sub>{@code 10}</sub>{@code 2}.
     *
     * @see <a href="https://mathworld.wolfram.com/CommonLogarithm.html" target="_top">Common Logarithm</a>
     */
    public static final double LOG10_2 = 0.301029995663981195213739;
    /**
     * The value of {@code log3 = log}<sub>{@code 10}</sub>{@code 3}.
     *
     * @see <a href="https://mathworld.wolfram.com/CommonLogarithm.html" target="_top">Common Logarithm</a>
     */
    public static final double LOG10_3 = 0.477121254719662437295028;
    /**
     * The value of {@code log5 = log}<sub>{@code 10}</sub>{@code 5}.
     *
     * @see <a href="https://mathworld.wolfram.com/CommonLogarithm.html" target="_top">Common Logarithm</a>
     */
    public static final double LOG10_5 = 0.698970004336018804786261;
    /**
     * The value of {@code log7 = log}<sub>{@code 10}</sub>{@code 7}.
     *
     * @see <a href="https://mathworld.wolfram.com/CommonLogarithm.html" target="_top">Common Logarithm</a>
     */
    public static final double LOG10_7 = 0.845098040014256830712216;
}
