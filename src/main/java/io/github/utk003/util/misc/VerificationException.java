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

/**
 * A custom runtime exception for use by the
 * {@link Verifier} class for failed verifications.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version February 25, 2021
 * @since 1.2.0
 */
public class VerificationException extends RuntimeException {
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
