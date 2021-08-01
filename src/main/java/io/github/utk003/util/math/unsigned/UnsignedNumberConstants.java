package io.github.utk003.util.math.unsigned;

import io.github.utk003.util.misc.Verifier;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@ApiStatus.Experimental
@RequiresDocumentation
abstract class UnsignedNumberConstants {
    private UnsignedNumberConstants() {
    }

    // 255 = 0xFF = 2^8 - 1
    public static final long MAX_UNSIGNED_BYTE_VALUE = 255;

    // 65_535 = 0xFFFF = 2^16 - 1
    public static final long MAX_UNSIGNED_SHORT_VALUE = 65_535;

    // 4_294_967_295L = 0xFFFFFFFFL = 2^32 - 1
    public static final long MAX_UNSIGNED_INT_VALUE = 4_294_967_295L;

    // 18446744073709551615 = 2^64 - 1
    public static final @NotNull BigInteger MAX_UNSIGNED_LONG_VALUE = new BigInteger("18446744073709551615");

    static {
        // verify max values
        Verifier.requireEqual(MAX_UNSIGNED_BYTE_VALUE, 2 * Byte.MAX_VALUE + 1, "Incorrect max unsigned byte value");
        Verifier.requireEqual(MAX_UNSIGNED_SHORT_VALUE, 2 * Short.MAX_VALUE + 1, "Incorrect max unsigned short value");
        Verifier.requireEqual(MAX_UNSIGNED_INT_VALUE, 2L * Integer.MAX_VALUE + 1, "Incorrect max unsigned int value");

        BigInteger maxUnsignedLong = BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.valueOf(2)).add(BigInteger.ONE);
        Verifier.requireEqual(MAX_UNSIGNED_LONG_VALUE, maxUnsignedLong, "Incorrect max unsigned long value");
    }

    static final String[] BINARY_STRING_PADDING_ZEROS = new String[65];
    static {
        BINARY_STRING_PADDING_ZEROS[0] = "";
        for (int i = 1; i <= 64; i++)
            BINARY_STRING_PADDING_ZEROS[i] = BINARY_STRING_PADDING_ZEROS[i - 1] + "0";
    }

    private static final @NotNull Map<String, BigInteger> BIG_INTEGER_CACHE = new HashMap<>();
    static {
        for (int i = 0; i < 10; i++)
            BIG_INTEGER_CACHE.put("" + i, BigInteger.valueOf(i));
    }

    public static synchronized @NotNull BigInteger getBigInteger(@NotNull String num) {
        return BIG_INTEGER_CACHE.computeIfAbsent(num, BigInteger::new);
    }
}
