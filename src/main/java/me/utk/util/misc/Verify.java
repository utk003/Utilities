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

package me.utk.util.misc;

/**
 * An abstract utility class for verification functions.
 * <p>
 * This class provides these methods:
 * <ul>
 * <li>The {@code isTrue} methods, which throw an {@link IllegalArgumentException}
 *     if and only if the given boolean expression evaluates to {@code false}.
 * <li>The {@code isFalse} methods, which throw an {@link IllegalArgumentException}
 *     if and only if the given boolean expression evaluates to {@code true}.
 * <li>The {@code isNull} methods, which throw an {@link IllegalArgumentException}
 *     if and only if the given object expression is not {@code null}.
 * <li>The {@code isNotNull} methods, which throw an {@link IllegalArgumentException}
 *     if and only if the given object expression is {@code null}.
 * </ul>
 *
 * @author Utkarsh Priyam
 * @version January 3, 2020
 * @see Verify
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
     * @see #isTrue(boolean, String)
     */
    public static void isTrue(boolean bool) {
        isTrue(bool, null);
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
    public static void isTrue(boolean bool, String message) {
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
     * @see #isFalse(boolean, String)
     */
    public static void isFalse(boolean bool) {
        isFalse(bool, null);
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
    public static void isFalse(boolean bool, String message) {
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
     * @see #isNull(Object, String)
     */
    public static void isNull(Object obj) {
        isNull(obj, null);
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
    public static void isNull(Object obj, String message) {
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
     * @see #isNull(Object, String)
     * @see java.util.Objects#requireNonNull(Object)
     */
    public static void isNotNull(Object obj) {
        isNotNull(obj, null);
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
     * @see #isNull(Object, String)
     * @see java.util.Objects#requireNonNull(Object, String)
     */
    public static void isNotNull(Object obj, String message) {
        if (obj == null)
            throw new IllegalArgumentException(message);
    }
}
