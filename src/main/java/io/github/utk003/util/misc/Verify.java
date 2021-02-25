/*
MIT License

Copyright (c) 2020-2021 Utkarsh Priyam

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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

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
 * </ul>
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version February 21, 2021
 * @since 1.0.2
 * @deprecated Since 1.2.0
 */
@Deprecated
public abstract class Verify {
    private Verify() {
    }

    /**
     * Throws a {@link VerificationException}
     *
     * @throws VerificationException Always
     * @since 1.1.0
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
     * @since 1.1.0
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
     * For a method with nearly-identical functionality that returns the argument as well,
     * use {@link java.util.Objects#requireNonNull(Object)} instead.
     *
     * @param obj The {@code Object} argument to check
     * @throws VerificationException If the {@code Object} argument is {@code null}
     * @see java.util.Objects#requireNonNull(Object)
     */
    @Contract("null -> fail")
    public static void requireNotNull(@Nullable Object obj) {
        if (obj == null) fail();
    }
    /**
     * Throws an {@link VerificationException} (with the specified message) if and only if
     * the given object is not {@code not-null} (or, equivalently, is {@code null}).
     * <p>
     * For a method with nearly-identical functionality that returns the argument as well,
     * use {@link java.util.Objects#requireNonNull(Object, String)} instead.
     *
     * @param obj     The {@code Object} argument to check
     * @param message The message for the exception
     * @throws VerificationException If the {@code Object} argument is {@code null}
     * @see java.util.Objects#requireNonNull(Object, String)
     */
    @Contract("null,_ -> fail")
    public static void requireNotNull(@Nullable Object obj, String message) {
        if (obj == null) fail(message);
    }

    /**
     * A custom runtime exception for use by the
     * {@link Verify} class for failed verifications.
     *
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version February 21, 2021
     * @since 1.1.0
     * @deprecated Since 1.2.0
     */
    @Deprecated
    public static class VerificationException extends RuntimeException {
        /**
         * Constructs a new verification exception
         *
         * @see RuntimeException#RuntimeException()
         */
        public VerificationException() {
            super();
        }

        /**
         * Constructs a new runtime exception with the specified detail message
         *
         * @param message The detail message
         * @see RuntimeException#RuntimeException(String)
         */
        public VerificationException(String message) {
            super(message);
        }

        /**
         * Constructs a new runtime exception with the specified detail message and cause
         *
         * @param message The detail message
         * @param cause   The cause
         * @see RuntimeException#RuntimeException(String, Throwable)
         */
        public VerificationException(String message, Throwable cause) {
            super(message, cause);
        }

        /**
         * Constructs a new runtime exception with the specified cause
         *
         * @param cause The cause
         * @see RuntimeException#RuntimeException(Throwable)
         */
        public VerificationException(Throwable cause) {
            super(cause);
        }

        /**
         * Constructs a new runtime exception with the specified detail
         * message, cause, suppression enabled or disabled, and writable
         * stack trace enabled or disabled
         *
         * @param message            The detail message
         * @param cause              The cause
         * @param enableSuppression  Whether or not suppression is enabled or disabled
         * @param writableStackTrace Whether or not the stack trace should be writable
         * @see RuntimeException#RuntimeException(String, Throwable, boolean, boolean)
         */
        protected VerificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
