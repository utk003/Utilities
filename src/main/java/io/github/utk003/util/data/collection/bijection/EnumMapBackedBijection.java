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

package io.github.utk003.util.data.collection.bijection;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

/**
 * A specialized subclass of {@link MapBackedBijection} that uses {@link EnumMap}s
 * as its backing {@code Map} type and maps {@code enum}s to {@code enum}s.
 *
 * @param <A> The type of enums in the first set of this bijection
 * @param <B> The type of enums in the second set of this bijection
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version April 23, 2021
 * @see MapBackedBijection
 * @see Enum
 * @see EnumMap
 * @since 2.0.0
 * @deprecated Since 2.1.0
 */
@Deprecated
public class EnumMapBackedBijection<A extends Enum<A>, B extends Enum<B>> extends MapBackedBijection<A, B> {
    /**
     * Creates a new {@code EnumMapBackedBijection} using {@link EnumMap#EnumMap(Class)}.
     *
     * @param enumTypeA The class type for the first enum set in this bijection
     * @param enumTypeB The class type for the second enum set in this bijection
     */
    public EnumMapBackedBijection(@NotNull Class<A> enumTypeA, @NotNull Class<B> enumTypeB) {
        super(
                new EnumMap<>(enumTypeA),
                new EnumMap<>(enumTypeB)
        );
    }
}