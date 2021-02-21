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
 * <li>The {@code requireTrue} methods, which throw an {@link IllegalArgumentException}
 *     if and only if the given boolean expression evaluates to {@code false}.
 * <li>The {@code requireFalse} methods, which throw an {@link IllegalArgumentException}
 *     if and only if the given boolean expression evaluates to {@code true}.
 * <li>The {@code requireNull} methods, which throw an {@link IllegalArgumentException}
 *     if and only if the given object expression is not {@code null}.
 * <li>The {@code requireNotNull} methods, which throw an {@link IllegalArgumentException}
 *     if and only if the given object expression is {@code null}.
 * </ul>
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version January 13, 2021
 * @since 1.0.2
 */
public abstract class Verify {
    private Verify() {
    }

    /**
     * Throws an {@link IllegalArgumentException} if and only if the given
     * boolean argument is not {@code true}.
     * <p>
     * If an exception is thrown, it is initialized with a {@code null} message.
     *
     * @param bool The boolean argument to check
     * @throws IllegalArgumentException If the boolean argument is not {@code true}
     * @see #requireTrue(boolean, String)
     */
    @Contract("false -> fail")
    public static void requireTrue(boolean bool) {
        requireTrue(bool, null);
    }
    /**
     * Throws an {@link IllegalArgumentException} if and only if the given
     * boolean argument is not {@code true}.
     * <p>
     * If an exception is thrown, it is initialized with the specified message.
     *
     * @param bool    The boolean argument to check
     * @param message The message for the exception
     * @throws IllegalArgumentException If the boolean argument is not {@code true}
     */
    @Contract("false,_ -> fail")
    public static void requireTrue(boolean bool, String message) {
        if (!bool)
            throw new IllegalArgumentException(message);
    }

    /**
     * Throws an {@link IllegalArgumentException} if and only if the given
     * boolean argument is not {@code false}.
     * <p>
     * If an exception is thrown, it is initialized with a {@code null} message.
     *
     * @param bool The boolean argument to check
     * @throws IllegalArgumentException If the boolean argument is not {@code false}
     * @see #requireFalse(boolean, String)
     */
    @Contract("true -> fail")
    public static void requireFalse(boolean bool) {
        requireFalse(bool, null);
    }
    /**
     * Throws an {@link IllegalArgumentException} if and only if the given
     * boolean argument is not {@code false}.
     * <p>
     * If an exception is thrown, it is initialized with the specified message.
     *
     * @param bool    The boolean argument to check
     * @param message The message for the exception
     * @throws IllegalArgumentException If the boolean argument is not {@code false}
     */
    @Contract("true,_ -> fail")
    public static void requireFalse(boolean bool, String message) {
        if (bool)
            throw new IllegalArgumentException(message);
    }

    /**
     * Throws an {@link IllegalArgumentException} if and only if the given
     * {@code Object} argument is not {@code null}.
     * <p>
     * If an exception is thrown, it is initialized with a {@code null} message.
     *
     * @param obj The {@code Object} argument to check
     * @throws IllegalArgumentException If the {@code Object} argument is not {@code null}
     * @see #requireNull(Object, String)
     */
    @Contract("!null -> fail")
    public static void requireNull(@Nullable Object obj) {
        requireNull(obj, null);
    }
    /**
     * Throws an {@link IllegalArgumentException} if and only if the given
     * {@code Object} argument is not {@code null}.
     * <p>
     * If an exception is thrown, it is initialized with the specified message.
     *
     * @param obj     The {@code Object} argument to check
     * @param message The message for the exception
     * @throws IllegalArgumentException If the {@code Object} argument is not {@code null}
     */
    @Contract("!null,_ -> fail")
    public static void requireNull(@Nullable Object obj, String message) {
        if (obj != null)
            throw new IllegalArgumentException(message);
    }

    /**
     * Throws an {@link IllegalArgumentException} if and only if the given
     * {@code Object} argument is not {@code not-null} or equivalently is {@code null}.
     * <p>
     * If an exception is thrown, it is initialized with a {@code null} message.
     * <p>
     * For a method with identical functionality that returns the argument as well,
     * use {@link java.util.Objects#requireNonNull(Object)} instead.
     *
     * @param obj The {@code Object} argument to check
     * @throws IllegalArgumentException If the {@code Object} argument is {@code null}
     * @see #requireNull(Object, String)
     * @see java.util.Objects#requireNonNull(Object)
     */
    @Contract("null -> fail")
    public static void requireNotNull(@Nullable Object obj) {
        requireNotNull(obj, null);
    }
    /**
     * Throws an {@link IllegalArgumentException} if and only if the given
     * {@code Object} argument is not {@code not-null} or equivalently is {@code null}.
     * <p>
     * If an exception is thrown, it is initialized with the specified message.
     * <p>
     * For a method with identical functionality that returns the argument as well,
     * use {@link java.util.Objects#requireNonNull(Object, String)} instead.
     *
     * @param obj     The {@code Object} argument to check
     * @param message The message for the exception
     * @throws IllegalArgumentException If the {@code Object} argument is {@code null}
     * @see #requireNull(Object, String)
     * @see java.util.Objects#requireNonNull(Object, String)
     */
    @Contract("null,_ -> fail")
    public static void requireNotNull(@Nullable Object obj, String message) {
        if (obj == null)
            throw new IllegalArgumentException(message);
    }
}
