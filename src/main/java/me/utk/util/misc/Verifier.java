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

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * An abstract utility class for verification functions.
 * <p>
 * This class provides these methods:
 * <ul>
 * <li>The {@code verify} method, which throws a specified exception if and only if
 *     the given boolean expression evaluates to {@code false}.
 * <li>The {@code match} methods, which throw specified exceptions if and only if
 *     the two given arguments are not equivalent to each other, either by
 *     {@code ==} for primitives or by the {@code equals} method for {@code Object}s.
 * </ul>
 * <p>
 * This class's functionality has been replaced by {@link Verify}.
 *
 * @author Utkarsh Priyam
 * @version January 3, 2020
 * @see Verify
 * @deprecated Since 1.0.4
 */
@Deprecated
public abstract class Verifier {
    private Verifier() {
    }

    //-----------------------------------------------------------------------------------------------------------//
    //                                       ERRORS WITH MESSAGE SUPPLIERS                                       //
    //-----------------------------------------------------------------------------------------------------------//

    /**
     * Throws an exception of the specified type {@code clazz} and reason {@code reason} using reflection.
     *
     * @param clazz  The class of the exception to throw
     * @param reason The reason for throwing the exception
     * @throws IllegalArgumentException If the specified exception cannot be constructed and thrown
     */
    private static void error(Class<? extends RuntimeException> clazz, Supplier<String> reason) {
        try {
            throw clazz.getConstructor(String.class).newInstance(reason.get());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException ignored) {
        }
        throw new IllegalArgumentException("Unable to throw exception of type \"" + clazz.toString() + "\"");
    }

    /**
     * Verifies that the given {@code boolean} {@code bool} is {@code true}, and throws
     * the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to calling {@code Verifier.match(bool, true, clazz, message)}.
     *
     * @param bool    The {@code boolean} to verify as {@code true}
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void verify(boolean bool, Class<? extends RuntimeException> clazz, Supplier<String> message) {
        if (!bool)
            error(clazz, message);
    }

    /**
     * Verifies that the given {@code Object}s {@code obj1} and {@code obj2} are equal
     * using the {@code equals} method, and throws the specified
     * exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (!Objects.equals(obj1, obj2))
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param obj1    The first {@code Object} in the equality check
     * @param obj2    The second {@code Object} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(Object obj1, Object obj2, Class<? extends RuntimeException> clazz, Supplier<String> message) {
        if (!Objects.equals(obj1, obj2))
            error(clazz, message);
    }

    /**
     * Verifies that the given {@code long}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code long} in the equality check
     * @param arg2    The second {@code long} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(long arg1, long arg2, Class<? extends RuntimeException> clazz, Supplier<String> message) {
        if (arg1 != arg2)
            error(clazz, message);
    }
    /**
     * Verifies that the given {@code int}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code int} in the equality check
     * @param arg2    The second {@code int} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(int arg1, int arg2, Class<? extends RuntimeException> clazz, Supplier<String> message) {
        if (arg1 != arg2)
            error(clazz, message);
    }
    /**
     * Verifies that the given {@code short}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code short} in the equality check
     * @param arg2    The second {@code short} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(short arg1, short arg2, Class<? extends RuntimeException> clazz, Supplier<String> message) {
        if (arg1 != arg2)
            error(clazz, message);
    }
    /**
     * Verifies that the given {@code byte}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code byte} in the equality check
     * @param arg2    The second {@code byte} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(byte arg1, byte arg2, Class<? extends RuntimeException> clazz, Supplier<String> message) {
        if (arg1 != arg2)
            error(clazz, message);
    }

    /**
     * Verifies that the given {@code double}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code double} in the equality check
     * @param arg2    The second {@code double} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(double arg1, double arg2, Class<? extends RuntimeException> clazz, Supplier<String> message) {
        if (arg1 != arg2)
            error(clazz, message);
    }
    /**
     * Verifies that the given {@code float}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code float} in the equality check
     * @param arg2    The second {@code float} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(float arg1, float arg2, Class<? extends RuntimeException> clazz, Supplier<String> message) {
        if (arg1 != arg2)
            error(clazz, message);
    }

    /**
     * Verifies that the given {@code char}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code char} in the equality check
     * @param arg2    The second {@code char} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(char arg1, char arg2, Class<? extends RuntimeException> clazz, Supplier<String> message) {
        if (arg1 != arg2)
            error(clazz, message);
    }
    /**
     * Verifies that the given {@code boolean}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code boolean} in the equality check
     * @param arg2    The second {@code boolean} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(boolean arg1, boolean arg2, Class<? extends RuntimeException> clazz, Supplier<String> message) {
        if (arg1 != arg2)
            error(clazz, message);
    }

    //-----------------------------------------------------------------------------------------------------------//
    //                                        ERRORS WITH STRING MESSAGES                                        //
    //-----------------------------------------------------------------------------------------------------------//

    /**
     * Throws an exception of the specified type {@code clazz} and reason {@code reason} using reflection.
     *
     * @param clazz  The class of the exception to throw
     * @param reason The reason for throwing the exception
     * @throws IllegalArgumentException If the specified exception cannot be constructed and thrown
     */
    private static void error(Class<? extends RuntimeException> clazz, String reason) {
        try {
            throw clazz.getConstructor(String.class).newInstance(reason);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException ignored) {
        }
        throw new IllegalArgumentException("Unable to throw exception of type \"" + clazz.toString() + "\"");
    }

    /**
     * Verifies that the given {@code boolean} {@code bool} is {@code true}, and throws
     * the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to calling {@code Verifier.match(bool, true, clazz, message)}.
     *
     * @param bool    The {@code boolean} to verify as {@code true}
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void verify(boolean bool, Class<? extends RuntimeException> clazz, String message) {
        if (!bool)
            error(clazz, message);
    }

    /**
     * Verifies that the given {@code Object}s {@code obj1} and {@code obj2} are equal
     * using the {@code equals} method, and throws the specified
     * exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (!Objects.equals(obj1, obj2))
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param obj1    The first {@code Object} in the equality check
     * @param obj2    The second {@code Object} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(Object obj1, Object obj2, Class<? extends RuntimeException> clazz, String message) {
        if (!Objects.equals(obj1, obj2))
            error(clazz, message);
    }

    /**
     * Verifies that the given {@code long}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code long} in the equality check
     * @param arg2    The second {@code long} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(long arg1, long arg2, Class<? extends RuntimeException> clazz, String message) {
        if (arg1 != arg2)
            error(clazz, message);
    }
    /**
     * Verifies that the given {@code int}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code int} in the equality check
     * @param arg2    The second {@code int} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(int arg1, int arg2, Class<? extends RuntimeException> clazz, String message) {
        if (arg1 != arg2)
            error(clazz, message);
    }
    /**
     * Verifies that the given {@code short}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code short} in the equality check
     * @param arg2    The second {@code short} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(short arg1, short arg2, Class<? extends RuntimeException> clazz, String message) {
        if (arg1 != arg2)
            error(clazz, message);
    }
    /**
     * Verifies that the given {@code byte}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code byte} in the equality check
     * @param arg2    The second {@code byte} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(byte arg1, byte arg2, Class<? extends RuntimeException> clazz, String message) {
        if (arg1 != arg2)
            error(clazz, message);
    }

    /**
     * Verifies that the given {@code double}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code double} in the equality check
     * @param arg2    The second {@code double} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(double arg1, double arg2, Class<? extends RuntimeException> clazz, String message) {
        if (arg1 != arg2)
            error(clazz, message);
    }
    /**
     * Verifies that the given {@code float}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code float} in the equality check
     * @param arg2    The second {@code float} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(float arg1, float arg2, Class<? extends RuntimeException> clazz, String message) {
        if (arg1 != arg2)
            error(clazz, message);
    }

    /**
     * Verifies that the given {@code char}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code char} in the equality check
     * @param arg2    The second {@code char} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(char arg1, char arg2, Class<? extends RuntimeException> clazz, String message) {
        if (arg1 != arg2)
            error(clazz, message);
    }
    /**
     * Verifies that the given {@code boolean}s {@code arg1} and {@code arg2} are equal
     * using {@code ==}, and throws the specified exception with the given reason otherwise.
     * <p>
     * Calling this method is equivalent to running:
     * <pre>
     * if (arg1 != arg2)
     *     throw // exception of type clazz and message message
     * </pre>
     *
     * @param arg1    The first {@code boolean} in the equality check
     * @param arg2    The second {@code boolean} in the equality check
     * @param clazz   The class of the exception to throw
     * @param message The reason for throwing the exception
     */
    public static void match(boolean arg1, boolean arg2, Class<? extends RuntimeException> clazz, String message) {
        if (arg1 != arg2)
            error(clazz, message);
    }
}
