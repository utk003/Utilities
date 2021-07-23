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

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A generic interface for any
 * <a href="https://mathworld.wolfram.com/ComplexNumber.html" target="_top">complex number</a> implementation.
 * <p>
 * This interface covers most of the basic operations for complex numbers.
 * Notable exceptions include a scaling function (ie multiplying by
 * scalar real numbers), magnitude, and getters/setters for the complex
 * number's real and imaginary components. These methods' signatures vary
 * based on whether the {@code ComplexNumber} implementation is backed
 * by {@code float}s or {@code double}s, and, as such, these methods
 * are left to the needs and specifications of any individual application.
 * <p>
 * Because of the aforementioned shortcomings in this interface declaration,
 * objects involving this interface and its implementing classes should prefer
 * being declared as the subclass rather than as a {@code ComplexNumber}.
 * <p>
 * The provided implementations of {@code ComplexNumber} are {@link ComplexFloat} and
 * {@link ComplexDouble} (backed by {@code float}s and {@code double}s, respectively).
 *
 * @param <CN> A subclass of {@code ComplexNumber}
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 24, 2021
 * @see ComplexFloat
 * @see ComplexDouble
 * @since 3.0.0
 */
@SuppressWarnings("UnusedReturnValue")
@ScheduledForRelease(inVersion = "v3.0.0")
public interface ComplexNumber<CN extends ComplexNumber<CN>> {
    /**
     * Copies the complex number represented by {@code cn}
     * and stores that same number into {@code this}.
     *
     * @param cn The {@code ComplexNumber} to copy into this {@code ComplexNumber}
     * @return This {@code ComplexNumber}, after the copy operation
     */
    @Contract("_ -> this")
    @NotNull CN copy(@NotNull CN cn);

    /**
     * Returns whether or not this {@code ComplexNumber} is exactly {@code 0}.
     *
     * @return {@code true}, if the complex number this object represents is {@code 0}; otherwise, {@code false}
     */
    boolean isZero();
    /**
     * Returns whether or not this {@code ComplexNumber} is a purely real number.
     *
     * @return {@code true}, if the complex number this object represents is real; otherwise, {@code false}
     * @see #isImaginary()
     */
    boolean isReal();
    /**
     * Returns whether or not this {@code ComplexNumber} is a purely imaginary number.
     *
     * @return {@code true}, if the complex number this object represents is imaginary; otherwise, {@code false}
     * @see #isReal()
     */
    boolean isImaginary();

    /**
     * Zeroes out the real and imaginary components of this {@code ComplexNumber}.
     *
     * @return This {@code ComplexNumber}, after both components of the complex number are zeroed out
     * @see #identity()
     */
    @Contract("-> this")
    @NotNull CN zero();
    /**
     * Replaces the complex number this {@code ComplexNumber} represents with the real number {@code 1}.
     *
     * @return This {@code ComplexNumber}, after it is replaced
     * @see #zero()
     */
    @Contract("-> this")
    @NotNull CN identity();

    /**
     * Multiplies this {@code ComplexNumber} by the given integer.
     *
     * @param n The {@code int} to multiply by
     * @return This {@code ComplexNumber}, after the multiplication
     * @see #divide(int)
     * @see #multiply(int, int)
     */
    @Contract("_ -> this")
    @NotNull CN multiply(int n);
    /**
     * Multiplies this {@code ComplexNumber} by the given integer.
     *
     * @param n The {@code long} to multiply by
     * @return This {@code ComplexNumber}, after the multiplication
     * @see #divide(long)
     * @see #multiply(long, long)
     */
    @Contract("_ -> this")
    @NotNull CN multiply(long n);

    /**
     * Divides this {@code ComplexNumber} by the given integer.
     *
     * @param n The {@code int} to multiply by
     * @return This {@code ComplexNumber}, after the division
     * @throws ArithmeticException If {@code n = 0}
     * @see #multiply(int)
     * @see #multiply(int, int)
     */
    @Contract("_ -> this")
    @NotNull CN divide(int n);
    /**
     * Divides this {@code ComplexNumber} by the given integer.
     *
     * @param n The {@code long} to multiply by
     * @return This {@code ComplexNumber}, after the division
     * @throws ArithmeticException If {@code n = 0}
     * @see #multiply(long)
     * @see #multiply(long, long)
     */
    @Contract("_ -> this")
    @NotNull CN divide(long n);

    /**
     * Multiplies this {@code ComplexNumber} by the rational number {@code p/q}.
     * <p>
     * If {@code p = q = 0}, it is equivalent to multiplying the complex number by {@code 1}.
     * In other words, if {@code p = q = 0}, the complex number this object represents does not change.
     *
     * @param p The numerator of the rational number to multiply by
     * @param q The denominator of the rational number to multiply by
     * @return This {@code ComplexNumber}, after the multiplication
     * @throws ArithmeticException If {@code q = 0} and {@code p ≠ 0}
     * @see #multiply(long)
     * @see #divide(long)
     */
    @Contract("_,_ -> this")
    @NotNull CN multiply(int p, int q);
    /**
     * Multiplies this {@code ComplexNumber} by the rational number {@code p/q}.
     * <p>
     * If {@code p = q = 0}, it is equivalent to multiplying the complex number by {@code 1}.
     * In other words, if {@code p = q = 0}, the complex number this object represents does not change.
     *
     * @param p The numerator of the rational number to multiply by
     * @param q The denominator of the rational number to multiply by
     * @return This {@code ComplexNumber}, after the multiplication
     * @throws ArithmeticException If {@code q = 0} and {@code p ≠ 0}
     * @see #multiply(long)
     * @see #divide(long)
     */
    @Contract("_,_ -> this")
    @NotNull CN multiply(long p, long q);

    /**
     * Independently rounds the real and imaginary components of this
     * {@code ComplexNumber} if each is within a specified threshold of an integer.
     * <p>
     * This functionality is identical to {@link io.github.utk003.util.math.FastMath#round(float, float)}
     * and {@link io.github.utk003.util.math.FastMath#round(double, double)}.
     *
     * @return This {@code ComplexNumber}, after rounding
     * @see io.github.utk003.util.math.FastMath#round(float, float)
     * @see io.github.utk003.util.math.FastMath#round(double, double)
     */
    @Contract("-> this")
    @NotNull CN round();

    /**
     * Normalizes this complex number.
     * <p>
     * This method scales down this complex number's
     * real and imaginary components by its magnitude.
     * When this process finishes, the resulting number's
     * magnitude will be exactly 1.
     *
     * @return This {@code ComplexNumber}, after the normalization
     */
    @Contract("-> this")
    @NotNull CN normalize();
    /**
     * Negates this complex number, or, equivalently, multiplies it by -1.
     * <p>
     * This method is identical to {@link #rot180()} and its aliases.
     *
     * @return This {@code ComplexNumber}, after the negation
     * @see #rot180()
     * @see #ccw180()
     * @see #cw180()
     */
    @Contract("-> this")
    @NotNull CN negate();
    /**
     * Replaces this complex number with its complex conjugate.
     *
     * @return This {@code ComplexNumber}, after the conjugation
     */
    @Contract("-> this")
    @NotNull CN conjugate();
    /**
     * Replaces this complex number with its multiplicative inverse (reciprocal).
     *
     * @return This {@code ComplexNumber}, after the reciprocation/inversion
     */
    @Contract("-> this")
    @NotNull CN invert();

    /**
     * Adds the specified {@code ComplexNumber} to this {@code ComplexNumber}.
     *
     * @param cn The {@code ComplexNumber} to add
     * @return This {@code ComplexNumber}, after the addition
     */
    @Contract("_ -> this")
    @NotNull CN add(@NotNull CN cn);
    /**
     * Subtracts the specified {@code ComplexNumber} from this {@code ComplexNumber}.
     *
     * @param cn The {@code ComplexNumber} to subtract
     * @return This {@code ComplexNumber}, after the subtraction
     */
    @Contract("_ -> this")
    @NotNull CN subtract(@NotNull CN cn);
    /**
     * Multiplies the specified {@code ComplexNumber} into this {@code ComplexNumber}.
     *
     * @param cn The {@code ComplexNumber} to multiply by
     * @return This {@code ComplexNumber}, after the multiplication
     */
    @Contract("_ -> this")
    @NotNull CN multiply(@NotNull CN cn);
    /**
     * Divides the specified {@code ComplexNumber} from this {@code ComplexNumber}.
     *
     * @param cn The {@code ComplexNumber} to divide by
     * @return This {@code ComplexNumber}, after the division
     */
    @Contract("_ -> this")
    @NotNull CN divide(@NotNull CN cn);

    /**
     * Takes the square root of this {@code ComplexNumber}.
     *
     * @return This {@code ComplexNumber}, after the square root is taken
     */
    @Contract("-> this")
    @NotNull CN sqrt();
    /**
     * Takes the cube root of this {@code ComplexNumber}.
     *
     * @return This {@code ComplexNumber}, after the cube root is taken
     */
    @Contract("-> this")
    @NotNull CN cbrt();

    /**
     * Squares this {@code ComplexNumber} (raises it to the second power).
     *
     * @return This {@code ComplexNumber}, after the squaring operation
     */
    @Contract("-> this")
    @NotNull CN square();
    /**
     * Squares this {@code ComplexNumber} (raises it to the third power).
     *
     * @return This {@code ComplexNumber}, after the cubing operation
     */
    @Contract("-> this")
    @NotNull CN cube();

    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 15} degrees (or {@code π/12} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity15();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 30} degrees (or {@code π/6} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity30();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 45} degrees (or {@code π/4} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity45();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 60} degrees (or {@code π/3} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity60();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 75} degrees (or {@code 5π/12} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity75();

    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 90} degrees (or {@code π/2} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity90();

    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 105} degrees (or {@code 7π/12} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity105();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 120} degrees (or {@code 2π/3} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity120();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 135} degrees (or {@code 3π/4} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity135();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 150} degrees (or {@code 5π/6} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity150();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 165} degrees (or {@code 11π/12} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity165();

    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 180} degrees (or {@code π} radians).
     * <p>
     * This sets the complex number to a value of {@code -1}.
     * The analogous (albeit positive) setter is {@link #identity()}.
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity180();

    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 195} degrees (or {@code 13π/12} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity195();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 210} degrees (or {@code 7π/6} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity210();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 225} degrees (or {@code 5π/4} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity225();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 240} degrees (or {@code 4π/3} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity240();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 255} degrees (or {@code 17π/12} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity255();

    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 270} degrees (or {@code 3π/2} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity270();

    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 285} degrees (or {@code 19π/12} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity285();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 300} degrees (or {@code 5π/3} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity300();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 315} degrees (or {@code 7π/4} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity315();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 330} degrees (or {@code 11π/6} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity330();
    /**
     * Replace this {@code ComplexNumber} with the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to an angle of {@code 345} degrees (or {@code 23π/12} radians).
     *
     * @return This {@code ComplexNumber}, after the value replacement
     */
    @Contract("-> this")
    @NotNull CN unity345();

    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 15} degrees (or {@code π/12} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     */
    @Contract("-> this")
    @NotNull CN ccw15();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 30} degrees (or {@code π/6} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     */
    @Contract("-> this")
    @NotNull CN ccw30();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 45} degrees (or {@code π/4} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     */
    @Contract("-> this")
    @NotNull CN ccw45();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 60} degrees (or {@code π/3} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     */
    @Contract("-> this")
    @NotNull CN ccw60();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 75} degrees (or {@code 5π/12} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     */
    @Contract("-> this")
    @NotNull CN ccw75();

    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 90} degrees (or {@code π/2} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     */
    @Contract("-> this")
    @NotNull CN ccw90();

    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 105} degrees (or {@code 7π/12} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     */
    @Contract("-> this")
    @NotNull CN ccw105();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 120} degrees (or {@code 2π/3} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     */
    @Contract("-> this")
    @NotNull CN ccw120();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 135} degrees (or {@code 3π/4} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     */
    @Contract("-> this")
    @NotNull CN ccw135();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 150} degrees (or {@code 5π/6} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     */
    @Contract("-> this")
    @NotNull CN ccw150();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 165} degrees (or {@code 11π/12} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     */
    @Contract("-> this")
    @NotNull CN ccw165();

    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 180} degrees (or {@code π} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     * <p>
     * This is equivalent to negating the complex number (multiplying by {@code -1}).
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #negate()
     * @see #rot180()
     * @see #cw180()
     */
    @Contract("-> this")
    @NotNull CN ccw180();
    /**
     * Rotates this {@code ComplexNumber} by an angle of {@code 180} degrees (or {@code π} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     * <p>
     * This is equivalent to negating the complex number (multiplying by {@code -1}).
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #negate()
     * @see #ccw180()
     * @see #cw180()
     */
    @Contract("-> this")
    @NotNull CN rot180();
    /**
     * Rotates this {@code ComplexNumber} clockwise by an angle of {@code 180} degrees (or {@code π} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     * <p>
     * This is equivalent to negating the complex number (multiplying by {@code -1}).
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #negate()
     * @see #ccw180()
     * @see #rot180()
     */
    @Contract("-> this")
    @NotNull CN cw180();

    /**
     * Rotates this {@code ComplexNumber} clockwise by an angle of {@code 165} degrees (or {@code 11π/12} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #ccw195()
     */
    @Contract("-> this")
    @NotNull CN cw165();
    /**
     * Rotates this {@code ComplexNumber} clockwise by an angle of {@code 150} degrees (or {@code 5π/6} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #ccw210()
     */
    @Contract("-> this")
    @NotNull CN cw150();
    /**
     * Rotates this {@code ComplexNumber} clockwise by an angle of {@code 135} degrees (or {@code 3π/4} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #ccw225()
     */
    @Contract("-> this")
    @NotNull CN cw135();
    /**
     * Rotates this {@code ComplexNumber} clockwise by an angle of {@code 120} degrees (or {@code 2π/3} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #ccw240()
     */
    @Contract("-> this")
    @NotNull CN cw120();
    /**
     * Rotates this {@code ComplexNumber} clockwise by an angle of {@code 105} degrees (or {@code 7π/12} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #ccw255()
     */
    @Contract("-> this")
    @NotNull CN cw105();

    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 195} degrees (or {@code 13π/12} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #cw165()
     */
    @Contract("-> this")
    @NotNull CN ccw195();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 210} degrees (or {@code 7π/6} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #cw150()
     */
    @Contract("-> this")
    @NotNull CN ccw210();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 225} degrees (or {@code 5π/4} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #cw135()
     */
    @Contract("-> this")
    @NotNull CN ccw225();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 240} degrees (or {@code 4π/3} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #cw120
     */
    @Contract("-> this")
    @NotNull CN ccw240();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 255} degrees (or {@code 17π/12} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #cw105
     */
    @Contract("-> this")
    @NotNull CN ccw255();

    /**
     * Rotates this {@code ComplexNumber} clockwise by an angle of {@code 90} degrees (or {@code π/2} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #ccw270()
     */
    @Contract("-> this")
    @NotNull CN cw90();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 270} degrees (or {@code 3π/2} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #cw90()
     */
    @Contract("-> this")
    @NotNull CN ccw270();

    /**
     * Rotates this {@code ComplexNumber} clockwise by an angle of {@code 75} degrees (or {@code 5π/12} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #ccw285()
     */
    @Contract("-> this")
    @NotNull CN cw75();
    /**
     * Rotates this {@code ComplexNumber} clockwise by an angle of {@code 60} degrees (or {@code π/3} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #ccw300()
     */
    @Contract("-> this")
    @NotNull CN cw60();
    /**
     * Rotates this {@code ComplexNumber} clockwise by an angle of {@code 45} degrees (or {@code π/4} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #ccw315()
     */
    @Contract("-> this")
    @NotNull CN cw45();
    /**
     * Rotates this {@code ComplexNumber} clockwise by an angle of {@code 30} degrees (or {@code π/6} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #ccw330()
     */
    @Contract("-> this")
    @NotNull CN cw30();
    /**
     * Rotates this {@code ComplexNumber} clockwise by an angle of {@code 15} degrees (or {@code π/12} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #ccw345()
     */
    @Contract("-> this")
    @NotNull CN cw15();

    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 285} degrees (or {@code 19π/12} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #cw75()
     */
    @Contract("-> this")
    @NotNull CN ccw285();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 300} degrees (or {@code 5π/3} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #cw60()
     */
    @Contract("-> this")
    @NotNull CN ccw300();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 315} degrees (or {@code 7π/4} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #cw45()
     */
    @Contract("-> this")
    @NotNull CN ccw315();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 330} degrees (or {@code 11π/6} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #cw30()
     */
    @Contract("-> this")
    @NotNull CN ccw330();
    /**
     * Rotates this {@code ComplexNumber} counter-clockwise by an angle of {@code 345} degrees (or {@code 23π/12} radians).
     * This operation is equivalent to multiplying this complex number by the {@code 24}<sup>th</sup>
     * <a href="https://mathworld.wolfram.com/RootofUnity.html" target="_top">root of unity</a>
     * corresponding to that same angle.
     *
     * @return This {@code ComplexNumber}, after the rotation
     * @see #cw15()
     */
    @Contract("-> this")
    @NotNull CN ccw345();
}
