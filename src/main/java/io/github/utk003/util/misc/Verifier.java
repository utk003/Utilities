package io.github.utk003.util.misc;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * An abstract utility class for verification functions.
 * <p>
 * This class provides these methods:
 * <ul>
 * <li>The {@code fail} methods, which always throw a {@link VerificationException}.
 * <li>The {@code requireTrue} methods, which throw a {@link VerificationException}
 *     if and only if the given boolean expression evaluates to {@code false}.
 * <li>The {@code requireFalse} methods, which throw a {@link VerificationException}
 *     if and only if the given boolean expression evaluates to {@code true}.
 * <li>The {@code requireNull} methods, which throw a {@link VerificationException}
 *     if and only if the given object expression is not {@code null}.
 * <li>The {@code requireNotNull} methods, which throw a {@link VerificationException}
 *     if and only if the given object expression is {@code null}.
 * <li>The {@code requireEqual} methods, which throw a {@link VerificationException}
 *     if and only if the given objects or primitives do not match.
 * <li>The {@code requireNotEqual} methods, which throw a {@link VerificationException}
 *     if and only if the given objects or primitives do match.
 * </ul>
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version February 25, 2021
 * @since 1.2.0
 */
public class Verifier {
    private Verifier() {
    }

    /**
     * Throws a {@link VerificationException}
     *
     * @throws VerificationException Always
     */
    @Contract(" -> fail")
    public static void fail() {
        throw new VerificationException();
    }
    /**
     * Throws a {@link VerificationException} with the specified message.
     *
     * @param message The exception's message
     * @throws VerificationException Always
     */
    @Contract("_ -> fail")
    public static void fail(String message) {
        throw new VerificationException(message);
    }

    /**
     * Throws a {@link VerificationException} (with no message)
     * if and only if the given boolean is not {@code true}.
     *
     * @param bool The boolean to check
     * @throws VerificationException If the boolean is not {@code true}
     */
    @Contract("false -> fail")
    public static void requireTrue(boolean bool) {
        if (!bool) fail();
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the given boolean is not {@code true}.
     *
     * @param bool    The boolean to check
     * @param message The message for the exception
     * @throws VerificationException If the boolean is not {@code true}
     */
    @Contract("false,_ -> fail")
    public static void requireTrue(boolean bool, String message) {
        if (!bool) fail(message);
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the given boolean is not {@code false}.
     *
     * @param bool The boolean to check
     * @throws VerificationException If the boolean is not {@code false}
     */
    @Contract("true -> fail")
    public static void requireFalse(boolean bool) {
        if (bool) fail();
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the given boolean is not {@code false}.
     *
     * @param bool    The boolean to check
     * @param message The message for the exception
     * @throws VerificationException If the boolean is not {@code false}
     */
    @Contract("true,_ -> fail")
    public static void requireFalse(boolean bool, String message) {
        if (bool) fail(message);
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the given object is not {@code null}.
     *
     * @param obj The object to check
     * @throws VerificationException If the object is not {@code null}
     */
    @Contract("!null -> fail")
    public static void requireNull(@Nullable Object obj) {
        if (obj != null) fail();
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the given object is not {@code null}.
     *
     * @param obj     The object to check
     * @param message The message for the exception
     * @throws VerificationException If the object is not {@code null}
     */
    @Contract("!null,_ -> fail")
    public static void requireNull(@Nullable Object obj, String message) {
        if (obj != null) fail(message);
    }

    /**
     * Throws an {@link VerificationException} (with no message) if and only if
     * the given object is not {@code not-null} (or, equivalently, is {@code null}).
     * <p>
     * This method is identical to {@link java.util.Objects#requireNonNull(Object)}.
     *
     * @param <T> The type of the object to check
     * @param obj The type-{@code T} argument to check
     * @return The argument (guaranteed to be not {@code null})
     * @throws VerificationException If the {@code Object} argument is {@code null}
     * @see java.util.Objects#requireNonNull(Object)
     */
    @Contract("null -> fail")
    public static <T> @NotNull T requireNotNull(@Nullable T obj) {
        if (obj == null) fail();
        return obj;
    }
    /**
     * Throws an {@link VerificationException} (with the specified message) if and only if
     * the given object is not {@code not-null} (or, equivalently, is {@code null}).
     * <p>
     * This method is identical to {@link java.util.Objects#requireNonNull(Object, String)}.
     *
     * @param <T>     The type of the object to check
     * @param obj     The type-{@code T} argument to check
     * @param message The message for the exception
     * @return The argument (guaranteed to be not {@code null})
     * @throws VerificationException If the {@code Object} argument is {@code null}
     * @see java.util.Objects#requireNonNull(Object, String)
     */
    @Contract("null,_ -> fail")
    public static <T> @NotNull T requireNotNull(@Nullable T obj, String message) {
        if (obj == null) fail(message);
        return obj;
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two {@code boolean} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @return The common value of the two arguments
     * @throws VerificationException If the two arguments are not equal
     */
    public static boolean requireEqual(boolean arg1, boolean arg2) {
        if (arg1 != arg2) fail();
        return arg1;
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two {@code boolean} primitive arguments are not equal,
     * and returns the common value otherwise.
     *
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @return The common value of the two arguments
     * @throws VerificationException If the two arguments are not equal
     */
    public static boolean requireEqual(boolean arg1, boolean arg2, String message) {
        if (arg1 != arg2) fail(message);
        return arg1;
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two {@code boolean} primitive arguments are equal.
     *
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotEqual(boolean arg1, boolean arg2) {
        if (arg1 == arg2) fail();
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two {@code boolean} primitive arguments are equal.
     *
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotEqual(boolean arg1, boolean arg2, String message) {
        if (arg1 == arg2) fail(message);
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two {@code int} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @return The common value of the two arguments
     * @throws VerificationException If the two arguments are not equal
     */
    public static int requireEqual(int arg1, int arg2) {
        if (arg1 != arg2) fail();
        return arg1;
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two {@code int} primitive arguments are not equal,
     * and returns the common value otherwise.
     *
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @return The common value of the two arguments
     * @throws VerificationException If the two arguments are not equal
     */
    public static int requireEqual(int arg1, int arg2, String message) {
        if (arg1 != arg2) fail(message);
        return arg1;
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two {@code int} primitive arguments are equal.
     *
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotEqual(int arg1, int arg2) {
        if (arg1 == arg2) fail();
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two {@code int} primitive arguments are equal.
     *
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotEqual(int arg1, int arg2, String message) {
        if (arg1 == arg2) fail(message);
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two {@code long} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @return The common value of the two arguments
     * @throws VerificationException If the two arguments are not equal
     */
    public static long requireEqual(long arg1, long arg2) {
        if (arg1 != arg2) fail();
        return arg1;
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two {@code long} primitive arguments are not equal,
     * and returns the common value otherwise.
     *
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @return The common value of the two arguments
     * @throws VerificationException If the two arguments are not equal
     */
    public static long requireEqual(long arg1, long arg2, String message) {
        if (arg1 != arg2) fail(message);
        return arg1;
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two {@code long} primitive arguments are equal.
     *
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotEqual(long arg1, long arg2) {
        if (arg1 == arg2) fail();
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two {@code long} primitive arguments are equal.
     *
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotEqual(long arg1, long arg2, String message) {
        if (arg1 == arg2) fail(message);
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two {@code double} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @return The common value of the two arguments
     * @throws VerificationException If the two arguments are not equal
     */
    public static double requireEqual(double arg1, double arg2) {
        if (arg1 != arg2) fail();
        return arg1;
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two {@code double} primitive arguments are not equal,
     * and returns the common value otherwise.
     *
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @return The common value of the two arguments
     * @throws VerificationException If the two arguments are not equal
     */
    public static double requireEqual(double arg1, double arg2, String message) {
        if (arg1 != arg2) fail(message);
        return arg1;
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two {@code double} primitive arguments are equal.
     *
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotEqual(double arg1, double arg2) {
        if (arg1 == arg2) fail();
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two {@code double} primitive arguments are equal.
     *
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotEqual(double arg1, double arg2, String message) {
        if (arg1 == arg2) fail(message);
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two {@code float} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @return The common value of the two arguments
     * @throws VerificationException If the two arguments are not equal
     */
    public static float requireEqual(float arg1, float arg2) {
        if (arg1 != arg2) fail();
        return arg1;
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two {@code float} primitive arguments are not equal,
     * and returns the common value otherwise.
     *
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @return The common value of the two arguments
     * @throws VerificationException If the two arguments are not equal
     */
    public static float requireEqual(float arg1, float arg2, String message) {
        if (arg1 != arg2) fail(message);
        return arg1;
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two {@code float} primitive arguments are equal.
     *
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotEqual(float arg1, float arg2) {
        if (arg1 == arg2) fail();
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two {@code float} primitive arguments are equal.
     *
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotEqual(float arg1, float arg2, String message) {
        if (arg1 == arg2) fail(message);
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two object arguments are not equal.
     * <p>
     * This verification method uses {@link Objects#equals(Object, Object)}
     * to check for equality. For memory-based equality (using {@code ==}),
     * use {@link #requireExactlyEqual(Object, Object)} instead.
     *
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @throws VerificationException If the two arguments are not equal
     */
    public static void requireEqual(Object arg1, Object arg2) {
        if (!Objects.equals(arg1, arg2)) fail();
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two object arguments are not equal.
     * <p>
     * This verification method uses {@link Objects#equals(Object, Object)}
     * to check for equality. For memory-based equality (using {@code ==}),
     * use {@link #requireExactlyEqual(Object, Object, String)} instead.
     *
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @throws VerificationException If the two arguments are not equal
     */
    public static void requireEqual(Object arg1, Object arg2, String message) {
        if (!Objects.equals(arg1, arg2)) fail(message);
    }
    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two object arguments are equal.
     * <p>
     * This verification method uses {@link Objects#equals(Object, Object)}
     * to check for equality. For memory-based equality (using {@code ==}),
     * use {@link #requireNotExactlyEqual(Object, Object)} instead.
     *
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotEqual(Object arg1, Object arg2) {
        if (Objects.equals(arg1, arg2)) fail();
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two object arguments are equal.
     * <p>
     * This verification method uses {@link Objects#equals(Object, Object)}
     * to check for equality. For memory-based equality (using {@code ==}),
     * use {@link #requireNotExactlyEqual(Object, Object, String)} instead.
     *
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotEqual(Object arg1, Object arg2, String message) {
        if (Objects.equals(arg1, arg2)) fail(message);
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two object arguments are not equal,
     * and returns the common value otherwise.
     * <p>
     * This verification method uses {@code ==} to check for equality.
     * For object-based equality verification (using {@link Objects#equals(Object, Object)}),
     * use {@link #requireEqual(Object, Object)} instead.
     *
     * @param <T>  The type of the object to check
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @return The common value of the two arguments
     * @throws VerificationException If the two arguments are not equal
     */
    public static <T> T requireExactlyEqual(T arg1, T arg2) {
        if (arg1 != arg2) fail();
        return arg1;
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two object arguments are not equal, and returns
     * the common value otherwise.
     * <p>
     * This verification method uses {@code ==} to check for equality.
     * For object-based equality verification (using {@link Objects#equals(Object, Object)}),
     * use {@link #requireEqual(Object, Object, String)} instead.
     *
     * @param <T>     The type of the object to check
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @return The common value of the two arguments
     * @throws VerificationException If the two arguments are not equal
     */
    public static <T> T requireExactlyEqual(T arg1, T arg2, String message) {
        if (arg1 != arg2) fail(message);
        return arg1;
    }

    /**
     * Throws an {@link VerificationException} (with no message)
     * if and only if the two object arguments are equal.
     * <p>
     * This verification method uses {@code ==} to check for equality.
     * For object-based equality verification (using {@link Objects#equals(Object, Object)}),
     * use {@link #requireNotEqual(Object, Object)} instead.
     *
     * @param arg1 The first argument to check
     * @param arg2 The second argument to check
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotExactlyEqual(Object arg1, Object arg2) {
        if (arg1 == arg2) fail();
    }
    /**
     * Throws an {@link VerificationException} (with the specified message)
     * if and only if the two object arguments are equal.
     * <p>
     * This verification method uses {@code ==} to check for equality.
     * For object-based equality verification (using {@link Objects#equals(Object, Object)}),
     * use {@link #requireNotEqual(Object, Object, String)} instead.
     *
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param message The message for the exception
     * @throws VerificationException If the two arguments are equal
     */
    public static void requireNotExactlyEqual(Object arg1, Object arg2, String message) {
        if (arg1 == arg2) fail(message);
    }

}
