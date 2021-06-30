package io.github.utk003.util.math.complex;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A generic interface for any complex number implementation.
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

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isZero();

    @Contract("-> this")
    @NotNull CN zero();
    @Contract("-> this")
    @NotNull CN identity();

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

    @Contract("_ -> this")
    @NotNull CN add(@NotNull CN cn);
    @Contract("_ -> this")
    @NotNull CN subtract(@NotNull CN cn);
    @Contract("_ -> this")
    @NotNull CN multiply(@NotNull CN cn);
    @Contract("_ -> this")
    @NotNull CN divide(@NotNull CN cn);

    @Contract("-> this")
    @NotNull CN sqrt();
    @Contract("-> this")
    @NotNull CN cbrt();

    @Contract("-> this")
    @NotNull CN square();
    @Contract("-> this")
    @NotNull CN cube();

    @Contract("-> this")
    @NotNull CN unity15();
    @Contract("-> this")
    @NotNull CN unity30();
    @Contract("-> this")
    @NotNull CN unity45();
    @Contract("-> this")
    @NotNull CN unity60();
    @Contract("-> this")
    @NotNull CN unity75();

    @Contract("-> this")
    @NotNull CN unity90();

    @Contract("-> this")
    @NotNull CN unity105();
    @Contract("-> this")
    @NotNull CN unity120();
    @Contract("-> this")
    @NotNull CN unity135();
    @Contract("-> this")
    @NotNull CN unity150();
    @Contract("-> this")
    @NotNull CN unity165();

    @Contract("-> this")
    @NotNull CN unity180();

    @Contract("-> this")
    @NotNull CN unity195();
    @Contract("-> this")
    @NotNull CN unity210();
    @Contract("-> this")
    @NotNull CN unity225();
    @Contract("-> this")
    @NotNull CN unity240();
    @Contract("-> this")
    @NotNull CN unity255();

    @Contract("-> this")
    @NotNull CN unity270();

    @Contract("-> this")
    @NotNull CN unity285();
    @Contract("-> this")
    @NotNull CN unity300();
    @Contract("-> this")
    @NotNull CN unity315();
    @Contract("-> this")
    @NotNull CN unity330();
    @Contract("-> this")
    @NotNull CN unity345();

    @Contract("-> this")
    @NotNull CN ccw15();
    @Contract("-> this")
    @NotNull CN ccw30();
    @Contract("-> this")
    @NotNull CN ccw45();
    @Contract("-> this")
    @NotNull CN ccw60();
    @Contract("-> this")
    @NotNull CN ccw75();

    @Contract("-> this")
    @NotNull CN ccw90();

    @Contract("-> this")
    @NotNull CN ccw105();
    @Contract("-> this")
    @NotNull CN ccw120();
    @Contract("-> this")
    @NotNull CN ccw135();
    @Contract("-> this")
    @NotNull CN ccw150();
    @Contract("-> this")
    @NotNull CN ccw165();

    @Contract("-> this")
    @NotNull CN ccw180();
    @Contract("-> this")
    @NotNull CN rot180();
    @Contract("-> this")
    @NotNull CN cw180();

    @Contract("-> this")
    @NotNull CN cw165();
    @Contract("-> this")
    @NotNull CN cw150();
    @Contract("-> this")
    @NotNull CN cw135();
    @Contract("-> this")
    @NotNull CN cw120();
    @Contract("-> this")
    @NotNull CN cw105();

    @Contract("-> this")
    @NotNull CN ccw195();
    @Contract("-> this")
    @NotNull CN ccw210();
    @Contract("-> this")
    @NotNull CN ccw225();
    @Contract("-> this")
    @NotNull CN ccw240();
    @Contract("-> this")
    @NotNull CN ccw255();

    @Contract("-> this")
    @NotNull CN cw90();
    @Contract("-> this")
    @NotNull CN ccw270();

    @Contract("-> this")
    @NotNull CN cw75();
    @Contract("-> this")
    @NotNull CN cw60();
    @Contract("-> this")
    @NotNull CN cw45();
    @Contract("-> this")
    @NotNull CN cw30();
    @Contract("-> this")
    @NotNull CN cw15();

    @Contract("-> this")
    @NotNull CN ccw285();
    @Contract("-> this")
    @NotNull CN ccw300();
    @Contract("-> this")
    @NotNull CN ccw315();
    @Contract("-> this")
    @NotNull CN ccw330();
    @Contract("-> this")
    @NotNull CN ccw345();
}
