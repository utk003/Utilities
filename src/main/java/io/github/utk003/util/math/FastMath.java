package io.github.utk003.util.math;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;

/**
 * A collection of simple math operations, optimized using heuristics
 * or alternative calculation methods to provide <em>approximate</em>
 * answers at a fraction of the computation time required by {@link Math}.
 * <p>
 * The values returned by this class may not be <em>exactly</em> accurate, but they will
 * be "close enough" and deterministic, so they can be used in situations where accuracy
 * is secondary (but still important) to speed and repeatability (such as in
 * {@linkplain io.github.utk003.util.math.noise noise generation}).
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 20, 2021
 * @since 3.0.0
 */
@SuppressWarnings("ManualMinMaxCalculation")
@ScheduledForRelease(inVersion = "v3.0.0")
public abstract class FastMath {
    private FastMath() {
    }

    /**
     * Returns the floor of the given {@code float}.
     * <p>
     * If the floor doesn't fit into an {@code int},
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param f The number to take the floor of
     * @return The floored value, as a fixed-point {@code int}
     */
    public static int floor(float f) {
        int i = (int) f;
        return i > f ? i - 1 : i;
    }
    /**
     * Returns the floor of the given {@code double}.
     * <p>
     * If the floor doesn't fit into a {@code long},
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param d The number to take the floor of
     * @return The floored value, as a fixed-point {@code long}
     */
    public static long floor(double d) {
        long l = (long) d;
        return l > d ? l - 1 : l;
    }

    /**
     * Rounds the given {@code float} to the nearest fixed-point number.
     * <p>
     * If the result doesn't fit into an {@code int},
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param f The number to round
     * @return The rounded result, as a fixed-point {@code int}
     */
    public static int round(float f) {
        return floor(f + 0.5f);
    }
    /**
     * Rounds the given {@code double} to the nearest fixed-point number.
     * <p>
     * If the result doesn't fit into a {@code long},
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param d The number to round
     * @return The rounded result, as a fixed-point {@code long}
     */
    public static long round(double d) {
        return floor(d + 0.5);
    }

    /**
     * Rounds the given {@code float} rounded to the nearest fixed-point number,
     * if it lies within a certain threshold of that resulting number.
     * <p>
     * If the number cannot be rounded, the input value
     * {@code val} is returned directly.
     * <p>
     * If the result doesn't fit into an {@code int},
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param val       The number to round
     * @param threshold The maximum amount to round up or down
     * @return The rounded result, as a {@code float}
     */
    public static float round(float val, float threshold) {
        int round = round(val);
        return Math.abs(val - round) < threshold ? round : val;
    }
    /**
     * Rounds the given {@code double} rounded to the nearest fixed-point number,
     * if it lies within a certain threshold of that resulting number.
     * <p>
     * If the number cannot be rounded, the input value
     * {@code val} is returned directly.
     * <p>
     * If the result doesn't fit into an {@code long},
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param val       The number to round
     * @param threshold The maximum amount to round up or down
     * @return The rounded result, as a {@code double}
     */
    public static double round(double val, double threshold) {
        long round = round(val);
        return Math.abs(val - round) < threshold ? round : val;
    }

    /**
     * Returns the ceiling of the given {@code float}.
     * <p>
     * If the ceiling doesn't fit into an {@code int},
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param f The number to take the ceiling of
     * @return The ceiling value, as a fixed-point {@code int}
     */
    public static int ceil(float f) {
        int i = (int) f;
        return i < f ? i + 1 : i;
    }
    /**
     * Returns the ceiling of the given {@code double}.
     * <p>
     * If the ceiling doesn't fit into an {@code long},
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param d The number to take the ceiling of
     * @return The ceiling value, as a fixed-point {@code long}
     */
    public static long ceil(double d) {
        long l = (long) d;
        return l < d ? l + 1 : l;
    }

    /**
     * Limits the given value to be between the given bounds via <em>clamping</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the "excess" is truncated. In
     * other words, {@code val} is either rounded up to {@code low}
     * or rounded down to {@code high}.
     * <p>
     * If {@code high &gt;= low}, then
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param val  The value to clamp
     * @param low  The lower bound of the clamp operation
     * @param high The upper bound of the clamp operation
     * @return The clamped value
     */
    public static byte clamp(byte val, byte low, byte high) {
        return val <= low ? low : val >= high ? high : val;
    }
    /**
     * Limits the given value to be between the given bounds via <em>clamping</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the "excess" is truncated. In
     * other words, {@code val} is either rounded up to {@code low}
     * or rounded down to {@code high}.
     * <p>
     * If {@code high &gt;= low}, then
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param val  The value to clamp
     * @param low  The lower bound of the clamp operation
     * @param high The upper bound of the clamp operation
     * @return The clamped value
     */
    public static short clamp(short val, short low, short high) {
        return val <= low ? low : val >= high ? high : val;
    }
    /**
     * Limits the given value to be between the given bounds via <em>clamping</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the "excess" is truncated. In
     * other words, {@code val} is either rounded up to {@code low}
     * or rounded down to {@code high}.
     * <p>
     * If {@code high &gt;= low}, then
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param val  The value to clamp
     * @param low  The lower bound of the clamp operation
     * @param high The upper bound of the clamp operation
     * @return The clamped value
     */
    public static int clamp(int val, int low, int high) {
        return val <= low ? low : val >= high ? high : val;
    }
    /**
     * Limits the given value to be between the given bounds via <em>clamping</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the "excess" is truncated. In
     * other words, {@code val} is either rounded up to {@code low}
     * or rounded down to {@code high}.
     * <p>
     * If {@code high &gt;= low}, then
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param val  The value to clamp
     * @param low  The lower bound of the clamp operation
     * @param high The upper bound of the clamp operation
     * @return The clamped value
     */
    public static long clamp(long val, long low, long high) {
        return val <= low ? low : val >= high ? high : val;
    }
    /**
     * Limits the given value to be between the given bounds via <em>clamping</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the "excess" is truncated. In
     * other words, {@code val} is either rounded up to {@code low}
     * or rounded down to {@code high}.
     * <p>
     * If {@code high &gt;= low}, then
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param val  The value to clamp
     * @param low  The lower bound of the clamp operation
     * @param high The upper bound of the clamp operation
     * @return The clamped value
     */
    public static float clamp(float val, float low, float high) {
        return val <= low ? low : val >= high ? high : val;
    }
    /**
     * Limits the given value to be between the given bounds via <em>clamping</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the "excess" is truncated. In
     * other words, {@code val} is either rounded up to {@code low}
     * or rounded down to {@code high}.
     * <p>
     * If {@code high &gt;= low}, then
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param val  The value to clamp
     * @param low  The lower bound of the clamp operation
     * @param high The upper bound of the clamp operation
     * @return The clamped value
     */
    public static double clamp(double val, double low, double high) {
        return val <= low ? low : val >= high ? high : val;
    }

    /**
     * Limits the given value to be between the given bounds via <em>modular rotation</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the value is "wrapped" around
     * the region until it lies within the interval. For example,
     * {@code shift(10, 2, 7)} returns {@code 5}, as the {@code 10}
     * has an "excess" of {@code 3 = 10 - 7}, which then "wraps" around
     * from the {@code 2}, resulting in {@code 5 = 2 + 3}.
     * <p>
     * This method is equivalent to calling {@code shift(val, 0, high)}.
     *
     * @param val  The value to shift into the region
     * @param high The upper bound of the shift operation
     * @return The shifted value
     * @see #shift(int, int, int)
     */
    public static int shift(int val, int high) {
        return (val % high + high) % high;
    }
    /**
     * Limits the given value to be between the given bounds via <em>modular rotation</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the value is "wrapped" around
     * the region until it lies within the interval. For example,
     * {@code shift(10, 2, 7)} returns {@code 5}, as the {@code 10}
     * has an "excess" of {@code 3 = 10 - 7}, which then "wraps" around
     * from the {@code 2}, resulting in {@code 5 = 2 + 3}.
     * <p>
     * This method is equivalent to calling {@code shift(val, 0, high)}.
     *
     * @param val  The value to shift into the region
     * @param high The upper bound of the shift operation
     * @return The shifted value
     * @see #shift(long, long, long)
     */
    public static long shift(long val, long high) {
        return (val % high + high) % high;
    }
    /**
     * Limits the given value to be between the given bounds via <em>modular rotation</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the value is "wrapped" around
     * the region until it lies within the interval. For example,
     * {@code shift(10, 2, 7)} returns {@code 5}, as the {@code 10}
     * has an "excess" of {@code 3 = 10 - 7}, which then "wraps" around
     * from the {@code 2}, resulting in {@code 5 = 2 + 3}.
     * <p>
     * This method is equivalent to calling {@code shift(val, 0, high)}.
     *
     * @param val  The value to shift into the region
     * @param high The upper bound of the shift operation
     * @return The shifted value
     * @see #shift(float, float, float)
     */
    public static float shift(float val, float high) {
        return (val % high + high) % high;
    }
    /**
     * Limits the given value to be between the given bounds via <em>modular rotation</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the value is "wrapped" around
     * the region until it lies within the interval. For example,
     * {@code shift(10, 2, 7)} returns {@code 5}, as the {@code 10}
     * has an "excess" of {@code 3 = 10 - 7}, which then "wraps" around
     * from the {@code 2}, resulting in {@code 5 = 2 + 3}.
     * <p>
     * This method is equivalent to calling {@code shift(val, 0, high)}.
     *
     * @param val  The value to shift into the region
     * @param high The upper bound of the shift operation
     * @return The shifted value
     * @see #shift(double, double, double)
     */
    public static double shift(double val, double high) {
        return (val % high + high) % high;
    }

    /**
     * Limits the given value to be between the given bounds via <em>modular rotation</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the value is "wrapped" around
     * the region until it lies within the interval. For example,
     * {@code shift(10, 2, 7)} returns {@code 5}, as the {@code 10}
     * has an "excess" of {@code 3 = 10 - 7}, which then "wraps" around
     * from the {@code 2}, resulting in {@code 5 = 2 + 3}.
     * <p>
     * If {@code high &gt;= low}, then
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param val  The value to shift into the region
     * @param low  The lower bound of the shift operation
     * @param high The upper bound of the shift operation
     * @return The shifted value
     * @see #shift(int, int)
     */
    public static int shift(int val, int low, int high) {
        return shift(val - low, high - low) + low;
    }
    /**
     * Limits the given value to be between the given bounds via <em>modular rotation</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the value is "wrapped" around
     * the region until it lies within the interval. For example,
     * {@code shift(10, 2, 7)} returns {@code 5}, as the {@code 10}
     * has an "excess" of {@code 3 = 10 - 7}, which then "wraps" around
     * from the {@code 2}, resulting in {@code 5 = 2 + 3}.
     * <p>
     * If {@code high &gt;= low}, then
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param val  The value to shift into the region
     * @param low  The lower bound of the shift operation
     * @param high The upper bound of the shift operation
     * @return The shifted value
     * @see #shift(long, long)
     */
    public static long shift(long val, long low, long high) {
        return shift(val - low, high - low) + low;
    }
    /**
     * Limits the given value to be between the given bounds via <em>modular rotation</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the value is "wrapped" around
     * the region until it lies within the interval. For example,
     * {@code shift(10, 2, 7)} returns {@code 5}, as the {@code 10}
     * has an "excess" of {@code 3 = 10 - 7}, which then "wraps" around
     * from the {@code 2}, resulting in {@code 5 = 2 + 3}.
     * <p>
     * If {@code high &gt;= low}, then
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param val  The value to shift into the region
     * @param low  The lower bound of the shift operation
     * @param high The upper bound of the shift operation
     * @return The shifted value
     * @see #shift(float, float)
     */
    public static float shift(float val, float low, float high) {
        return shift(val - low, high - low) + low;
    }
    /**
     * Limits the given value to be between the given bounds via <em>modular rotation</em>.
     * <p>
     * If the given value {@code val} lies outside the interval
     * {@code [low,high]}, then the value is "wrapped" around
     * the region until it lies within the interval. For example,
     * {@code shift(10, 2, 7)} returns {@code 5}, as the {@code 10}
     * has an "excess" of {@code 3 = 10 - 7}, which then "wraps" around
     * from the {@code 2}, resulting in {@code 5 = 2 + 3}.
     * <p>
     * If {@code high &gt;= low}, then
     * the value returned by this method <em>may</em>
     * not match the expected value.
     *
     * @param val  The value to shift into the region
     * @param low  The lower bound of the shift operation
     * @param high The upper bound of the shift operation
     * @return The shifted value
     * @see #shift(double, double)
     */
    public static double shift(double val, double low, double high) {
        return shift(val - low, high - low) + low;
    }
}
