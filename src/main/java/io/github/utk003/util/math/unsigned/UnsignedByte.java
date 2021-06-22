package io.github.utk003.util.math.unsigned;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class UnsignedByte extends UnsignedNumber<Byte> implements Comparable<UnsignedByte> {
    private static final long MAX_VALUE = UnsignedNumberConstants.MAX_UNSIGNED_BYTE_VALUE;
    private static final @NotNull BigInteger BIG_INTEGER_MAX_VALUE = BigInteger.valueOf(MAX_VALUE);

    public final byte value;

    private UnsignedByte(byte unsigned, boolean dummy) {
        value = unsigned;
    }

    public UnsignedByte(@NotNull UnsignedByte unsigned) {
        value = unsigned.value;
    }

    private static final @NotNull Map<Byte, UnsignedByte> CACHE_MAP = new HashMap<>();
    private static final @NotNull Function<Byte, UnsignedByte> FUNC = (unsigned) -> new UnsignedByte(unsigned, false);

    private static boolean verify(byte signed) {
        return signed >= 0;
    }
    private static byte computeUnsigned(byte signed) {
        return signed;
    }
    public UnsignedByte(byte signed) throws IllegalArgumentException {
        if (!verify(signed)) throwExceptionForValueCast("the given byte", "an unsigned byte");
        value = computeUnsigned(signed);
    }
    public static @Nullable UnsignedByte wrap(byte signed) {
        return verify(signed) ? CACHE_MAP.computeIfAbsent(computeUnsigned(signed), FUNC) : null;
    }

    private static boolean verify(short signed) {
        return 0 <= signed && signed <= MAX_VALUE;
    }
    private static byte computeUnsigned(short signed) {
        return (byte) signed;
    }
    public UnsignedByte(short signed) throws IllegalArgumentException {
        if (!verify(signed)) throwExceptionForValueCast("the given short", "an unsigned byte");
        value = computeUnsigned(signed);
    }
    public static @Nullable UnsignedByte wrap(short signed) {
        return verify(signed) ? CACHE_MAP.computeIfAbsent(computeUnsigned(signed), FUNC) : null;
    }

    private static boolean verify(int signed) {
        return 0 <= signed && signed <= MAX_VALUE;
    }
    private static byte computeUnsigned(int signed) {
        return (byte) signed;
    }
    public UnsignedByte(int signed) throws IllegalArgumentException {
        if (!verify(signed)) throwExceptionForValueCast("the given int", "an unsigned byte");
        value = computeUnsigned(signed);
    }
    public static @Nullable UnsignedByte wrap(int signed) {
        return verify(signed) ? CACHE_MAP.computeIfAbsent(computeUnsigned(signed), FUNC) : null;
    }

    private static boolean verify(long signed) {
        return 0 <= signed && signed <= MAX_VALUE;
    }
    private static byte computeUnsigned(long signed) {
        return (byte) signed;
    }
    public UnsignedByte(long signed) throws IllegalArgumentException {
        if (!verify(signed)) throwExceptionForValueCast("the given long", "an unsigned byte");
        value = computeUnsigned(signed);
    }
    public static @Nullable UnsignedByte wrap(long signed) {
        return verify(signed) ? CACHE_MAP.computeIfAbsent(computeUnsigned(signed), FUNC) : null;
    }

    private static boolean verify(@NotNull BigInteger signed) {
        return signed.compareTo(BigInteger.ZERO) >= 0 && signed.compareTo(BIG_INTEGER_MAX_VALUE) <= 0;
    }
    private static byte computeUnsigned(@NotNull BigInteger signed) {
        return signed.byteValue();
    }
    public UnsignedByte(@NotNull BigInteger signed) throws IllegalArgumentException {
        if (!verify(signed)) throwExceptionForValueCast("the given BigInteger", "an unsigned byte");
        value = computeUnsigned(signed);
    }
    public static @Nullable UnsignedByte wrap(@NotNull BigInteger signed) {
        return verify(signed) ? CACHE_MAP.computeIfAbsent(computeUnsigned(signed), FUNC) : null;
    }

    private static long getValue(byte unsigned) {
        return unsigned & MAX_VALUE;
    }

    public static @Nullable Byte asByte(byte unsigned) {
        return unsigned >= 0 ? unsigned : null;
    }
    @Override
    public @Nullable Byte getByteValue() {
        return asByte(value);
    }

    public static @NotNull Short asShort(byte unsigned) {
        return (short) getValue(unsigned);
    }
    @Override
    public @NotNull Short getShortValue() {
        return asShort(value);
    }

    public static @NotNull Integer asInt(byte unsigned) {
        return (int) getValue(unsigned);
    }
    @Override
    public @NotNull Integer getIntegerValue() {
        return asInt(value);
    }

    public static @NotNull Long asLong(byte unsigned) {
        return getValue(unsigned);
    }
    @Override
    public @NotNull Long getLongValue() {
        return asLong(value);
    }

    public static float asFloat(byte unsigned) {
        return getValue(unsigned);
    }
    @Override
    public float floatValue() {
        return asFloat(value);
    }

    public static double asDouble(byte unsigned) {
        return getValue(unsigned);
    }
    @Override
    public double doubleValue() {
        return asDouble(value);
    }

    private final @NotNull BigInteger EXACT_VALUE = BigInteger.valueOf(intValue());
    @Override
    public @NotNull BigInteger getExactValue() {
        return EXACT_VALUE;
    }
    @Override
    public @NotNull Byte getSignedEquivalent() {
        return value;
    }

    public static @NotNull String asBinaryString(byte unsigned) {
        /* 8 bits in a byte, 32 bits in an int */
        String bitString = Integer.toBinaryString(unsigned);
        int len = bitString.length();
        if (len == 8)
            return bitString;
        if (len < 8)
            return UnsignedNumberConstants.BINARY_STRING_PADDING_ZEROS[8 - len] + bitString;
        return bitString.substring(24); // 32 - 8 = 24
    }
    @Override
    public @NotNull String asBinaryString() {
        return asBinaryString(value);
    }

    public static int compare(byte unsigned1, byte unsigned2) {
        return Integer.compare(asInt(unsigned1), asInt(unsigned2));
    }
    public static int compare(@NotNull UnsignedByte unsigned1, @NotNull UnsignedByte unsigned2) {
        return compare(unsigned1.value, unsigned2.value);
    }
    @Override
    public int compareTo(@NotNull UnsignedByte otherUnsigned) {
        return compare(this, otherUnsigned);
    }
}
