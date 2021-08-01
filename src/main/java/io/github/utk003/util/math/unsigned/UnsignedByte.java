package io.github.utk003.util.math.unsigned;

import io.github.utk003.util.math.exception.UnsignedValueCastException;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

import static io.github.utk003.util.math.exception.UnsignedValueCastException.getExceptionForCast;
import static io.github.utk003.util.math.unsigned.UnsignedNumberConstants.getBigInteger;

@ApiStatus.Experimental
@RequiresDocumentation
public final class UnsignedByte extends UnsignedNumber {
    /**
     * The maximum value for an unsigned {@code byte}.
     *
     * @see UnsignedNumberConstants#MAX_UNSIGNED_BYTE_VALUE
     */
    private static final int MAX_VALUE = (int) UnsignedNumberConstants.MAX_UNSIGNED_BYTE_VALUE;
    /**
     * The maximum value for an unsigned {@code byte}, as a {@link BigInteger}.
     *
     * @see #MAX_VALUE
     */
    private static final @NotNull BigInteger BIG_INTEGER_MAX_VALUE = getBigInteger("" + MAX_VALUE);

    /**
     * The raw {@code byte} value of this {@code UnsignedByte}.
     */
    public final byte value;

    /**
     * Constructs a new {@code UnsignedByte} with the given raw {@code byte} value.
     *
     * @param unsigned The raw value of this {@code UnsignedByte}
     */
    private UnsignedByte(byte unsigned) {
        value = unsigned;
    }

    /**
     * A {@code static} cache of all possible unique {@code UnsignedByte} instances.
     * <p>
     * Values range from {@code 0x00 [0]} to {@code 0xFF [2}<sup>{@code 8}</sup> {@code - 1]}.
     */
    private static final @NotNull UnsignedByte[] CACHE = new UnsignedByte[MAX_VALUE + 1];
    static {
        for (int i = 0; i <= MAX_VALUE; i++)
            CACHE[i] = new UnsignedByte((byte) i);
    }

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
     * Computes the index of the {@code UnsignedByte} instance in the cache corresponding
     * to the signed {@code byte} argument value.
     * <p>
     * The return value of this method is only meaningful if {@link #verify(byte)} returns {@code true}.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return The cache index of the intended {@code UnsignedByte} instance
     * @see #CACHE
     * @see #verify(byte)
     */
    private static int computeUnsignedIndex(byte signed) {
        return signed & MAX_VALUE;
    }
    /**
     * Returns a {@code UnsignedByte} corresponding to the value of the given {@code byte}
     * argument, if such an unsigned value exists, and {@code null} otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedByte} of the given value, if non-negative and within the size limitations
     * of a {@code byte} (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]}); otherwise, {@code null}
     * @see #wrapOrFail(byte)
     */
    public static @Nullable UnsignedByte wrap(byte signed) {
        return verify(signed) ? CACHE[computeUnsignedIndex(signed)] : null;
    }
    /**
     * Returns a {@code UnsignedByte} corresponding to the value of the given {@code byte}
     * argument, if such an unsigned value exists, and throws an {@link UnsignedValueCastException}
     * otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedByte} of the given value, if non-negative and within the size
     * limitations of a {@code byte} (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]})
     * @throws UnsignedValueCastException If the given value is invalid for an unsigned byte
     * @see #wrap(byte)
     */
    public static @NotNull UnsignedByte wrapOrFail(byte signed) throws UnsignedValueCastException {
        if (verify(signed)) return CACHE[computeUnsignedIndex(signed)];
        throw getExceptionForCast("the given byte", signed, "an unsigned byte");
    }

    /**
     * Checks whether the given signed {@code short} has an equivalent unsigned value.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return {@code true}, if the argument is non-negative and representable by a {@code byte}
     * (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]}); otherwise, {@code false}
     */
    private static boolean verify(short signed) {
        return 0 <= signed && signed <= MAX_VALUE;
    }
    /**
     * Computes the index of the {@code UnsignedByte} instance in the cache corresponding
     * to the signed {@code short} argument value.
     * <p>
     * The return value of this method is only meaningful if {@link #verify(short)} returns {@code true}.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return The cache index of the intended {@code UnsignedByte} instance
     * @see #CACHE
     * @see #verify(short)
     */
    private static int computeUnsignedIndex(short signed) {
        return signed & MAX_VALUE;
    }
    /**
     * Returns a {@code UnsignedByte} corresponding to the value of the given {@code short}
     * argument, if such an unsigned value exists, and {@code null} otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedByte} of the given value, if non-negative and within the size limitations
     * of a {@code byte} (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]}); otherwise, {@code null}
     * @see #wrapOrFail(short)
     */
    public static @Nullable UnsignedByte wrap(short signed) {
        return verify(signed) ? CACHE[computeUnsignedIndex(signed)] : null;
    }
    /**
     * Returns a {@code UnsignedByte} corresponding to the value of the given {@code short}
     * argument, if such an unsigned value exists, and throws an {@link UnsignedValueCastException}
     * otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedByte} of the given value, if non-negative and within the size
     * limitations of a {@code byte} (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]})
     * @throws UnsignedValueCastException If the given value is invalid for an unsigned byte
     * @see #wrap(short)
     */
    public static @NotNull UnsignedByte wrapOrFail(short signed) throws UnsignedValueCastException {
        if (verify(signed)) return CACHE[computeUnsignedIndex(signed)];
        throw getExceptionForCast("the given short", signed, "an unsigned byte");
    }

    /**
     * Checks whether the given signed {@code int} has an equivalent unsigned value.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return {@code true}, if the argument is non-negative and representable by a {@code byte}
     * (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]}); otherwise, {@code false}
     */
    private static boolean verify(int signed) {
        return 0 <= signed && signed <= MAX_VALUE;
    }
    /**
     * Computes the index of the {@code UnsignedByte} instance in the cache corresponding
     * to the signed {@code int} argument value.
     * <p>
     * The return value of this method is only meaningful if {@link #verify(int)} returns {@code true}.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return The cache index of the intended {@code UnsignedByte} instance
     * @see #CACHE
     * @see #verify(int)
     */
    private static int computeUnsignedIndex(int signed) {
        return signed & MAX_VALUE;
    }
    /**
     * Returns a {@code UnsignedByte} corresponding to the value of the given {@code int}
     * argument, if such an unsigned value exists, and {@code null} otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedByte} of the given value, if non-negative and within the size limitations
     * of a {@code byte} (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]}); otherwise, {@code null}
     * @see #wrapOrFail(int)
     */
    public static @Nullable UnsignedByte wrap(int signed) {
        return verify(signed) ? CACHE[computeUnsignedIndex(signed)] : null;
    }
    /**
     * Returns a {@code UnsignedByte} corresponding to the value of the given {@code int}
     * argument, if such an unsigned value exists, and throws an {@link UnsignedValueCastException}
     * otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedByte} of the given value, if non-negative and within the size
     * limitations of a {@code byte} (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]})
     * @throws UnsignedValueCastException If the given value is invalid for an unsigned byte
     * @see #wrap(int)
     */
    public static @NotNull UnsignedByte wrapOrFail(int signed) throws UnsignedValueCastException {
        if (verify(signed)) return CACHE[computeUnsignedIndex(signed)];
        throw getExceptionForCast("the given int", signed, "an unsigned byte");
    }

    /**
     * Checks whether the given signed {@code long} has an equivalent unsigned value.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return {@code true}, if the argument is non-negative and representable by a {@code byte}
     * (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]}); otherwise, {@code false}
     */
    private static boolean verify(long signed) {
        return 0 <= signed && signed <= MAX_VALUE;
    }
    /**
     * Computes the index of the {@code UnsignedByte} instance in the cache corresponding
     * to the signed {@code long} argument value.
     * <p>
     * The return value of this method is only meaningful if {@link #verify(long)} returns {@code true}.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return The cache index of the intended {@code UnsignedByte} instance
     * @see #CACHE
     * @see #verify(long)
     */
    private static int computeUnsignedIndex(long signed) {
        return (int) signed & MAX_VALUE;
    }
    /**
     * Returns a {@code UnsignedByte} corresponding to the value of the given {@code long}
     * argument, if such an unsigned value exists, and {@code null} otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedByte} of the given value, if non-negative and within the size limitations
     * of a {@code byte} (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]}); otherwise, {@code null}
     * @see #wrapOrFail(long)
     */
    public static @Nullable UnsignedByte wrap(long signed) {
        return verify(signed) ? CACHE[computeUnsignedIndex(signed)] : null;
    }
    /**
     * Returns a {@code UnsignedByte} corresponding to the value of the given {@code long}
     * argument, if such an unsigned value exists, and throws an {@link UnsignedValueCastException}
     * otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedByte} of the given value, if non-negative and within the size
     * limitations of a {@code byte} (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]})
     * @throws UnsignedValueCastException If the given value is invalid for an unsigned byte
     * @see #wrap(long)
     */
    public static @NotNull UnsignedByte wrapOrFail(long signed) throws UnsignedValueCastException {
        if (verify(signed)) return CACHE[computeUnsignedIndex(signed)];
        throw getExceptionForCast("the given long", signed, "an unsigned byte");
    }

    /**
     * Checks whether the given signed {@code BigInteger} has an equivalent unsigned value.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return {@code true}, if the argument is non-negative and representable by a {@code byte}
     * (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]}); otherwise, {@code false}
     */
    private static boolean verify(@NotNull BigInteger signed) {
        return signed.compareTo(BigInteger.ZERO) >= 0 && signed.compareTo(BIG_INTEGER_MAX_VALUE) <= 0;
    }
    /**
     * Computes the index of the {@code UnsignedByte} instance in the cache corresponding
     * to the signed {@code BigInteger} argument value.
     * <p>
     * The return value of this method is only meaningful if {@link #verify(BigInteger)} returns {@code true}.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return The cache index of the intended {@code UnsignedByte} instance
     * @see #CACHE
     * @see #verify(BigInteger)
     */
    private static int computeUnsignedIndex(@NotNull BigInteger signed) {
        return signed.byteValue() & MAX_VALUE;
    }
    /**
     * Returns a {@code UnsignedByte} corresponding to the value of the given {@code BigInteger}
     * argument, if such an unsigned value exists, and {@code null} otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedByte} of the given value, if non-negative and within the size limitations
     * of a {@code byte} (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]}); otherwise, {@code null}
     * @see #wrapOrFail(BigInteger)
     */
    public static @Nullable UnsignedByte wrap(@NotNull BigInteger signed) {
        return verify(signed) ? CACHE[computeUnsignedIndex(signed)] : null;
    }
    /**
     * Returns a {@code UnsignedByte} corresponding to the value of the given {@code BigInteger}
     * argument, if such an unsigned value exists, and throws an {@link UnsignedValueCastException}
     * otherwise.
     *
     * @param signed The signed value to convert to an unsigned value
     * @return An {@code UnsignedByte} of the given value, if non-negative and within the size
     * limitations of a {@code byte} (ie {@code <= 0xFF [2}<sup>{@code 8}</sup> {@code - 1]})
     * @throws UnsignedValueCastException If the given value is invalid for an unsigned byte
     * @see #wrap(BigInteger)
     */
    public static @NotNull UnsignedByte wrapOrFail(@NotNull BigInteger signed) throws UnsignedValueCastException {
        if (verify(signed)) return CACHE[computeUnsignedIndex(signed)];
        throw getExceptionForCast("the given BigInteger", signed, "an unsigned byte");
    }

    /**
     * Returns the {@code long}-value equivalent of an unsigned {@code byte} value.
     *
     * @param unsigned The unsigned {@code byte} value to convert to a {@code long}
     * @return The {@code long}-value equivalent of the unsigned {@code byte} value
     */
    private static long getValue(byte unsigned) {
        return unsigned & (long) MAX_VALUE;
    }

    /**
     * Extracts the value of the unsigned number as a {@code byte}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code byte}
     * @throws UnsignedValueCastException If the conversion fails due to the limited size of the output {@code byte}
     */
    public static byte byteValue(byte unsigned) throws UnsignedValueCastException {
        if (unsigned >= 0) return unsigned;
        throw getExceptionForCast("this unsigned byte", getValue(unsigned), "a signed byte");
    }
    /**
     * Extracts the value of the unsigned number as a {@code Byte}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code Byte}, if the conversion
     * if possible within the size of a {@code byte}; otherwise, {@code null}
     */
    public static @Nullable Byte asByte(byte unsigned) {
        return unsigned >= 0 ? unsigned : null;
    }
    /**
     * {@inheritDoc}
     *
     * @see #byteValue(byte)
     */
    @Override
    public byte byteValue() {
        return byteValue(value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #asByte(byte)
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
     */
    public static short shortValue(byte unsigned) {
        return (short) getValue(unsigned);
    }
    /**
     * Extracts the value of the unsigned number as a {@code Short}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code Short}
     */
    public static @NotNull Short asShort(byte unsigned) {
        return shortValue(unsigned);
    }
    /**
     * {@inheritDoc}
     *
     * @see #shortValue(byte)
     */
    @Override
    public short shortValue() {
        return shortValue(value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #asShort(byte)
     */
    @Override
    public @NotNull Short getShortValue() {
        return asShort(value);
    }

    /**
     * Extracts the value of the unsigned number as a {@code int}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code int}
     */
    public static int intValue(byte unsigned) {
        return (int) getValue(unsigned);
    }
    /**
     * Extracts the value of the unsigned number as a {@code Integer}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code Integer}
     */
    public static @NotNull Integer asInteger(byte unsigned) {
        return intValue(unsigned);
    }
    /**
     * {@inheritDoc}
     *
     * @see #intValue(byte)
     */
    @Override
    public int intValue() {
        return intValue(value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #asInteger(byte)
     */
    @Override
    public @NotNull Integer getIntegerValue() {
        return asInteger(value);
    }

    /**
     * Extracts the value of the unsigned number as a {@code long}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code long}
     */
    public static long longValue(byte unsigned) {
        return getValue(unsigned);
    }
    /**
     * Extracts the value of the unsigned number as a {@code Long}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code Long}
     */
    public static @NotNull Long asLong(byte unsigned) {
        return longValue(unsigned);
    }
    /**
     * {@inheritDoc}
     *
     * @see #longValue(byte)
     */
    @Override
    public long longValue() {
        return longValue(value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #asLong(byte)
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
    public static float floatValue(byte unsigned) {
        return getValue(unsigned);
    }
    /**
     * Extracts the value of the unsigned number as a {@code Float}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code Float}
     */
    public static @NotNull Float asFloat(byte unsigned) {
        return floatValue(unsigned);
    }
    /**
     * {@inheritDoc}
     *
     * @see #floatValue(byte)
     */
    @Override
    public float floatValue() {
        return floatValue(value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #asFloat(byte)
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
    public static double doubleValue(byte unsigned) {
        return getValue(unsigned);
    }
    /**
     * Extracts the value of the unsigned number as a {@code Double}.
     *
     * @param unsigned The unsigned value to extract
     * @return The value of the given unsigned number as a {@code Double}
     */
    public static @NotNull Double asDouble(byte unsigned) {
        return doubleValue(unsigned);
    }
    /**
     * {@inheritDoc}
     *
     * @see #doubleValue(byte)
     */
    @Override
    public double doubleValue() {
        return doubleValue(value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #asDouble(byte)
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
    public static @NotNull BigInteger asBigInteger(byte unsigned) {
        return getBigInteger("" + intValue(unsigned));
    }
    /**
     * {@inheritDoc}
     *
     * @see #asBigInteger(byte)
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
    public static @NotNull String asBinaryString(byte unsigned) {
        /* 8 bits in a byte, 32 bits in an int */
        String bitString = Integer.toBinaryString(intValue(unsigned));
        // 0 < bitString.length() <= 8 b/c 0 <= intValue(unsigned) < 256
        return UnsignedNumberConstants.BINARY_STRING_PADDING_ZEROS[8 - bitString.length()] + bitString;
    }
    /**
     * {@inheritDoc}
     *
     * @see #asBinaryString(byte)
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
        return "ubyte=" + intValue();
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
        return o instanceof UnsignedByte && value == ((UnsignedByte) o).value;
    }

    /**
     * Compares two unsigned {@code byte}s based on their actual values.
     *
     * @param unsigned1 The first unsigned byte to compare
     * @param unsigned2 The second unsigned byte to compare
     * @return An integer comparison between the two given unsigned bytes
     */
    public static int compare(byte unsigned1, byte unsigned2) {
        return Integer.compare(intValue(unsigned1), intValue(unsigned2));
    }
    /**
     * Compares two {@code UnsignedByte}s based on their values.
     *
     * @param unsigned1 The first {@code UnsignedByte} to compare
     * @param unsigned2 The second {@code UnsignedByte} to compare
     * @return An integer comparison between the two given {@code UnsignedByte}s
     * @see #value
     * @see #compare(byte, byte)
     */
    public static int compare(@NotNull UnsignedByte unsigned1, @NotNull UnsignedByte unsigned2) {
        return compare(unsigned1.value, unsigned2.value);
    }
    /**
     * {@inheritDoc}
     *
     * @see #compare(UnsignedByte, UnsignedByte)
     */
    @Override
    public int compareTo(@NotNull UnsignedNumber other) {
        return other instanceof UnsignedByte ? compare(this, (UnsignedByte) other) : compare(this, other);
    }
}
