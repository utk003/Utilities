package io.github.utk003.util.data.singleton;

import java.util.NoSuchElementException;

/**
 * Thrown by {@link SingletonFactory}s when a singleton access fails.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 26, 2021
 * @see Singleton
 * @see SingletonFactory
 * @since 2.3.0
 */
public class NoSuchSingletonException extends NoSuchElementException {
    /**
     * Constructs a <code>NoSuchSingletonException</code> with <tt>null</tt>
     * as its error message string.
     */
    public NoSuchSingletonException() {
        super();
    }

    /**
     * Constructs a <code>NoSuchSingletonException</code>, saving a reference
     * to the error message string <tt>s</tt> for later retrieval by the
     * <tt>getMessage</tt> method.
     *
     * @param s The detail message
     */
    public NoSuchSingletonException(String s) {
        super(s);
    }
}
