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
 * The quadruplet of complex roots to a
 * <a href="https://mathworld.wolfram.com/QuarticEquation.html" target="_top">quartic equation</a>.
 *
 * @param <CN> The class type of the {@link ComplexNumber}s this root quadruplet holds
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 20, 2021
 * @see QuarticFormula
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public final class QuarticSolution<CN extends ComplexNumber<CN>> {
    /**
     * The roots held by this solution object.
     */
    public final @NotNull CN root1, root2, root3, root4;

    /**
     * Creates a new {@code QuarticSolution} with the four given {@link ComplexNumber}s as roots.
     *
     * @param cn1 The {@code ComplexNumber} of {@link #root1}
     * @param cn2 The {@code ComplexNumber} of {@link #root2}
     * @param cn3 The {@code ComplexNumber} of {@link #root3}
     * @param cn4 The {@code ComplexNumber} of {@link #root4}
     */
    private QuarticSolution(@NotNull CN cn1, @NotNull CN cn2, @NotNull CN cn3, @NotNull CN cn4) {
        root1 = cn1;
        root2 = cn2;
        root3 = cn3;
        root4 = cn4;
    }

    /**
     * Creates a new {@link QuarticSolution} of {@link ComplexFloat}s.
     *
     * @return A new {@code QuarticSolution} of {@code ComplexFloat}s
     */
    @ApiStatus.Internal
    public static @NotNull QuarticSolution<ComplexFloat> newFloatSolution() {
        return new QuarticSolution<>(new ComplexFloat(), new ComplexFloat(), new ComplexFloat(), new ComplexFloat());
    }
    /**
     * Creates a new {@link QuarticSolution} of {@link ComplexDouble}s.
     *
     * @return A new {@code QuarticSolution} of {@code ComplexDouble}s
     */
    @ApiStatus.Internal
    public static @NotNull QuarticSolution<ComplexDouble> newDoubleSolution() {
        return new QuarticSolution<>(new ComplexDouble(), new ComplexDouble(), new ComplexDouble(), new ComplexDouble());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String toString() {
        return "{" + root1 + ", " + root2 + ", " + root3 + ", " + root4 + "}";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return root1.hashCode() ^ root2.hashCode() ^ root3.hashCode() ^ root4.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QuarticSolution))
            return false;

        QuarticSolution<?> qs = (QuarticSolution<?>) o;
        return root1.equals(qs.root1) && root2.equals(qs.root2) && root3.equals(qs.root3) && root4.equals(qs.root4);
    }
}
