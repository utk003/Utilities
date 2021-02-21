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
 * Represents a void lambda function which takes no parameters.
 * <p>
 * A {@code VoidLambda0} behaves just like a {@link Runnable}.
 * <p>
 * This is a {@link FunctionalInterface} whose functional method is
 * {@link #run()}.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version January 13, 2021
 * @see Runnable
 * @since 1.0.2
 */
@FunctionalInterface
public interface VoidLambda0 {
    /**
     * Runs the given lambda function.
     *
     * @see Runnable#run()
     */
    void run();
}