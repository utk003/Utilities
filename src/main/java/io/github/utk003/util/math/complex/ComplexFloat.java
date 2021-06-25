package io.github.utk003.util.math.complex;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static io.github.utk003.util.math.Constants.PI;
import static io.github.utk003.util.math.Constants.TAU;
import static io.github.utk003.util.math.FastMath.shift;

@SuppressWarnings("UnusedReturnValue")
public final class ComplexFloat implements ComplexNumber<ComplexFloat> {
    /**
     * The "identity" {@code ComplexFloat} (ie the {@code ComplexFloat} corresponding to 1).
     */
    private static final @NotNull ComplexFloat IDENTITY = new ComplexFloat();
    /**
     * An array cache of the 24<sup>th</sup> roots of unity.
     */
    private static final @NotNull ComplexFloat[] ROOTS_OF_UNITY_24 = new ComplexFloat[24];
    static {
        ROOTS_OF_UNITY_24[0] = IDENTITY;
        ROOTS_OF_UNITY_24[1] = rootOfUnity(24);
        for (int i = 2; i < 24; i++)
            ROOTS_OF_UNITY_24[i] = new ComplexFloat().copy(ROOTS_OF_UNITY_24[i - 1]).multiply(ROOTS_OF_UNITY_24[1]);
    }

    /**
     * Returns a new {@code ComplexFloat} corresponding to
     * the "first" primitive root of unity of order {@code n}.
     * In other words, this method returns {@code e}<sup>{@code 2πi/n}</sup>.
     *
     * @param n The order of the root of unity to provide
     * @return The "first" primitive root of unity of order {@code n}
     */
    public static @NotNull ComplexFloat rootOfUnity(int n) {
        return new ComplexFloat().rotate((float) TAU / n);
    }

    /**
     * The real component of this complex number
     */
    public float real;
    /**
     * The imaginary component of this complex number
     */
    public float imag;

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
    public float length;
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
    public float lengthSquared;
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
    public float arg;

    /**
     * Constructs a new {@code ComplexFloat} corresponding to the real number {@code 1}.
     * <p>
     * This constructor is an optimized way of calling <pre>new ComplexFloat(1.0f, 0.0f).</pre>
     */
    public ComplexFloat() {
        real = 1.0f;
        imag = 0.0f;

        length = lengthSquared = 1.0f;
        arg = 0.0f;
    }
    /**
     * Constructs a new {@code ComplexFloat} with the given
     * real and imaginary components.
     *
     * @param real      The complex number's real part
     * @param imaginary The complex number's imaginary part
     */
    public ComplexFloat(float real, float imaginary) {
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
    private static float adjustAngle(float arg) {
        return shift(arg, (float) TAU);
    }

    /**
     * Sets this {@code ComplexFloat}'s real component to the specified value.
     *
     * @return This {@code ComplexFloat}, after the value change
     */
    @Contract("_ -> this")
    public @NotNull ComplexFloat setReal(float real) {
        this.real = real;
        return updateMembers();
    }
    /**
     * Sets this {@code ComplexFloat}'s imaginary component to the specified value.
     *
     * @return This {@code ComplexFloat}, after the value change
     */
    @Contract("_ -> this")
    public @NotNull ComplexFloat setImaginary(float imag) {
        this.imag = imag;
        return updateMembers();
    }

    /**
     * Sets this {@code ComplexFloat}'s real and imaginary
     * components to the specified values.
     *
     * @return This {@code ComplexFloat}, after the value changes
     */
    @Contract("_, _ -> this")
    public @NotNull ComplexFloat set(float real, float imag) {
        this.real = real;
        this.imag = imag;
        return updateMembers();
    }

    /**
     * Update this {@code ComplexFloat}'s instance variables
     * to be in sync with each other.
     * <p>
     * This method is primarily for use after a {@code ComplexFloat}
     * is modified directly via its instance vars {@link #real} and
     * {@link #imag}. In such a scenario, calling this method will
     * force the {@code ComplexFloat} to update its values of
     * {@link #length}, {@link #lengthSquared}, and {@link #arg}
     * to correspond to the same imaginary number represented by
     * {@code real} and {@code imag}.
     *
     * @return This {@code ComplexFloat}, after the forced update
     * @see #real
     * @see #imag
     * @see #length
     * @see #lengthSquared
     * @see #arg
     */
    @Contract("-> this")
    public @NotNull ComplexFloat updateMembers() {
        lengthSquared = real * real + imag * imag;
        length = (float) Math.sqrt(lengthSquared);
        arg = adjustAngle((float) Math.atan2(imag, real)); // y, x = imag, real
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_ -> this")
    public @NotNull ComplexFloat copy(@NotNull ComplexFloat cd) {
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
    @Contract("-> this")
    public @NotNull ComplexFloat normalize() {
        real /= length;
        imag /= length;
        length = lengthSquared = 1.0f;
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat negate() {
        real = -real;
        imag = -imag;
        arg = adjustAngle(arg + (float) PI);
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat conjugate() {
        imag = -imag;            // flip imag
        arg = adjustAngle(-arg); // flip arg
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat invert() {
        arg = adjustAngle(-arg);
        real /= lengthSquared;
        imag /= -lengthSquared; // negate imag (see #conjugate())
        length = 1.0f / length;
        lengthSquared = 1.0f / lengthSquared;
        return this;
    }

    /**
     * Scale this {@code ComplexFloat} up (or down) by the given factor.
     * <p>
     * If the given scaling factor is negative, this {@code ComplexFloat}'s
     * length and angle will be changed appropriately to ensure that its
     * length remains positive.
     *
     * @param scaleFactor The factor to scale this {@code ComplexFloat} by
     * @return This {@code ComplexFloat}, after the scaling
     */
    @Contract("_ -> this")
    public @NotNull ComplexFloat scale(float scaleFactor) {
        real *= scaleFactor;
        imag *= scaleFactor;

        if (scaleFactor < 0.0f) {
            scaleFactor = -scaleFactor;
            arg = adjustAngle(arg + (float) PI);
        }

        length *= scaleFactor;
        lengthSquared *= scaleFactor * scaleFactor;
        return this;
    }
    /**
     * Rotates this {@code ComplexFloat} counter-clockwise by the specified angle.
     * <p>
     * For angles that are multiples of {@code 15} degrees (or equivalently
     * {@code π/12} radians), use the provided specialized methods:
     * {@link #ccw90()}, {@link #ccw120()},
     * and {@link #rot180()}, to name a few.
     *
     * @param angle The angle to rotate this {@code ComplexFloat} by
     * @return This {@code ComplexFloat}, after the rotation
     * @see #ccw90()
     * @see #ccw120()
     * @see #rot180()
     */
    @Contract("_ -> this")
    public @NotNull ComplexFloat rotate(float angle) {
        arg = adjustAngle(arg + angle);
        real = length * (float) Math.cos(arg);
        imag = length * (float) Math.sin(arg);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_ -> this")
    public @NotNull ComplexFloat add(@NotNull ComplexFloat cd) {
        real += cd.real;
        imag += cd.imag;
        return updateMembers();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_ -> this")
    public @NotNull ComplexFloat subtract(@NotNull ComplexFloat cd) {
        real -= cd.real;
        imag -= cd.imag;
        return updateMembers();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_ -> this")
    public @NotNull ComplexFloat multiply(@NotNull ComplexFloat cd) {
        arg = adjustAngle(arg + cd.arg);
        length *= cd.length;
        lengthSquared *= cd.lengthSquared;

        float a = real, b = imag, c = cd.real, d = cd.imag;
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
    public @NotNull ComplexFloat divide(@NotNull ComplexFloat cd) {
        arg = adjustAngle(arg - cd.arg);
        length /= cd.length;
        lengthSquared /= cd.lengthSquared;

        float a = real, b = imag, c = cd.real, d = cd.imag;
        // (a + bi)/(c + di) = (a + bi)(c - di)/(c^2+d^2) = [(ac+bd) + (bc-ad)i] / (c^2+d^2)
        real = (a * c + b * d) / cd.lengthSquared;
        imag = (b * c - a * d) / cd.lengthSquared;
        return this;
    }

    /**
     * Raise this {@code ComplexFloat} to the given power.
     * <p>
     * For squares, cubes, square roots, and cube roots,
     * use the dedicated methods {@link #square()},
     * {@link #cube()}, {@link #sqrt()},
     * and {@link #cbrt()}, which have been optimized
     * to be much more efficient than this general method.
     *
     * @param exponent The power to raise this {@code ComplexFloat} to
     * @return This {@code ComplexFloat}, after the exponentiation
     * @see #square()
     * @see #cube()
     * @see #sqrt()
     * @see #cbrt()
     */
    @Contract("_ -> this")
    public @NotNull ComplexFloat pow(float exponent) {
        arg = adjustAngle(arg * exponent);
        length = (float) Math.pow(length, exponent);
        lengthSquared = length * length;
        real = length * (float) Math.cos(arg);
        imag = length * (float) Math.sin(arg);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat sqrt() {
        arg = adjustAngle(arg / 2.0f);
        lengthSquared = length;
        length = (float) Math.sqrt(length);
        real = length * (float) Math.cos(arg);
        imag = length * (float) Math.sin(arg);
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat cbrt() {
        arg = adjustAngle(arg / 3.0f);
        length = (float) Math.cbrt(length);
        lengthSquared = length * length;
        real = length * (float) Math.cos(arg);
        imag = length * (float) Math.sin(arg);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat square() {
        arg = adjustAngle(arg * 2.0f);
        length = lengthSquared;
        lengthSquared = length * length;

        float a = real, b = imag;
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
    public @NotNull ComplexFloat cube() {
        arg = adjustAngle(arg * 3.0f);
        length = length * lengthSquared;
        lengthSquared = length * length;

        float a = real, b = imag, aa = a * a, bb = b * b;
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
    public @NotNull ComplexFloat identity() {
        return copy(IDENTITY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity15() {
        return copy(ROOTS_OF_UNITY_24[1]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity30() {
        return copy(ROOTS_OF_UNITY_24[2]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity45() {
        return copy(ROOTS_OF_UNITY_24[3]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity60() {
        return copy(ROOTS_OF_UNITY_24[4]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity75() {
        return copy(ROOTS_OF_UNITY_24[5]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity90() {
        return copy(ROOTS_OF_UNITY_24[6]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity105() {
        return copy(ROOTS_OF_UNITY_24[7]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity120() {
        return copy(ROOTS_OF_UNITY_24[8]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity135() {
        return copy(ROOTS_OF_UNITY_24[9]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity150() {
        return copy(ROOTS_OF_UNITY_24[10]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity165() {
        return copy(ROOTS_OF_UNITY_24[11]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity180() {
        return copy(ROOTS_OF_UNITY_24[12]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity195() {
        return copy(ROOTS_OF_UNITY_24[13]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity210() {
        return copy(ROOTS_OF_UNITY_24[14]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity225() {
        return copy(ROOTS_OF_UNITY_24[15]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity240() {
        return copy(ROOTS_OF_UNITY_24[16]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity255() {
        return copy(ROOTS_OF_UNITY_24[17]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity270() {
        return copy(ROOTS_OF_UNITY_24[18]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity285() {
        return copy(ROOTS_OF_UNITY_24[19]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity300() {
        return copy(ROOTS_OF_UNITY_24[20]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity315() {
        return copy(ROOTS_OF_UNITY_24[21]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity330() {
        return copy(ROOTS_OF_UNITY_24[22]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat unity345() {
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
    public @NotNull ComplexFloat ccw15() {
        return multiply(ROOTS_OF_UNITY_24[1]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw30() {
        return multiply(ROOTS_OF_UNITY_24[2]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw45() {
        return multiply(ROOTS_OF_UNITY_24[3]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw60() {
        return multiply(ROOTS_OF_UNITY_24[4]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw75() {
        return multiply(ROOTS_OF_UNITY_24[5]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw90() {
        return multiply(ROOTS_OF_UNITY_24[6]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw105() {
        return multiply(ROOTS_OF_UNITY_24[7]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw120() {
        return multiply(ROOTS_OF_UNITY_24[8]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw135() {
        return multiply(ROOTS_OF_UNITY_24[9]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw150() {
        return multiply(ROOTS_OF_UNITY_24[10]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw165() {
        return multiply(ROOTS_OF_UNITY_24[11]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw180() {
        return negate();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat rot180() {
        return negate();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat cw180() {
        return negate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat cw165() {
        return multiply(ROOTS_OF_UNITY_24[13]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat cw150() {
        return multiply(ROOTS_OF_UNITY_24[14]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat cw135() {
        return multiply(ROOTS_OF_UNITY_24[15]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat cw120() {
        return multiply(ROOTS_OF_UNITY_24[16]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat cw105() {
        return multiply(ROOTS_OF_UNITY_24[17]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw195() {
        return multiply(ROOTS_OF_UNITY_24[13]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw210() {
        return multiply(ROOTS_OF_UNITY_24[14]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw225() {
        return multiply(ROOTS_OF_UNITY_24[15]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw240() {
        return multiply(ROOTS_OF_UNITY_24[16]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw255() {
        return multiply(ROOTS_OF_UNITY_24[17]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat cw90() {
        return multiply(ROOTS_OF_UNITY_24[18]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw270() {
        return multiply(ROOTS_OF_UNITY_24[18]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat cw75() {
        return multiply(ROOTS_OF_UNITY_24[19]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat cw60() {
        return multiply(ROOTS_OF_UNITY_24[20]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat cw45() {
        return multiply(ROOTS_OF_UNITY_24[21]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat cw30() {
        return multiply(ROOTS_OF_UNITY_24[22]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat cw15() {
        return multiply(ROOTS_OF_UNITY_24[23]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw285() {
        return multiply(ROOTS_OF_UNITY_24[19]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw300() {
        return multiply(ROOTS_OF_UNITY_24[20]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw315() {
        return multiply(ROOTS_OF_UNITY_24[21]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw330() {
        return multiply(ROOTS_OF_UNITY_24[22]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("-> this")
    public @NotNull ComplexFloat ccw345() {
        return multiply(ROOTS_OF_UNITY_24[23]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String toString() {
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
        if (!(o instanceof ComplexFloat))
            return false;

        ComplexFloat cd = (ComplexFloat) o;
        return real == cd.real && imag == cd.imag;
    }
}
