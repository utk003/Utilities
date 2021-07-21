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

package io.github.utk003.util.math.solve;

import io.github.utk003.util.math.complex.ComplexDouble;
import io.github.utk003.util.math.complex.ComplexFloat;
import io.github.utk003.util.math.complex.ComplexNumber;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * The triplet of complex roots to a
 * <a href="https://mathworld.wolfram.com/CubicEquation.html" target="_top">cubic equation</a>.
 *
 * @param <CN> The class type of the {@link ComplexNumber}s this root triplet holds
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 20, 2021
 * @see CubicFormula
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public final class CubicSolution<CN extends ComplexNumber<CN>> {
    /**
     * The roots held by this solution object.
     */
    public final @NotNull CN root1, root2, root3;

    /**
     * Creates a new {@code CubicSolution} with the three given {@link ComplexNumber}s as roots.
     *
     * @param cn1 The {@code ComplexNumber} of {@link #root1}
     * @param cn2 The {@code ComplexNumber} of {@link #root2}
     * @param cn3 The {@code ComplexNumber} of {@link #root3}
     */
    private CubicSolution(@NotNull CN cn1, @NotNull CN cn2, @NotNull CN cn3) {
        root1 = cn1;
        root2 = cn2;
        root3 = cn3;
    }

    /**
     * Creates a new {@link CubicSolution} of {@link ComplexFloat}s.
     *
     * @return A new {@code CubicSolution} of {@code ComplexFloat}s
     */
    @ApiStatus.Internal
    public static @NotNull CubicSolution<ComplexFloat> newFloatSolution() {
        return new CubicSolution<>(new ComplexFloat(), new ComplexFloat(), new ComplexFloat());
    }
    /**
     * Creates a new {@link CubicSolution} of {@link ComplexDouble}s.
     *
     * @return A new {@code CubicSolution} of {@code ComplexDouble}s
     */
    @ApiStatus.Internal
    public static @NotNull CubicSolution<ComplexDouble> newDoubleSolution() {
        return new CubicSolution<>(new ComplexDouble(), new ComplexDouble(), new ComplexDouble());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String toString() {
        return "{" + root1 + ", " + root2 + ", " + root3 + "}";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return root1.hashCode() ^ root2.hashCode() ^ root3.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CubicSolution))
            return false;

        CubicSolution<?> cs = (CubicSolution<?>) o;
        return root1.equals(cs.root1) && root2.equals(cs.root2) && root3.equals(cs.root3);
    }
}
