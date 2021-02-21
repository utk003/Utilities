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

package io.github.utk003.util.function.void_lambda;

/**
 * Represents a void lambda function which takes in 6 parameters.
 * <p>
 * The types of the 6 parameters are
 * {@code T1}, {@code T2}, {@code T3}, {@code T4}, {@code T5}, and {@code T6}, respectively.
 * <p>
 * This is a {@link FunctionalInterface} whose functional method is
 * {@link #run(Object, Object, Object, Object, Object, Object)}.
 *
 * @param <T1> The type of parameter 1 for this lambda function
 * @param <T2> The type of parameter 2 for this lambda function
 * @param <T3> The type of parameter 3 for this lambda function
 * @param <T4> The type of parameter 4 for this lambda function
 * @param <T5> The type of parameter 5 for this lambda function
 * @param <T6> The type of parameter 6 for this lambda function
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version January 13, 2021
 * @since 1.0.2
 */
@FunctionalInterface
public interface VoidLambda6<T1, T2, T3, T4, T5, T6> {
    /**
     * Applies the lambda function to the given parameters.
     *
     * @param param1 Parameter #1 for this lambda function
     * @param param2 Parameter #2 for this lambda function
     * @param param3 Parameter #3 for this lambda function
     * @param param4 Parameter #4 for this lambda function
     * @param param5 Parameter #5 for this lambda function
     * @param param6 Parameter #6 for this lambda function
     */
    void run(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6);
}
