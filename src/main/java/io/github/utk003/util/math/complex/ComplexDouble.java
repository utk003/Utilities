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

package io.github.utk003.util.math.complex;

import io.github.utk003.util.math.FastMath;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static io.github.utk003.util.math.Constants.PI;
import static io.github.utk003.util.math.Constants.TAU;

/**
 * A {@code double}-based implementation of {@link ComplexNumber}.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 22, 2021
 * @see ComplexFloat
 * @since 3.0.0
 */
@SuppressWarnings("UnusedReturnValue")
@ScheduledForRelease(inVersion = "v3.0.0")
public final class ComplexDouble implements ComplexNumber<ComplexDouble> {
    /**
     * The "zero" {@code ComplexDouble} (ie the {@code ComplexDouble} corresponding to 0).
     */
    private static final @NotNull ComplexDouble ZERO = new ComplexDouble(0.0, 0.0);
    /**
     * The "identity" {@code ComplexDouble} (ie the {@code ComplexDouble} corresponding to 1).
     */
    private static final @NotNull ComplexDouble IDENTITY = new ComplexDouble();
    /**
     * An array cache of the 24<sup>th</sup> roots of unity.
     */
    private static final @NotNull ComplexDouble[] ROOTS_OF_UNITY_24 = new ComplexDouble[24];
    static {
        ROOTS_OF_UNITY_24[0] = IDENTITY;
        ROOTS_OF_UNITY_24[1] = rootOfUnity(24);
        for (int i = 2; i < 24; i++)
            ROOTS_OF_UNITY_24[i] = new ComplexDouble().copy(ROOTS_OF_UNITY_24[i - 1]).multiply(ROOTS_OF_UNITY_24[1]);
    }

    /**
     * Returns a new {@code ComplexDouble} corresponding to
     * the "first" primitive root of unity of order {@code n}.
     * In other words, this method returns {@code e}<sup>{@code 2πi/n}</sup>.
     *
     * @param n The order of the root of unity to provide
     * @return The "first" primitive root of unity of order {@code n}
     */
    public static @NotNull ComplexDouble rootOfUnity(int n) {
        return new ComplexDouble().rotate(TAU / n);
    }

    /**
     * The real component of this complex number
     */
    public double real;
    /**
     * The imaginary component of this complex number
     */
    public double imag;

    /**
     * The magnitude of this complex number, or, equivalently,
     * the length of this number interpreted as a vector.
     * <p>
     * If {@link #real} or {@link #imag} is modified directly,
     * this field may become incorrect. In such a scenario,
     * {@link #updateMembers()} <em>must</em> be called
     * before any other methods from this class are called,
     * otherwise the resulting complex numbers may be inaccurate
     * as well.
     *
     * @see #real
     * @see #imag
     * @see #updateMembers()
     */
    public double length;
    /**
     * The magnitude of this complex number, squared.
     * <p>
     * This field exists solely for convenience.
     * <p>
     * If {@link #real} or {@link #imag} is modified directly,
     * this field may become incorrect. In such a scenario,
     * {@link #updateMembers()} <em>must</em> be called
     * before any other methods from this class are called,
     * otherwise the resulting complex numbers may be inaccurate
     * as well.
     *
     * @see #real
     * @see #imag
     * @see #updateMembers()
     */
    public double lengthSquared;
    /**
     * The argument of this complex number, or, in other words,
     * the angle this number (as a vector) makes with the
     * positive-real axis on the complex coordinate plane.
     * <p>
     * If {@link #real} or {@link #imag} is modified directly,
     * this field may become incorrect. In such a scenario,
     * {@link #updateMembers()} <em>must</em> be called
     * before any other methods from this class are called,
     * otherwise the resulting complex numbers may be inaccurate
     * as well.
     *
     * @see #real
     * @see #imag
     * @see #updateMembers()
     */
    public double arg;

    /**
     * Constructs a new {@code ComplexDouble} corresponding to the real number {@code 1}.
     * <p>
     * This constructor is an optimized way of calling <pre>new ComplexDouble(1.0, 0.0).</pre>
     */
    public ComplexDouble() {
        real = 1.0;
        imag = 0.0;

        length = lengthSquared = 1.0;
        arg = 0.0;
    }
    /**
     * Constructs a new {@code ComplexDouble} with the given
     * real and imaginary components.
     *
     * @param real      The complex number's real part
     * @param imaginary The complex number's imaginary part
     */
    public ComplexDouble(double real, double imaginary) {
        this.real = real;
        imag = imaginary;

        updateMembers();
    }

    /**
     * Returns the given argument, shifted to be between {@code 0} and {@code 2π} radians.
     *
     * @param arg The angle to shift
     * @return The shifted angle
     */
    private static double adjustAngle(double arg) {
        return FastMath.shift(arg, TAU);
    }

    /**
     * Sets this {@code ComplexDouble}'s real component to the specified value.
     *
     * @param real The complex number's new real part
     * @return This {@code ComplexDouble}, after the value change
     */
    @Contract("_ -> this")
    public @NotNull ComplexDouble setReal(double real) {
        this.real = real;
        return updateMembers();
    }
    /**
     * Sets this {@code ComplexDouble}'s imaginary component to the specified value.
     *
     * @param imag The complex number's new imaginary part
     * @return This {@code ComplexDouble}, after the value change
     */
    @Contract("_ -> this")
    public @NotNull ComplexDouble setImaginary(double imag) {
        this.imag = imag;
        return updateMembers();
    }

    /**
     * Sets this {@code ComplexDouble}'s real and imaginary
     * components to the specified values.
     *
     * @param real The complex number's new real part
     * @param imag The complex number's new imaginary part
     * @return This {@code ComplexDouble}, after the value changes
     */
    @Contract("_,_ -> this")
    public @NotNull ComplexDouble set(double real, double imag) {
        this.real = real;
        this.imag = imag;
        return updateMembers();
    }

    /**
     * Update this {@code ComplexDouble}'s instance variables
     * to be in sync with each other.
     * <p>
     * This method is primarily for use after a {@code ComplexDouble}
     * is modified directly via its instance vars {@link #real} and
     * {@link #imag}. In such a scenario, calling this method will
     * force the {@code ComplexDouble} to update its values of
     * {@link #length}, {@link #lengthSquared}, and {@link #arg}
     * to correspond to the same imaginary number represented by
     * {@code real} and {@code imag}.
     *
     * @return This {@code ComplexDouble}, after the forced update
     * @see #real
     * @see #imag
     * @see #length
     * @see #lengthSquared
     * @see #arg
     */
    @Contract("-> this")
    public @NotNull ComplexDouble updateMembers() {
        lengthSquared = real * real + imag * imag;
        length = Math.sqrt(lengthSquared);
        arg = adjustAngle(Math.atan2(imag, real)); // y, x = imag, real
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_ -> this")
    public @NotNull ComplexDouble copy(@NotNull ComplexDouble cd) {
        arg = cd.arg;
        length = cd.length;
        lengthSquared = cd.lengthSquared;

        real = cd.real;
        imag = cd.imag;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return lengthSquared == 0.0;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReal() {
        return imag == 0.0;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isImaginary() {
        return real == 0.0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble zero() {
        return copy(ZERO);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble identity() {
        return copy(IDENTITY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_ -> this")
    public @NotNull ComplexDouble multiply(int n) {
        return n == 1 ? this : scale(n);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_ -> this")
    public @NotNull ComplexDouble multiply(long n) {
        return n == 1 ? this : scale(n);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_ -> this")
    public @NotNull ComplexDouble divide(int n) {
        if (n == 0)
            throw new ArithmeticException("Division by 0");
        return n == 1 ? this : scale(1.0 / n);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_ -> this")
    public @NotNull ComplexDouble divide(long n) {
        if (n == 0)
            throw new ArithmeticException("Division by 0");
        return n == 1 ? this : scale(1.0 / n);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_,_ -> this")
    public @NotNull ComplexDouble multiply(int p, int q) {
        if (q == 0) {
            if (p != 0)
                throw new ArithmeticException("Division by 0");
            // 0 / 0 = 1
            return this;
        }
        return p == q ? this : scale((double) p / q);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_,_ -> this")
    public @NotNull ComplexDouble multiply(long p, long q) {
        if (q == 0) {
            if (p != 0)
                throw new ArithmeticException("Division by 0");
            // 0 / 0 = 1
            return this;
        }
        return p == q ? this : scale((double) p / q);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation rounds with a threshold of
     * {@code 10}<sup>{@code -10}</sup>.
     *
     * @see #round(double)
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble round() {
        return round(1e-10);
    }
    /**
     * Independently rounds the real and imaginary components of this {@code ComplexDouble}
     * if each is within a specified threshold of an integer.
     * <p>
     * The method {@link #round()} is equivalent to {@code round(1e-10)}.
     *
     * @param threshold The rounding threshold
     * @return This {@code ComplexDouble}, after rounding
     * @see #round()
     */
    @Contract("_ -> this")
    public @NotNull ComplexDouble round(double threshold) {
        real = FastMath.round(real, threshold);
        imag = FastMath.round(imag, threshold);
        return updateMembers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble normalize() {
        real /= length;
        imag /= length;
        length = lengthSquared = 1.0;
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble negate() {
        real = -real;
        imag = -imag;
        arg = adjustAngle(arg + PI);
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble conjugate() {
        imag = -imag;            // flip imag
        arg = adjustAngle(-arg); // flip arg
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble invert() {
        arg = adjustAngle(-arg);
        real /= lengthSquared;
        imag /= -lengthSquared; // negate imag (see #conjugate())
        length = 1.0 / length;
        lengthSquared = 1.0 / lengthSquared;
        return this;
    }

    /**
     * Scale this {@code ComplexDouble} up (or down) by the given factor.
     * <p>
     * If the given scaling factor is negative, this {@code ComplexDouble}'s
     * length and angle will be changed appropriately to ensure that its
     * length remains positive.
     *
     * @param scaleFactor The factor to scale this {@code ComplexDouble} by
     * @return This {@code ComplexDouble}, after the scaling
     */
    @Contract("_ -> this")
    public @NotNull ComplexDouble scale(double scaleFactor) {
        real *= scaleFactor;
        imag *= scaleFactor;

        if (scaleFactor < 0.0) {
            scaleFactor = -scaleFactor;
            arg = adjustAngle(arg + PI);
        }

        length *= scaleFactor;
        lengthSquared *= scaleFactor * scaleFactor;
        return this;
    }
    /**
     * Rotates this {@code ComplexDouble} counter-clockwise by the specified angle.
     * <p>
     * For angles that are multiples of {@code 15} degrees (or equivalently
     * {@code π/12} radians), use the provided specialized methods:
     * {@link #ccw90()}, {@link #ccw120()},
     * and {@link #rot180()}, to name a few.
     *
     * @param angle The angle to rotate this {@code ComplexDouble} by
     * @return This {@code ComplexDouble}, after the rotation
     * @see #ccw90()
     * @see #ccw120()
     * @see #rot180()
     */
    @Contract("_ -> this")
    public @NotNull ComplexDouble rotate(double angle) {
        arg = adjustAngle(arg + angle);
        real = length * Math.cos(arg);
        imag = length * Math.sin(arg);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_ -> this")
    public @NotNull ComplexDouble add(@NotNull ComplexDouble cd) {
        real += cd.real;
        imag += cd.imag;
        return updateMembers();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_ -> this")
    public @NotNull ComplexDouble subtract(@NotNull ComplexDouble cd) {
        real -= cd.real;
        imag -= cd.imag;
        return updateMembers();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_ -> this")
    public @NotNull ComplexDouble multiply(@NotNull ComplexDouble cd) {
        arg = adjustAngle(arg + cd.arg);
        length *= cd.length;
        lengthSquared *= cd.lengthSquared;

        double a = real, b = imag, c = cd.real, d = cd.imag;
        // (a + bi)(c + di) = (ac-bd) + (ad+bc)i
        real = a * c - b * d;
        imag = a * d + b * c;
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_ -> this")
    public @NotNull ComplexDouble divide(@NotNull ComplexDouble cd) {
        arg = adjustAngle(arg - cd.arg);
        length /= cd.length;
        lengthSquared /= cd.lengthSquared;

        double a = real, b = imag, c = cd.real, d = cd.imag;
        // (a + bi)/(c + di) = (a + bi)(c - di)/(c^2+d^2) = [(ac+bd) + (bc-ad)i] / (c^2+d^2)
        real = (a * c + b * d) / cd.lengthSquared;
        imag = (b * c - a * d) / cd.lengthSquared;
        return this;
    }

    /**
     * Raise this {@code ComplexDouble} to the given power.
     * <p>
     * For squares, cubes, square roots, and cube roots,
     * use the dedicated methods {@link #square()},
     * {@link #cube()}, {@link #sqrt()},
     * and {@link #cbrt()}, which have been optimized
     * to be much more efficient than this general method.
     *
     * @param exponent The power to raise this {@code ComplexDouble} to
     * @return This {@code ComplexDouble}, after the exponentiation
     * @see #square()
     * @see #cube()
     * @see #sqrt()
     * @see #cbrt()
     */
    @Contract("_ -> this")
    public @NotNull ComplexDouble pow(double exponent) {
        arg = adjustAngle(arg * exponent);
        length = Math.pow(length, exponent);
        lengthSquared = length * length;
        real = length * Math.cos(arg);
        imag = length * Math.sin(arg);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble sqrt() {
        arg = adjustAngle(arg / 2.0);
        lengthSquared = length;
        length = Math.sqrt(length);
        real = length * Math.cos(arg);
        imag = length * Math.sin(arg);
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cbrt() {
        arg = adjustAngle(arg / 3.0);
        length = Math.cbrt(length);
        lengthSquared = length * length;
        real = length * Math.cos(arg);
        imag = length * Math.sin(arg);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble square() {
        arg = adjustAngle(arg * 2.0);
        length = lengthSquared;
        lengthSquared = length * length;

        double a = real, b = imag;
        // (a + bi)^2 = a^2-b^2 + 2abi
        real = a * a - b * b;
        imag = 2 * a * b;
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cube() {
        arg = adjustAngle(arg * 3.0);
        length = length * lengthSquared;
        lengthSquared = length * length;

        double a = real, b = imag, aa = a * a, bb = b * b;
        // (a + bi)^3 = (a^2-b^2 + 2abi)(a + bi)  = (a^3-3ab^2) + (3a^2b-b^3)i
        real = a * (aa - 3 * bb); // a * aa - 3 * a * bb
        imag = (3 * aa - bb) * b; // 3 * aa * b - bb * b
        return this;
    }

    /*
     * Replace self
     */

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity15() {
        return copy(ROOTS_OF_UNITY_24[1]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity30() {
        return copy(ROOTS_OF_UNITY_24[2]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity45() {
        return copy(ROOTS_OF_UNITY_24[3]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity60() {
        return copy(ROOTS_OF_UNITY_24[4]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity75() {
        return copy(ROOTS_OF_UNITY_24[5]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity90() {
        return copy(ROOTS_OF_UNITY_24[6]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity105() {
        return copy(ROOTS_OF_UNITY_24[7]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity120() {
        return copy(ROOTS_OF_UNITY_24[8]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity135() {
        return copy(ROOTS_OF_UNITY_24[9]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity150() {
        return copy(ROOTS_OF_UNITY_24[10]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity165() {
        return copy(ROOTS_OF_UNITY_24[11]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity180() {
        return copy(ROOTS_OF_UNITY_24[12]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity195() {
        return copy(ROOTS_OF_UNITY_24[13]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity210() {
        return copy(ROOTS_OF_UNITY_24[14]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity225() {
        return copy(ROOTS_OF_UNITY_24[15]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity240() {
        return copy(ROOTS_OF_UNITY_24[16]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity255() {
        return copy(ROOTS_OF_UNITY_24[17]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity270() {
        return copy(ROOTS_OF_UNITY_24[18]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity285() {
        return copy(ROOTS_OF_UNITY_24[19]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity300() {
        return copy(ROOTS_OF_UNITY_24[20]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity315() {
        return copy(ROOTS_OF_UNITY_24[21]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity330() {
        return copy(ROOTS_OF_UNITY_24[22]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble unity345() {
        return copy(ROOTS_OF_UNITY_24[23]);
    }

    /*
     * Rotate self
     */

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw15() {
        return multiply(ROOTS_OF_UNITY_24[1]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw30() {
        return multiply(ROOTS_OF_UNITY_24[2]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw45() {
        return multiply(ROOTS_OF_UNITY_24[3]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw60() {
        return multiply(ROOTS_OF_UNITY_24[4]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw75() {
        return multiply(ROOTS_OF_UNITY_24[5]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw90() {
        return multiply(ROOTS_OF_UNITY_24[6]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw105() {
        return multiply(ROOTS_OF_UNITY_24[7]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw120() {
        return multiply(ROOTS_OF_UNITY_24[8]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw135() {
        return multiply(ROOTS_OF_UNITY_24[9]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw150() {
        return multiply(ROOTS_OF_UNITY_24[10]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw165() {
        return multiply(ROOTS_OF_UNITY_24[11]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw180() {
        return negate();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble rot180() {
        return negate();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cw180() {
        return negate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cw165() {
        return multiply(ROOTS_OF_UNITY_24[13]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cw150() {
        return multiply(ROOTS_OF_UNITY_24[14]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cw135() {
        return multiply(ROOTS_OF_UNITY_24[15]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cw120() {
        return multiply(ROOTS_OF_UNITY_24[16]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cw105() {
        return multiply(ROOTS_OF_UNITY_24[17]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw195() {
        return multiply(ROOTS_OF_UNITY_24[13]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw210() {
        return multiply(ROOTS_OF_UNITY_24[14]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw225() {
        return multiply(ROOTS_OF_UNITY_24[15]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw240() {
        return multiply(ROOTS_OF_UNITY_24[16]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw255() {
        return multiply(ROOTS_OF_UNITY_24[17]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cw90() {
        return multiply(ROOTS_OF_UNITY_24[18]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw270() {
        return multiply(ROOTS_OF_UNITY_24[18]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cw75() {
        return multiply(ROOTS_OF_UNITY_24[19]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cw60() {
        return multiply(ROOTS_OF_UNITY_24[20]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cw45() {
        return multiply(ROOTS_OF_UNITY_24[21]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cw30() {
        return multiply(ROOTS_OF_UNITY_24[22]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble cw15() {
        return multiply(ROOTS_OF_UNITY_24[23]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw285() {
        return multiply(ROOTS_OF_UNITY_24[19]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw300() {
        return multiply(ROOTS_OF_UNITY_24[20]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw315() {
        return multiply(ROOTS_OF_UNITY_24[21]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw330() {
        return multiply(ROOTS_OF_UNITY_24[22]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexDouble ccw345() {
        return multiply(ROOTS_OF_UNITY_24[23]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String toString() {
        if (isReal())
            return real + "";
        if (isImaginary())
            return imag + "I";
        if (imag < 0.0)
            return real + " - " + (-imag) + "I";
        return real + " + " + imag + "I";
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Double.hashCode(real) ^ Double.hashCode(imag);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ComplexDouble))
            return false;

        ComplexDouble cd = (ComplexDouble) o;
        return real == cd.real && imag == cd.imag;
    }
}
