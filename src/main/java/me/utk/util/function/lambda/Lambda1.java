/*
MIT License

Copyright (c) 2020 Utkarsh Priyam

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

package me.utk.util.function.lambda;

/**
 * Represents a lambda function which takes in 1 parameter of type {@code T}
 * and returns an object of type {@code R}.
 * <p>
 * This is a {@link FunctionalInterface} whose functional method is
 * {@link #get(Object)}.
 *
 * @param <R> The return type of this lambda function
 * @param <T> The type of the parameter for this lambda function
 * @author Utkarsh Priyam
 * @version December 10, 2020
 */
@FunctionalInterface
public interface Lambda1<R, T> {
    /**
     * Applies the lambda function to the given parameters
     *
     * @param param The parameter for this lambda function
     * @return The result of this function call
     */
    R get(T param);
}
