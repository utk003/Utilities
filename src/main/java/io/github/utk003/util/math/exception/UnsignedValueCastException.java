package io.github.utk003.util.math.exception;

import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

@ScheduledForRelease
@RequiresDocumentation
public class UnsignedValueCastException extends IllegalArgumentException {
    public UnsignedValueCastException() {
        super();
    }

    public UnsignedValueCastException(@NotNull String message) {
        super(message);
    }

    public static @NotNull UnsignedValueCastException getExceptionForCast(@NotNull String from, byte value, @NotNull String to) {
        return new UnsignedValueCastException("Cannot represent " + from + " (" + value + ") as " + to);
    }
    public static @NotNull UnsignedValueCastException getExceptionForCast(@NotNull String from, short value, @NotNull String to) {
        return new UnsignedValueCastException("Cannot represent " + from + " (" + value + ") as " + to);
    }
    public static @NotNull UnsignedValueCastException getExceptionForCast(@NotNull String from, int value, @NotNull String to) {
        return new UnsignedValueCastException("Cannot represent " + from + " (" + value + ") as " + to);
    }
    public static @NotNull UnsignedValueCastException getExceptionForCast(@NotNull String from, long value, @NotNull String to) {
        return new UnsignedValueCastException("Cannot represent " + from + " (" + value + ") as " + to);
    }
    public static @NotNull UnsignedValueCastException getExceptionForCast(@NotNull String from, @NotNull BigInteger value, @NotNull String to) {
        return new UnsignedValueCastException("Cannot represent " + from + " (" + value + ") as " + to);
    }
}
