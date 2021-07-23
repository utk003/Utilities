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
import org.jetbrains.annotations.NotNull;

/**
 * A custom runtime exception for use by the {@link Verifier} class for failed exception instantiations.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 22, 2021
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public class VerifierFailedInstantiationException extends RuntimeException {
    /**
     * Constructs a new {@code VerifierFailedInstantiationException} with the
     * specified un-instantiated exception.
     *
     * @param clazz The class type of the un-instantiated exception
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public VerifierFailedInstantiationException(@NotNull Class<?> clazz) {
        super(getMessage(clazz));
    }
    /**
     * Constructs a new {@code VerifierFailedInstantiationException} with the
     * specified un-instantiated exception and intended failure message.
     *
     * @param clazz   The class type of the un-instantiated exception
     * @param message The detail message of the exception
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public VerifierFailedInstantiationException(@NotNull Class<?> clazz, @NotNull String message) {
        super(getMessage(clazz) + " " + message);
    }

    /**
     * Get the actual message of a {@code VerifierFailedInstantiationException}
     * from the {@code Class} of the un-instantiated exception.
     *
     * @param clazz The class type of the un-instantiated exception
     * @return The message of a {@code VerifierFailedInstantiationException}
     * constructed with the given {@code Class} object
     */
    private static @NotNull String getMessage(@NotNull Class<?> clazz) {
        return "[Unable to create an instance of " + clazz + "]";
    }
}
