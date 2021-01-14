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

/**
 * A package that contains {@link java.lang.FunctionalInterface}s which
 * represent value-returning lambda functions taking any number of parameters.
 * <p>
 * For functions with fewer than 10 parameters, there are individual,
 * parametrized classes which can specify the exact types of every argument.
 * The generalized class {@link io.github.utk003.util.function.lambda.LambdaN}
 * is also available for any functions that require 10 or more parameters
 * as well as for applications which can have varying argument list lengths.
 * <p>
 * For lambda functions which do not return values (ie are void functions),
 * go to the {@link io.github.utk003.util.function.void_lambda} package instead.
 *
 * @see io.github.utk003.util.function.lambda.LambdaN
 * @see java.lang.FunctionalInterface
 * @see io.github.utk003.util.function.void_lambda
 */
package io.github.utk003.util.function.lambda;