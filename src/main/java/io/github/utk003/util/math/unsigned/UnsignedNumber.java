package io.github.utk003.util.math.unsigned;

import io.github.utk003.util.math.exception.UnsignedValueCastException;
import io.github.utk003.util.misc.Verifier;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

@ApiStatus.Experimental
@RequiresDocumentation
public abstract class UnsignedNumber extends Number implements Comparable<UnsignedNumber> {
    @Override
    public byte byteValue() throws UnsignedValueCastException {
        return Verifier.requireNotNull(getByteValue(), UnsignedValueCastException.class);
    }
    public abstract @Nullable Byte getByteValue();

    @Override
    public short shortValue() throws UnsignedValueCastException {
        return Verifier.requireNotNull(getShortValue(), UnsignedValueCastException.class);
    }
    public abstract @Nullable Short getShortValue();

    @Override
    public int intValue() throws UnsignedValueCastException {
        return Verifier.requireNotNull(getIntegerValue(), UnsignedValueCastException.class);
    }
    public abstract @Nullable Integer getIntegerValue();

    @Override
    public long longValue() throws UnsignedValueCastException {
        return Verifier.requireNotNull(getLongValue(), UnsignedValueCastException.class);
    }
    public abstract @Nullable Long getLongValue();

    @Override
    public float floatValue() {
        return Float.parseFloat(getExactValue().toString());
    }
    public abstract @NotNull Float getFloatValue();

    @Override
    public double doubleValue() {
        return Double.parseDouble(getExactValue().toString());
    }
    public abstract @NotNull Double getDoubleValue();

    public abstract @NotNull BigInteger getExactValue();
    public abstract @NotNull String asBinaryString();

    @Override
    public abstract @NotNull String toString();

    public static @Nullable UnsignedByte wrapByte(byte signed) {
        return UnsignedByte.wrap(signed);
    }
//    public static @Nullable UnsignedShort wrapShort(short signed) {
//        return UnsignedShort.wrap(signed);
//    }
    public static @Nullable UnsignedInteger wrapInt(int signed) {
        return UnsignedInteger.wrap(signed);
    }
//    public static @Nullable UnsignedLong wrapLong(long signed) {
//        return UnsignedLong.wrap(signed);
//    }

    public static int compare(@NotNull UnsignedNumber unsigned1, @NotNull UnsignedNumber unsigned2) {
        return unsigned1.getExactValue().compareTo(unsigned2.getExactValue());
    }
    @Override
    public int compareTo(@NotNull UnsignedNumber other) {
        return compare(this, other);
    }
}
