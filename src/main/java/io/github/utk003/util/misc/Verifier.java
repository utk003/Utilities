/*
MIT License

Copyright (c) 2021 Utkarsh Priyam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package io.github.utk003.util.misc;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * An abstract utility class for verification functions.
 * <p>
 * This class provides methods for:
 * <ul>
 * <li>Failing immediately with either a {@link VerificationException},
 *     a {@link VerifierFailedInstantiationException}, or a specified
 *     {@code Exception} subclass,
 * <li>Checking if a condition is {@code true} or {@code false},
 * <li>Checking if an object is {@code null} or not-{@code null},
 * <li>Checking if two primitives are equal,
 * <li>Checking if two objects are equal via {@link Object#equals(Object)},
 * <li>And checking if two object pointers are equal (using {@code ==}).
 * </ul>
 * <p>
 * Any call to this class's verification method(s) can specify any combination of
 * a {@code String} error message and an {@code Exception} subclass as the mode
 * of failure for that single verification. Alternatively, the call can simply
 * provide an {@code Exception} instance for the method to throw in case of failure.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 22, 2021
 * @see VerificationException
 * @since 1.2.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public abstract class Verifier {
    private Verifier() {
    }

    /**
     * Throws a {@link VerificationException}.
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
    public static void fail(@NotNull String message) {
        throw new VerificationException(message);
    }
    /**
     * Throws an exception of type {@code E}.
     *
     * @param <E>       The type of the exception to throw
     * @param exception The exception instance to throw
     * @throws E Always
     */
    @Contract("_ -> fail")
    public static <E extends Exception> void fail(@NotNull E exception) throws E {
        throw exception;
    }
    /**
     * Throws an exception of type {@code E}.
     *
     * @param <E>   The type of the exception to throw
     * @param clazz The {@code Class} object of the exception to throw
     * @throws E Always
     */
    @Contract("_ -> fail")
    public static <E extends Exception> void fail(@NotNull Class<E> clazz) throws E {
        E instance = null;
        try {
            instance = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException ignored) {
        }

        if (instance != null) throw instance;
        throw new VerifierFailedInstantiationException(clazz);
    }
    /**
     * Throws an exception of type {@code E} with the specified message.
     *
     * @param <E>     The type of the exception to throw
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @throws E Always
     */
    @Contract("_,_ -> fail")
    public static <E extends Exception> void fail(@NotNull Class<E> clazz, @NotNull String message) throws E {
        E instance = null;
        try {
            Constructor<E> constructor = clazz.getDeclaredConstructor(String.class);
            if (constructor.isAccessible())
                instance = constructor.newInstance(message);
            else {
                constructor.setAccessible(true);
                instance = constructor.newInstance(message);
                constructor.setAccessible(false);
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException ignored) {
        }

        if (instance != null) throw instance;
        throw new VerifierFailedInstantiationException(clazz, message);
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
    public static void requireTrue(boolean bool, @NotNull String message) {
        if (!bool) fail(message);
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the given boolean is not {@code true}.
     *
     * @param <E>       The type of the exception to throw
     * @param bool      The boolean to check
     * @param exception The exception instance to throw
     * @throws E If the boolean is not {@code true}
     */
    @Contract("false,_ -> fail")
    public static <E extends Exception> void requireTrue(boolean bool, @NotNull E exception) throws E {
        if (!bool) fail(exception);
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the given boolean is not {@code true}.
     *
     * @param <E>   The type of the exception to throw
     * @param bool  The boolean to check
     * @param clazz The {@code Class} object of the exception to throw
     * @throws E If the boolean is not {@code true}
     */
    @Contract("false,_ -> fail")
    public static <E extends Exception> void requireTrue(boolean bool, @NotNull Class<E> clazz) throws E {
        if (!bool) fail(clazz);
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the given boolean is not {@code true}.
     *
     * @param <E>     The type of the exception to throw
     * @param bool    The boolean to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @throws E If the boolean is not {@code true}
     */
    @Contract("false,_,_ -> fail")
    public static <E extends Exception> void requireTrue(boolean bool, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (!bool) fail(clazz, message);
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
    public static void requireFalse(boolean bool, @NotNull String message) {
        if (bool) fail(message);
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the given boolean is not {@code false}.
     *
     * @param <E>       The type of the exception to throw
     * @param bool      The boolean to check
     * @param exception The exception instance to throw
     * @throws E If the boolean is not {@code false}
     */
    @Contract("true,_ -> fail")
    public static <E extends Exception> void requireFalse(boolean bool, @NotNull E exception) throws E {
        if (bool) fail(exception);
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the given boolean is not {@code false}.
     *
     * @param <E>   The type of the exception to throw
     * @param bool  The boolean to check
     * @param clazz The {@code Class} object of the exception to throw
     * @throws E If the boolean is not {@code false}
     */
    @Contract("true,_ -> fail")
    public static <E extends Exception> void requireFalse(boolean bool, @NotNull Class<E> clazz) throws E {
        if (bool) fail(clazz);
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the given boolean is not {@code false}.
     *
     * @param <E>     The type of the exception to throw
     * @param bool    The boolean to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @throws E If the boolean is not {@code false}
     */
    @Contract("true,_,_ -> fail")
    public static <E extends Exception> void requireFalse(boolean bool, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (bool) fail(clazz, message);
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
    public static void requireNull(@Nullable Object obj, @NotNull String message) {
        if (obj != null) fail(message);
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the given object is not {@code null}.
     *
     * @param <E>       The type of the exception to throw
     * @param obj       The object to check
     * @param exception The exception instance to throw
     * @throws E If the object is not {@code null}
     */
    @Contract("!null,_ -> fail")
    public static <E extends Exception> void requireNull(@Nullable Object obj, @NotNull E exception) throws E {
        if (obj != null) fail(exception);
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the given object is not {@code null}.
     *
     * @param <E>   The type of the exception to throw
     * @param obj   The object to check
     * @param clazz The {@code Class} object of the exception to throw
     * @throws E If the object is not {@code null}
     */
    @Contract("!null,_ -> fail")
    public static <E extends Exception> void requireNull(@Nullable Object obj, @NotNull Class<E> clazz) throws E {
        if (obj != null) fail(clazz);
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the given object is not {@code null}.
     *
     * @param <E>     The type of the exception to throw
     * @param obj     The object to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @throws E If the object is not {@code null}
     */
    @Contract("!null,_,_ -> fail")
    public static <E extends Exception> void requireNull(@Nullable Object obj, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (obj != null) fail(clazz, message);
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
    public static <T> @NotNull T requireNotNull(@Nullable T obj, @NotNull String message) {
        if (obj == null) fail(message);
        return obj;
    }
    /**
     * Throws the given exception of type {@code E} if and only if
     * the given object is not {@code not-null} (or, equivalently, is {@code null}).
     *
     * @param <T>       The type of the object to check
     * @param <E>       The type of the exception to throw
     * @param obj       The type-{@code T} argument to check
     * @param exception The exception instance to throw
     * @return The argument (guaranteed to be not {@code null})
     * @throws E If the {@code Object} argument is {@code null}
     */
    @Contract("null,_ -> fail")
    public static <T, E extends Exception> @NotNull T requireNotNull(@Nullable T obj, @NotNull E exception) throws E {
        if (obj == null) fail(exception);
        return obj;
    }
    /**
     * Throws an exception of type {@code E} (with no message) if and only if
     * the given object is not {@code not-null} (or, equivalently, is {@code null}).
     *
     * @param <T>   The type of the object to check
     * @param <E>   The type of the exception to throw
     * @param obj   The type-{@code T} argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @return The argument (guaranteed to be not {@code null})
     * @throws E If the {@code Object} argument is {@code null}
     */
    @Contract("null,_ -> fail")
    public static <T, E extends Exception> @NotNull T requireNotNull(@Nullable T obj, @NotNull Class<E> clazz) throws E {
        if (obj == null) fail(clazz);
        return obj;
    }
    /**
     * Throws an exception of type {@code E} (with the specified message) if and only if
     * the given object is not {@code not-null} (or, equivalently, is {@code null}).
     *
     * @param <T>     The type of the object to check
     * @param <E>     The type of the exception to throw
     * @param obj     The type-{@code T} argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @return The argument (guaranteed to be not {@code null})
     * @throws E If the {@code Object} argument is {@code null}
     */
    @Contract("null,_,_ -> fail")
    public static <T, E extends Exception> @NotNull T requireNotNull(@Nullable T obj, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (obj == null) fail(clazz, message);
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
    public static boolean requireEqual(boolean arg1, boolean arg2, @NotNull String message) {
        if (arg1 != arg2) fail(message);
        return arg1;
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two {@code boolean} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> boolean requireEqual(boolean arg1, boolean arg2, @NotNull E exception) throws E {
        if (arg1 != arg2) fail(exception);
        return arg1;
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two {@code boolean} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> boolean requireEqual(boolean arg1, boolean arg2, @NotNull Class<E> clazz) throws E {
        if (arg1 != arg2) fail(clazz);
        return arg1;
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two {@code boolean} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> boolean requireEqual(boolean arg1, boolean arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (arg1 != arg2) fail(clazz, message);
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
    public static void requireNotEqual(boolean arg1, boolean arg2, @NotNull String message) {
        if (arg1 == arg2) fail(message);
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two {@code boolean} primitive arguments are equal.
     *
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(boolean arg1, boolean arg2, @NotNull E exception) throws E {
        if (arg1 == arg2) fail(exception);
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two {@code boolean} primitive arguments are equal.
     *
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(boolean arg1, boolean arg2, @NotNull Class<E> clazz) throws E {
        if (arg1 == arg2) fail(clazz);
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two {@code boolean} primitive arguments are equal.
     *
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(boolean arg1, boolean arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (arg1 == arg2) fail(clazz, message);
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
    public static int requireEqual(int arg1, int arg2, @NotNull String message) {
        if (arg1 != arg2) fail(message);
        return arg1;
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two {@code int} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> int requireEqual(int arg1, int arg2, @NotNull E exception) throws E {
        if (arg1 != arg2) fail(exception);
        return arg1;
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two {@code int} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> int requireEqual(int arg1, int arg2, @NotNull Class<E> clazz) throws E {
        if (arg1 != arg2) fail(clazz);
        return arg1;
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two {@code int} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> int requireEqual(int arg1, int arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (arg1 != arg2) fail(clazz, message);
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
    public static void requireNotEqual(int arg1, int arg2, @NotNull String message) {
        if (arg1 == arg2) fail(message);
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two {@code int} primitive arguments are equal.
     *
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(int arg1, int arg2, @NotNull E exception) throws E {
        if (arg1 == arg2) fail(exception);
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two {@code int} primitive arguments are equal.
     *
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(int arg1, int arg2, @NotNull Class<E> clazz) throws E {
        if (arg1 == arg2) fail(clazz);
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two {@code int} primitive arguments are equal.
     *
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(int arg1, int arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (arg1 == arg2) fail(clazz, message);
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
    public static long requireEqual(long arg1, long arg2, @NotNull String message) {
        if (arg1 != arg2) fail(message);
        return arg1;
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two {@code long} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> long requireEqual(long arg1, long arg2, @NotNull E exception) throws E {
        if (arg1 != arg2) fail(exception);
        return arg1;
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two {@code long} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> long requireEqual(long arg1, long arg2, @NotNull Class<E> clazz) throws E {
        if (arg1 != arg2) fail(clazz);
        return arg1;
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two {@code long} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> long requireEqual(long arg1, long arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (arg1 != arg2) fail(clazz, message);
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
    public static void requireNotEqual(long arg1, long arg2, @NotNull String message) {
        if (arg1 == arg2) fail(message);
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two {@code long} primitive arguments are equal.
     *
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(long arg1, long arg2, @NotNull E exception) throws E {
        if (arg1 == arg2) fail(exception);
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two {@code long} primitive arguments are equal.
     *
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(long arg1, long arg2, @NotNull Class<E> clazz) throws E {
        if (arg1 == arg2) fail(clazz);
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two {@code long} primitive arguments are equal.
     *
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(long arg1, long arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (arg1 == arg2) fail(clazz, message);
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
    public static double requireEqual(double arg1, double arg2, @NotNull String message) {
        if (arg1 != arg2) fail(message);
        return arg1;
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two {@code double} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> double requireEqual(double arg1, double arg2, @NotNull E exception) throws E {
        if (arg1 != arg2) fail(exception);
        return arg1;
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two {@code double} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> double requireEqual(double arg1, double arg2, @NotNull Class<E> clazz) throws E {
        if (arg1 != arg2) fail(clazz);
        return arg1;
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two {@code double} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> double requireEqual(double arg1, double arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (arg1 != arg2) fail(clazz, message);
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
    public static void requireNotEqual(double arg1, double arg2, @NotNull String message) {
        if (arg1 == arg2) fail(message);
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two {@code double} primitive arguments are equal.
     *
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(double arg1, double arg2, @NotNull E exception) throws E {
        if (arg1 == arg2) fail(exception);
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two {@code double} primitive arguments are equal.
     *
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(double arg1, double arg2, @NotNull Class<E> clazz) throws E {
        if (arg1 == arg2) fail(clazz);
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two {@code double} primitive arguments are equal.
     *
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(double arg1, double arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (arg1 == arg2) fail(clazz, message);
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
    public static float requireEqual(float arg1, float arg2, @NotNull String message) {
        if (arg1 != arg2) fail(message);
        return arg1;
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two {@code float} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> float requireEqual(float arg1, float arg2, @NotNull E exception) throws E {
        if (arg1 != arg2) fail(exception);
        return arg1;
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two {@code float} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> float requireEqual(float arg1, float arg2, @NotNull Class<E> clazz) throws E {
        if (arg1 != arg2) fail(clazz);
        return arg1;
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two {@code float} primitive arguments
     * are not equal, and returns the common value otherwise.
     *
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> float requireEqual(float arg1, float arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (arg1 != arg2) fail(clazz, message);
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
    public static void requireNotEqual(float arg1, float arg2, @NotNull String message) {
        if (arg1 == arg2) fail(message);
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two {@code float} primitive arguments are equal.
     *
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(float arg1, float arg2, @NotNull E exception) throws E {
        if (arg1 == arg2) fail(exception);
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two {@code float} primitive arguments are equal.
     *
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(float arg1, float arg2, @NotNull Class<E> clazz) throws E {
        if (arg1 == arg2) fail(clazz);
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two {@code float} primitive arguments are equal.
     *
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(float arg1, float arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (arg1 == arg2) fail(clazz, message);
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
    public static void requireEqual(Object arg1, Object arg2, @NotNull String message) {
        if (!Objects.equals(arg1, arg2)) fail(message);
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two object arguments are not equal.
     * <p>
     * This verification method uses {@link Objects#equals(Object, Object)}
     * to check for equality. For memory-based equality (using {@code ==}),
     * use {@link #requireExactlyEqual(Object, Object, Exception)} instead.
     *
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> void requireEqual(Object arg1, Object arg2, @NotNull E exception) throws E {
        if (!Objects.equals(arg1, arg2)) fail(exception);
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two object arguments are not equal.
     * <p>
     * This verification method uses {@link Objects#equals(Object, Object)}
     * to check for equality. For memory-based equality (using {@code ==}),
     * use {@link #requireExactlyEqual(Object, Object, Class)} instead.
     *
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> void requireEqual(Object arg1, Object arg2, @NotNull Class<E> clazz) throws E {
        if (!Objects.equals(arg1, arg2)) fail(clazz);
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two object arguments are not equal.
     * <p>
     * This verification method uses {@link Objects#equals(Object, Object)}
     * to check for equality. For memory-based equality (using {@code ==}),
     * use {@link #requireExactlyEqual(Object, Object, Class, String)} instead.
     *
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @throws E If the two arguments are not equal
     */
    public static <E extends Exception> void requireEqual(Object arg1, Object arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (!Objects.equals(arg1, arg2)) fail(clazz, message);
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
    public static void requireNotEqual(Object arg1, Object arg2, @NotNull String message) {
        if (Objects.equals(arg1, arg2)) fail(message);
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two object arguments are equal.
     * <p>
     * This verification method uses {@link Objects#equals(Object, Object)}
     * to check for equality. For memory-based equality (using {@code ==}),
     * use {@link #requireNotExactlyEqual(Object, Object, Exception)} instead.
     *
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(Object arg1, Object arg2, @NotNull E exception) throws E {
        if (Objects.equals(arg1, arg2)) fail(exception);
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two object arguments are equal.
     * <p>
     * This verification method uses {@link Objects#equals(Object, Object)}
     * to check for equality. For memory-based equality (using {@code ==}),
     * use {@link #requireNotExactlyEqual(Object, Object, Class)} instead.
     *
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(Object arg1, Object arg2, @NotNull Class<E> clazz) throws E {
        if (Objects.equals(arg1, arg2)) fail(clazz);
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two object arguments are equal.
     * <p>
     * This verification method uses {@link Objects#equals(Object, Object)}
     * to check for equality. For memory-based equality (using {@code ==}),
     * use {@link #requireNotExactlyEqual(Object, Object, Class, String)} instead.
     *
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotEqual(Object arg1, Object arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (Objects.equals(arg1, arg2)) fail(clazz, message);
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
    public static <T> T requireExactlyEqual(T arg1, T arg2, @NotNull String message) {
        if (arg1 != arg2) fail(message);
        return arg1;
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two object arguments are not equal,
     * and returns the common value otherwise.
     * <p>
     * This verification method uses {@code ==} to check for equality.
     * For object-based equality verification (using {@link Objects#equals(Object, Object)}),
     * use {@link #requireEqual(Object, Object, Exception)} instead.
     *
     * @param <T>       The type of the object to check
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <T, E extends Exception> T requireExactlyEqual(T arg1, T arg2, @NotNull E exception) throws E {
        if (arg1 != arg2) fail(exception);
        return arg1;
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two object arguments are not equal,
     * and returns the common value otherwise.
     * <p>
     * This verification method uses {@code ==} to check for equality.
     * For object-based equality verification (using {@link Objects#equals(Object, Object)}),
     * use {@link #requireEqual(Object, Object, Class)} instead.
     *
     * @param <T>   The type of the object to check
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <T, E extends Exception> T requireExactlyEqual(T arg1, T arg2, @NotNull Class<E> clazz) throws E {
        if (arg1 != arg2) fail(clazz);
        return arg1;
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two object arguments are not equal,
     * and returns the common value otherwise.
     * <p>
     * This verification method uses {@code ==} to check for equality.
     * For object-based equality verification (using {@link Objects#equals(Object, Object)}),
     * use {@link #requireEqual(Object, Object, Class, String)} instead.
     *
     * @param <T>     The type of the object to check
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @return The common value of the two arguments
     * @throws E If the two arguments are not equal
     */
    public static <T, E extends Exception> T requireExactlyEqual(T arg1, T arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (arg1 != arg2) fail(clazz, message);
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
    public static void requireNotExactlyEqual(Object arg1, Object arg2, @NotNull String message) {
        if (arg1 == arg2) fail(message);
    }
    /**
     * Throws the given exception of type {@code E}
     * if and only if the two object arguments are equal.
     * <p>
     * This verification method uses {@code ==} to check for equality.
     * For object-based equality verification (using {@link Objects#equals(Object, Object)}),
     * use {@link #requireNotEqual(Object, Object, Exception)} instead.
     *
     * @param <E>       The type of the exception to throw
     * @param arg1      The first argument to check
     * @param arg2      The second argument to check
     * @param exception The exception instance to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotExactlyEqual(Object arg1, Object arg2, @NotNull E exception) throws E {
        if (arg1 == arg2) fail(exception);
    }
    /**
     * Throws an exception of type {@code E} (with no message)
     * if and only if the two object arguments are equal.
     * <p>
     * This verification method uses {@code ==} to check for equality.
     * For object-based equality verification (using {@link Objects#equals(Object, Object)}),
     * use {@link #requireNotEqual(Object, Object, Class)} instead.
     *
     * @param <E>   The type of the exception to throw
     * @param arg1  The first argument to check
     * @param arg2  The second argument to check
     * @param clazz The {@code Class} object of the exception to throw
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotExactlyEqual(Object arg1, Object arg2, @NotNull Class<E> clazz) throws E {
        if (arg1 == arg2) fail(clazz);
    }
    /**
     * Throws an exception of type {@code E} (with the specified message)
     * if and only if the two object arguments are equal.
     * <p>
     * This verification method uses {@code ==} to check for equality.
     * For object-based equality verification (using {@link Objects#equals(Object, Object)}),
     * use {@link #requireNotEqual(Object, Object, Class, String)} instead.
     *
     * @param <E>     The type of the exception to throw
     * @param arg1    The first argument to check
     * @param arg2    The second argument to check
     * @param clazz   The {@code Class} object of the exception to throw
     * @param message The message for the exception
     * @throws E If the two arguments are equal
     */
    public static <E extends Exception> void requireNotExactlyEqual(Object arg1, Object arg2, @NotNull Class<E> clazz, @NotNull String message) throws E {
        if (arg1 == arg2) fail(clazz, message);
    }
}
