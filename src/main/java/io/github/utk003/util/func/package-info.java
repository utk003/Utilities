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
 * A package that contains {@link java.lang.FunctionalInterface}s which represent
 * value-returning and void lambda functions with varying numbers of parameters.
 * <p>
 * For functions with fewer than 10 parameters, there are individual,
 * parametrized classes which can specify the exact types of every argument.
 * The generalized classes {@link io.github.utk003.util.func.LambdaN} and
 * {@link io.github.utk003.util.func.VoidLambdaN} are also available for
 * any functions that require 10 or more parameters as well as
 * for applications which can have varying argument lists.
 * <p>
 * For lambda functions which return values, use the {@code Lambda*} classes
 * in this package, and, for lambda functions which do not return values
 * (ie are void functions), use the {@code VoidLambda*} classes in this
 * package instead. In both those classes, {@code *} can be a single digit
 * or the character 'N', and it represents the number of arguments the
 * lambda takes (or that the lambda uses varargs, if the {@code *} is 'N').
 *
 * @see io.github.utk003.util.func.LambdaN
 * @see io.github.utk003.util.func.VoidLambdaN
 * @see java.lang.FunctionalInterface
 */
package io.github.utk003.util.func;