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

package io.github.utk003.util.func;

import java.util.function.Supplier;

/**
 * Represents a lambda function which takes in 0 parameters and
 * returns an object of type {@code R}.
 * <p>
 * This is a {@link FunctionalInterface} whose functional method is {@link #get()}.
 * <p>
 * This functional interface also extends Java's {@link Supplier}, which has the same
 * method signature. This allows for {@code Lambda0} objects to be used in other Java
 * code that uses {@code Supplier}.
 *
 * @param <R> The return type of this lambda function
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version January 13, 2021
 * @see Supplier
 * @since 1.0.3
 */
@FunctionalInterface
public interface Lambda0<R> extends Supplier<R> {
    /**
     * Supplies the output of the given function.
     *
     * @return The result of this function call
     */
    R get();
}