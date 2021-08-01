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

package io.github.utk003.util.data.collection.bitset;

import io.github.utk003.util.func.Lambda1;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

@ApiStatus.Experimental
@RequiresDocumentation
public interface BitSet<E extends Enum<E>> extends Iterable<BitSet.Bits> {
    int size();
    @NotNull Iterator<Bits> iterator();

    @NotNull Bits getValue(@NotNull E key);
    byte getValueAsByte(@NotNull E key);
    short getValueAsShort(@NotNull E key);
    int getValueAsInt(@NotNull E key);
    long getValueAsLong(@NotNull E key);
    boolean getValueAsBool(@NotNull E key);
    char getValueAsChar(@NotNull E key);

    void setValue(@NotNull E key, byte value);
    void setValue(@NotNull E key, short value);
    void setValue(@NotNull E key, int value);
    void setValue(@NotNull E key, long value);
    void setValue(@NotNull E key, boolean value);
    void setValue(@NotNull E key, char value);

    void clearValue(@NotNull E key);
    void clear();

    @NotNull Bits[] toArray();
    @NotNull byte[] toArray(@NotNull byte[] template);
    @NotNull short[] toArray(@NotNull short[] template);
    @NotNull int[] toArray(@NotNull int[] template);
    @NotNull long[] toArray(@NotNull long[] template);
    @NotNull boolean[] toArray(@NotNull boolean[] template);
    @NotNull char[] toArray(@NotNull char[] template);

    static <E extends Enum<E> & Sizable> @NotNull BitSet<E> getInstance(@NotNull Class<E> keyType) {
        return new DefaultBitSet<>(keyType);
    }
    static <E extends Enum<E>> @NotNull BitSet<E> getInstance(@NotNull Class<E> keyType, @NotNull Lambda1<Integer, E> bitSizeGetter) {
        return new DefaultBitSet<>(keyType, bitSizeGetter);
    }

    interface Sizable {
        int sizeInBits();
    }

    /**
     * @since 2.1.0
     */
    interface Bits {
        /**
         * Returns the bits this object represents as a {@code byte}.
         * <p>
         * Any unused bits in the {@code byte} will be {@code 0},
         * and any excess bits from the ones this object holds
         * will be ignored in the conversion to a {@code byte}.
         * <p>
         * As the bits held by this object are ordered, the first 8
         * bits will be returned as a single {@code byte}, with
         * the first bit in the least-significant position and the
         * following bits in places of increasing significance.
         *
         * @return The bits this object represents as a {@code byte}
         */
        byte asByte();
        /**
         * Returns the bits this object represents as a {@code short}.
         * <p>
         * Any unused bits in the {@code short} will be {@code 0},
         * and any excess bits from the ones this object holds
         * will be ignored in the conversion to a {@code short}.
         * <p>
         * As the bits held by this object are ordered, the first 16
         * bits will be returned as a single {@code short}, with
         * the first bit in the least-significant position and the
         * following bits in places of increasing significance.
         *
         * @return The bits this object represents as a {@code short}
         */
        short asShort();
        /**
         * Returns the bits this object represents as a {@code int}.
         * <p>
         * Any unused bits in the {@code int} will be {@code 0},
         * and any excess bits from the ones this object holds
         * will be ignored in the conversion to a {@code int}.
         * <p>
         * As the bits held by this object are ordered, the first 32
         * bits will be returned as a single {@code int}, with
         * the first bit in the least-significant position and the
         * following bits in places of increasing significance.
         *
         * @return The bits this object represents as a {@code int}
         */
        int asInt();
        /**
         * Returns the bits this object represents as a {@code long}.
         * <p>
         * Any unused bits in the {@code long} will be {@code 0},
         * and any excess bits from the ones this object holds
         * will be ignored in the conversion to a {@code long}.
         * <p>
         * As the bits held by this object are ordered, the first 64
         * bits will be returned as a single {@code long}, with
         * the first bit in the least-significant position and the
         * following bits in places of increasing significance.
         *
         * @return The bits this object represents as a {@code long}
         */
        long asLong();

        /**
         * Returns the bits this object represents as a {@code boolean}.
         * <p>
         * Any unused bits in the {@code boolean} will be {@code 0},
         * and any excess bits from the ones this object holds
         * will be ignored in the conversion to a {@code boolean}.
         * <p>
         * As the bits held by this object are ordered, the first 1
         * bit will be returned as a single {@code boolean}, with
         * the first bit in the least-significant position and the
         * following bits in places of increasing significance.
         *
         * @return The bits this object represents as a {@code boolean}
         */
        boolean asBool();
        /**
         * Returns the bits this object represents as a {@code char}.
         * <p>
         * Any unused bits in the {@code char} will be {@code 0},
         * and any excess bits from the ones this object holds
         * will be ignored in the conversion to a {@code char}.
         * <p>
         * As the bits held by this object are ordered, the first 16
         * bits will be returned as a single {@code char}, with
         * the first bit in the least-significant position and the
         * following bits in places of increasing significance.
         *
         * @return The bits this object represents as a {@code char}
         */
        char asChar();
    }
}
