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

/**
 * A package for <a href="https://mathworld.wolfram.com/ComplexNumber.html" target="_top">complex numbers</a>.
 * <p>
 * This package contains object definitions analogous to {@link java.lang.Float}
 * and {@link java.lang.Double}, but representing complex numbers rather than
 * real numbers.
 * The package also expands various mathematical functions and tools
 * provided by the rest of the {@link io.github.utk003.util.math} package to
 * complex numbers: The most prominent is {@link io.github.utk003.util.math.complex.solve},
 * which expands the equation solvers from the {@link io.github.utk003.util.math.solve}
 * package to work with equations with complex coefficients as well.
 *
 * @see io.github.utk003.util.math.solve
 */
@ScheduledForRelease(inVersion = "v3.0.0")
package io.github.utk003.util.math.complex;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;