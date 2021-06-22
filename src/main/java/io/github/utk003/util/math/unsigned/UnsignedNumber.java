package io.github.utk003.util.math.unsigned;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

@SuppressWarnings("ConstantConditions")
public abstract class UnsignedNumber<N extends Number> extends Number {
    public abstract @Nullable Byte getByteValue();
    @Override
    public byte byteValue() throws NullPointerException {
        return getByteValue();
    }

    public abstract @Nullable Short getShortValue();
    @Override
    public short shortValue() throws NullPointerException {
        return getShortValue();
    }

    public abstract @Nullable Integer getIntegerValue();
    @Override
    public int intValue() throws NullPointerException {
        return getIntegerValue();
    }

    public abstract @Nullable Long getLongValue();
    @Override
    public long longValue() throws NullPointerException {
        return getLongValue();
    }

    @Override
    public float floatValue() {
        return Float.parseFloat(getExactValue().toString());
    }
    @Override
    public double doubleValue() {
        return Double.parseDouble(getExactValue().toString());
    }

    public abstract @NotNull BigInteger getExactValue();
    public abstract @NotNull N getSignedEquivalent();

    public abstract @NotNull String asBinaryString();

    @Contract("_, _ -> fail")
    static void throwExceptionForValueCast(@NotNull String from, @NotNull String to) throws IllegalArgumentException {
        throw new IllegalArgumentException("Cannot represent " + from + " as " + to);
    }

    public static @Nullable UnsignedByte wrap(byte signed) {
        return UnsignedByte.wrap(signed);
    }
//    public static @Nullable UnsignedShort wrap(short signed) {
//        return UnsignedShort.wrap(signed);
//    }
//    public static @Nullable UnsignedInt wrap(int signed) {
//        return UnsignedInt.wrap(signed);
//    }
//    public static @Nullable UnsignedLong wrap(long signed) {
//        return UnsignedLong.wrap(signed);
//    }

    public static int compare(@NotNull UnsignedNumber<?> unsigned1, @NotNull UnsignedNumber<?> unsigned2) {
        return unsigned1.getExactValue().compareTo(unsigned2.getExactValue());
    }
}
