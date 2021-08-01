package io.github.utk003.util.math.unsigned;

import io.github.utk003.util.math.exception.UnsignedValueCastException;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static io.github.utk003.util.math.exception.UnsignedValueCastException.getExceptionForCast;
import static io.github.utk003.util.math.unsigned.UnsignedNumberConstants.getBigInteger;

@ApiStatus.Experimental
@RequiresDocumentation
public final class UnsignedInteger extends UnsignedNumber {
    /**
     * The maximum value for an unsigned {@code int}.
     *
     * @see UnsignedNumberConstants#MAX_UNSIGNED_INT_VALUE
     */
    private static final long MAX_VALUE = UnsignedNumberConstants.MAX_UNSIGNED_INT_VALUE;
    /**
     * The maximum value for an unsigned {@code byte}, as a {@link BigInteger}.
     *
     * @see #MAX_VALUE
     */
    private static final @NotNull BigInteger BIG_INTEGER_MAX_VALUE = getBigInteger("" + MAX_VALUE);

    /**
     * The raw {@code int} value of this {@code UnsignedInteger}.
     */
    public final int value;

    /**
     * Constructs a new {@code UnsignedInteger} with the given raw {@code int} value.
     *
     * @param unsigned The raw value of this {@code UnsignedInteger}
     */
    private UnsignedInteger(int unsigned) {
        value = unsigned;
    }

    /**
     * A {@code static} cache of {@code UnsignedInteger} instances.
     * <p>
     * Possible values range from {@code 0x00000000 [0]} to {@code 0xFFFFFFFF [2}<sup>{@code 32}</sup> {@code - 1]}.
     */
    private static final @NotNull Map<Integer, UnsignedInteger> CACHE = new HashMap<>();

    /**
     * Checks whether the given signed {@code byte} has an equivalent unsigned value.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return {@code true}, if the argument is non-negative; otherwise, {@code false}
     */
    private static boolean verify(byte signed) {
        return signed >= 0;
    }
    /**
     * Computes the raw {@code int} equivalent of the signed {@code byte} argument value.
     * <p>
     * The return value of this method is only meaningful if {@link #verify(byte)} returns {@code true}.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return The raw {@code int} equivalent of the signed {@code byte} argument
     * @see #verify(byte)
     */
    private static int computeUnsignedRawInt(byte signed) {
        return signed & (int) MAX_VALUE;
    }
    /**
     * Returns a {@code UnsignedInteger} corresponding to the value of the given {@code byte}
     * argument, if such an unsigned value exists, and {@code null} otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedInteger} of the given value, if non-negative and within the size limitations
     * of a {@code int} (ie {@code <= 0xFFFFFFFF [2}<sup>{@code 32}</sup> {@code - 1]}); otherwise, {@code null}
     * @see #wrapOrFail(byte)
     */
    public static @Nullable UnsignedInteger wrap(byte signed) {
        return verify(signed) ? CACHE.computeIfAbsent(computeUnsignedRawInt(signed), UnsignedInteger::new) : null;
    }
    /**
     * Returns a {@code UnsignedInteger} corresponding to the value of the given {@code byte}
     * argument, if such an unsigned value exists, and throws an {@link UnsignedValueCastException}
     * otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedInteger} of the given value, if non-negative and within the size
     * limitations of a {@code int} (ie {@code <= 0xFFFFFFFF [2}<sup>{@code 32}</sup> {@code - 1]})
     * @throws UnsignedValueCastException If the given value is invalid for an unsigned integer
     * @see #wrap(byte)
     */
    public static @NotNull UnsignedInteger wrapOrFail(byte signed) throws UnsignedValueCastException {
        if (verify(signed)) return CACHE.computeIfAbsent(computeUnsignedRawInt(signed), UnsignedInteger::new);
        throw getExceptionForCast("the given byte", signed, "an unsigned int");
    }

    /**
     * Checks whether the given signed {@code short} has an equivalent unsigned value.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return {@code true}, if the argument is non-negative; otherwise, {@code false}
     */
    private static boolean verify(short signed) {
        return 0 <= signed;
    }
    /**
     * Computes the raw {@code int} equivalent of the signed {@code short} argument value.
     * <p>
     * The return value of this method is only meaningful if {@link #verify(short)} returns {@code true}.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return The raw {@code int} equivalent of the signed {@code short} argument
     * @see #verify(short)
     */
    private static int computeUnsignedRawInt(short signed) {
        return signed & (int) MAX_VALUE;
    }
    /**
     * Returns a {@code UnsignedInteger} corresponding to the value of the given {@code short}
     * argument, if such an unsigned value exists, and {@code null} otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedInteger} of the given value, if non-negative and within the size limitations
     * of a {@code int} (ie {@code <= 0xFFFFFFFF [2}<sup>{@code 32}</sup> {@code - 1]}); otherwise, {@code null}
     * @see #wrapOrFail(short)
     */
    public static @Nullable UnsignedInteger wrap(short signed) {
        return verify(signed) ? CACHE.computeIfAbsent(computeUnsignedRawInt(signed), UnsignedInteger::new) : null;
    }
    /**
     * Returns a {@code UnsignedInteger} corresponding to the value of the given {@code short}
     * argument, if such an unsigned value exists, and throws an {@link UnsignedValueCastException}
     * otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedInteger} of the given value, if non-negative and within the size
     * limitations of a {@code int} (ie {@code <= 0xFFFFFFFF [2}<sup>{@code 32}</sup> {@code - 1]})
     * @throws UnsignedValueCastException If the given value is invalid for an unsigned integer
     * @see #wrap(short)
     */
    public static @NotNull UnsignedInteger wrapOrFail(short signed) throws UnsignedValueCastException {
        if (verify(signed)) return CACHE.computeIfAbsent(computeUnsignedRawInt(signed), UnsignedInteger::new);
        throw getExceptionForCast("the given short", signed, "an unsigned int");
    }

    /**
     * Checks whether the given signed {@code int} has an equivalent unsigned value.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return {@code true}, if the argument is non-negative; otherwise, {@code false}
     */
    private static boolean verify(int signed) {
        return 0 <= signed;
    }
    /**
     * Computes the raw {@code int} equivalent of the signed {@code int} argument value.
     * <p>
     * The return value of this method is only meaningful if {@link #verify(int)} returns {@code true}.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return The raw {@code int} equivalent of the signed {@code int} argument
     * @see #verify(int)
     */
    private static int computeUnsignedRawInt(int signed) {
        return signed & (int) MAX_VALUE;
    }
    /**
     * Returns a {@code UnsignedInteger} corresponding to the value of the given {@code int}
     * argument, if such an unsigned value exists, and {@code null} otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedInteger} of the given value, if non-negative and within the size limitations
     * of a {@code int} (ie {@code <= 0xFFFFFFFF [2}<sup>{@code 32}</sup> {@code - 1]}); otherwise, {@code null}
     * @see #wrapOrFail(int)
     */
    public static @Nullable UnsignedInteger wrap(int signed) {
        return verify(signed) ? CACHE.computeIfAbsent(computeUnsignedRawInt(signed), UnsignedInteger::new) : null;
    }
    /**
     * Returns a {@code UnsignedInteger} corresponding to the value of the given {@code int}
     * argument, if such an unsigned value exists, and throws an {@link UnsignedValueCastException}
     * otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedInteger} of the given value, if non-negative and within the size
     * limitations of a {@code int} (ie {@code <= 0xFFFFFFFF [2}<sup>{@code 32}</sup> {@code - 1]})
     * @throws UnsignedValueCastException If the given value is invalid for an unsigned integer
     * @see #wrap(int)
     */
    public static @NotNull UnsignedInteger wrapOrFail(int signed) throws UnsignedValueCastException {
        if (verify(signed)) return CACHE.computeIfAbsent(computeUnsignedRawInt(signed), UnsignedInteger::new);
        throw getExceptionForCast("the given int", signed, "an unsigned int");
    }

    /**
     * Checks whether the given signed {@code long} has an equivalent unsigned value.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return {@code true}, if the argument is non-negative and representable by a {@code byte}
     * (ie {@code <= 0xFFFFFFFF [2}<sup>{@code 32}</sup> {@code - 1]}); otherwise, {@code false}
     */
    private static boolean verify(long signed) {
        return 0 <= signed && signed <= MAX_VALUE;
    }
    /**
     * Computes the raw {@code int} equivalent of the signed {@code long} argument value.
     * <p>
     * The return value of this method is only meaningful if {@link #verify(long)} returns {@code true}.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return The raw {@code int} equivalent of the signed {@code long} argument
     * @see #verify(long)
     */
    private static int computeUnsignedRawInt(long signed) {
        return (int) (signed & MAX_VALUE);
    }
    /**
     * Returns a {@code UnsignedInteger} corresponding to the value of the given {@code long}
     * argument, if such an unsigned value exists, and {@code null} otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedInteger} of the given value, if non-negative and within the size limitations
     * of a {@code int} (ie {@code <= 0xFFFFFFFF [2}<sup>{@code 32}</sup> {@code - 1]}); otherwise, {@code null}
     * @see #wrapOrFail(long)
     */
    public static @Nullable UnsignedInteger wrap(long signed) {
        return verify(signed) ? CACHE.computeIfAbsent(computeUnsignedRawInt(signed), UnsignedInteger::new) : null;
    }
    /**
     * Returns a {@code UnsignedInteger} corresponding to the value of the given {@code long}
     * argument, if such an unsigned value exists, and throws an {@link UnsignedValueCastException}
     * otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedInteger} of the given value, if non-negative and within the size
     * limitations of a {@code int} (ie {@code <= 0xFFFFFFFF [2}<sup>{@code 32}</sup> {@code - 1]})
     * @throws UnsignedValueCastException If the given value is invalid for an unsigned integer
     * @see #wrap(long)
     */
    public static @NotNull UnsignedInteger wrapOrFail(long signed) throws UnsignedValueCastException {
        if (verify(signed)) return CACHE.computeIfAbsent(computeUnsignedRawInt(signed), UnsignedInteger::new);
        throw getExceptionForCast("the given long", signed, "an unsigned int");
    }

    /**
     * Checks whether the given signed {@code BigInteger} has an equivalent unsigned value.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return {@code true}, if the argument is non-negative and representable by a {@code byte}
     * (ie {@code <= 0xFFFFFFFF [2}<sup>{@code 32}</sup> {@code - 1]}); otherwise, {@code false}
     */
    private static boolean verify(@NotNull BigInteger signed) {
        return signed.compareTo(BigInteger.ZERO) >= 0 && signed.compareTo(BIG_INTEGER_MAX_VALUE) <= 0;
    }
    /**
     * Computes the raw {@code int} equivalent of the signed {@code BigInteger} argument value.
     * <p>
     * The return value of this method is only meaningful if {@link #verify(BigInteger)} returns {@code true}.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return The raw {@code int} equivalent of the signed {@code BigInteger} argument
     * @see #verify(BigInteger)
     */
    private static int computeUnsignedRawInt(@NotNull BigInteger signed) {
        return signed.intValue() & (int) MAX_VALUE;
    }
    /**
     * Returns a {@code UnsignedInteger} corresponding to the value of the given {@code BigInteger}
     * argument, if such an unsigned value exists, and {@code null} otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedInteger} of the given value, if non-negative and within the size limitations
     * of a {@code int} (ie {@code <= 0xFFFFFFFF [2}<sup>{@code 32}</sup> {@code - 1]}); otherwise, {@code null}
     * @see #wrapOrFail(BigInteger)
     */
    public static @Nullable UnsignedInteger wrap(@NotNull BigInteger signed) {
        return verify(signed) ? CACHE.computeIfAbsent(computeUnsignedRawInt(signed), UnsignedInteger::new) : null;
    }
    /**
     * Returns a {@code UnsignedInteger} corresponding to the value of the given {@code BigInteger}
     * argument, if such an unsigned value exists, and throws an {@link UnsignedValueCastException}
     * otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedInteger} of the given value, if non-negative and within the size
     * limitations of a {@code int} (ie {@code <= 0xFFFFFFFF [2}<sup>{@code 32}</sup> {@code - 1]})
     * @throws UnsignedValueCastException If the given value is invalid for an unsigned integer
     * @see #wrap(BigInteger)
     */
    public static @NotNull UnsignedInteger wrapOrFail(@NotNull BigInteger signed) throws UnsignedValueCastException {
        if (verify(signed)) return CACHE.computeIfAbsent(computeUnsignedRawInt(signed), UnsignedInteger::new);
        throw getExceptionForCast("the given BigInteger", signed, "an unsigned int");
    }

    /**
     * Returns the {@code long}-value equivalent of an unsigned {@code int} value.
     *
     * @param unsigned The unsigned {@code int} value to convert to a {@code long}
     * @return The {@code long}-value equivalent of the unsigned {@code int} value
     */
    private static long getValue(int unsigned) {
        return unsigned & MAX_VALUE;
    }

    /**
     * Extracts the value of the unsigned number as a {@code byte}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code byte}
     * @throws UnsignedValueCastException If the conversion fails due to the limited size of the output {@code byte}
     */
    public static byte byteValue(int unsigned) throws UnsignedValueCastException {
        if (0 <= unsigned && unsigned <= Byte.MAX_VALUE) return (byte) unsigned;
        throw getExceptionForCast("this unsigned int", getValue(unsigned), "a signed byte");
    }
    /**
     * Extracts the value of the unsigned number as a {@code Byte}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code Byte}, if the conversion
     * if possible within the size of a {@code byte}; otherwise, {@code null}
     */
    public static @Nullable Byte asByte(int unsigned) {
        return 0 <= unsigned && unsigned <= Byte.MAX_VALUE ? (byte) unsigned : null;
    }
    /**
     * {@inheritDoc}
     *
     * @see #byteValue(int)
     */
    @Override
    public byte byteValue() {
        return byteValue(value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #asByte(int)
     */
    @Override
    public @Nullable Byte getByteValue() {
        return asByte(value);
    }

    /**
     * Extracts the value of the unsigned number as a {@code short}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code short}
     * @throws UnsignedValueCastException If the conversion fails due to the limited size of the output {@code short}
     */
    public static short shortValue(int unsigned) throws UnsignedValueCastException {
        if (0 <= unsigned && unsigned <= Short.MAX_VALUE) return (short) unsigned;
        throw getExceptionForCast("this unsigned int", getValue(unsigned), "a signed short");
    }
    /**
     * Extracts the value of the unsigned number as a {@code Short}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code Short}, if the conversion
     * if possible within the size of a {@code short}; otherwise, {@code null}
     */
    public static @Nullable Short asShort(int unsigned) {
        return 0 <= unsigned && unsigned <= Short.MAX_VALUE ? (short) unsigned : null;
    }
    /**
     * {@inheritDoc}
     *
     * @see #shortValue(int)
     */
    @Override
    public short shortValue() {
        return shortValue(value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #asShort(int)
     */
    @Override
    public @Nullable Short getShortValue() {
        return asShort(value);
    }

    /**
     * Extracts the value of the unsigned number as a {@code int}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code int}
     * @throws UnsignedValueCastException If the conversion fails due to the limited size of the output {@code int}
     */
    public static int intValue(int unsigned) throws UnsignedValueCastException {
        if (0 <= unsigned) return unsigned;
        throw getExceptionForCast("this unsigned int", getValue(unsigned), "a signed int");
    }
    /**
     * Extracts the value of the unsigned number as a {@code Integer}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code Integer}, if the conversion
     * if possible within the size of a {@code int}; otherwise, {@code null}
     */
    public static @Nullable Integer asInteger(int unsigned) {
        return 0 <= unsigned ? unsigned : null;
    }
    /**
     * {@inheritDoc}
     *
     * @see #intValue(int)
     */
    @Override
    public int intValue() {
        return intValue(value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #asInteger(int)
     */
    @Override
    public @Nullable Integer getIntegerValue() {
        return asInteger(value);
    }

    /**
     * Extracts the value of the unsigned number as a {@code long}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code long}
     */
    public static long longValue(int unsigned) {
        return getValue(unsigned);
    }
    /**
     * Extracts the value of the unsigned number as a {@code Long}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code Long}
     */
    public static @NotNull Long asLong(int unsigned) {
        return longValue(unsigned);
    }
    /**
     * {@inheritDoc}
     *
     * @see #longValue(int)
     */
    @Override
    public long longValue() {
        return longValue(value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #asLong(int)
     */
    @Override
    public @NotNull Long getLongValue() {
        return asLong(value);
    }

    /**
     * Extracts the value of the unsigned number as a {@code float}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code float}
     */
    public static float floatValue(int unsigned) {
        return getValue(unsigned);
    }
    /**
     * Extracts the value of the unsigned number as a {@code Float}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code Float}
     */
    public static @NotNull Float asFloat(int unsigned) {
        return floatValue(unsigned);
    }
    /**
     * {@inheritDoc}
     *
     * @see #floatValue(int)
     */
    @Override
    public float floatValue() {
        return floatValue(value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #asFloat(int)
     */
    @Override
    public @NotNull Float getFloatValue() {
        return asFloat(value);
    }

    /**
     * Extracts the value of the unsigned number as a {@code double}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code double}
     */
    public static double doubleValue(int unsigned) {
        return getValue(unsigned);
    }
    /**
     * Extracts the value of the unsigned number as a {@code Double}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code Double}
     */
    public static @NotNull Double asDouble(int unsigned) {
        return doubleValue(unsigned);
    }
    /**
     * {@inheritDoc}
     *
     * @see #doubleValue(int)
     */
    @Override
    public double doubleValue() {
        return doubleValue(value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #asDouble(int)
     */
    @Override
    public @NotNull Double getDoubleValue() {
        return asDouble(value);
    }

    /**
     * Extracts the value of the unsigned number as a {@code BigInteger}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code BigInteger}
     */
    public static @NotNull BigInteger asBigInteger(int unsigned) {
        return getBigInteger("" + intValue(unsigned));
    }
    /**
     * {@inheritDoc}
     *
     * @see #asBigInteger(int)
     */
    @Override
    public @NotNull BigInteger getExactValue() {
        return asBigInteger(value);
    }

    /**
     * Returns the binary {@code String} form of the given unsigned number.
     *
     * @param unsigned The unsigned number to convert to binary
     * @return The binary form of the unsigned number
     */
    public static @NotNull String asBinaryString(int unsigned) {
        /* 32 bits in an int, 64 bits in a long */
        String bitString = Long.toBinaryString(longValue(unsigned));
        // 0 < bitString.length() <= 32 b/c 0 <= intValue(unsigned) < 2^32
        return UnsignedNumberConstants.BINARY_STRING_PADDING_ZEROS[32 - bitString.length()] + bitString;
    }
    /**
     * {@inheritDoc}
     *
     * @see #asBinaryString(int)
     */
    @Override
    public @NotNull String asBinaryString() {
        return asBinaryString(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String toString() {
        return "uint=" + intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return value;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof UnsignedInteger && value == ((UnsignedInteger) o).value;
    }

    /**
     * Compares two unsigned {@code int}s based on their actual values.
     *
     * @param unsigned1 The first unsigned int to compare
     * @param unsigned2 The second unsigned int to compare
     * @return An integer comparison between the two given unsigned ints
     */
    public static int compare(int unsigned1, int unsigned2) {
        return Long.compare(longValue(unsigned1), longValue(unsigned2));
    }
    /**
     * Compares two {@code UnsignedInteger}s based on their values.
     *
     * @param unsigned1 The first {@code UnsignedInteger} to compare
     * @param unsigned2 The second {@code UnsignedInteger} to compare
     * @return An integer comparison between the two given {@code UnsignedInteger}s
     * @see #value
     * @see #compare(int, int)
     */
    public static int compare(@NotNull UnsignedInteger unsigned1, @NotNull UnsignedInteger unsigned2) {
        return compare(unsigned1.value, unsigned2.value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #compare(UnsignedInteger, UnsignedInteger)
     */
    @Override
    public int compareTo(@NotNull UnsignedNumber other) {
        return other instanceof UnsignedInteger ? compare(this, (UnsignedInteger) other) : compare(this, other);
    }
}
