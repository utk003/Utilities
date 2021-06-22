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

import java.util.function.BiConsumer;

/**
 * Represents a void lambda function which takes in 2 parameters.
 * <p>
 * The types of the 2 parameters are
 * {@code T1} and {@code T2}, respectively.
 * <p>
 * This is a {@link FunctionalInterface} whose functional method is
 * {@link #run(Object, Object)}.
 * <p>
 * This functional interface also extends {@link BiConsumer}, which has a similar
 * method signature. This allows for {@code VoidLambda2} objects to be used with
 * other Java code that uses {@code BiConsumer}.
 *
 * @param <T1> The type of parameter 1 for this lambda function
 * @param <T2> The type of parameter 2 for this lambda function
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version January 13, 2021
 * @see BiConsumer
 * @since 1.0.2
 */
@FunctionalInterface
public interface VoidLambda2<T1, T2> extends BiConsumer<T1, T2> {
    /**
     * Applies the lambda function to the given parameters.
     *
     * @param param1 Parameter #1 for this lambda function
     * @param param2 Parameter #2 for this lambda function
     */
    void run(T1 param1, T2 param2);

    /**
     * {@inheritDoc}
     *
     * @since 2.1.0
     */
    @Override
    default void accept(T1 t1, T2 t2) {
        run(t1, t2);
    }
}
