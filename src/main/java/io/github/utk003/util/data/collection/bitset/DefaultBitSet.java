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
import sun.misc.SharedSecrets; // TODO remove SharedSecrets

import java.util.Iterator;

@ApiStatus.Experimental
@RequiresDocumentation
final class DefaultBitSet<E extends Enum<E>> extends AbstractBitSet<E> implements BitSet<E> {
    final @NotNull Class<E> KEY_TYPE;
    final int NUM_KEYS;
    final @NotNull E[] KEYS;
    final @NotNull int[] OFFSETS;
    final @NotNull byte[] BITS;

    private static <E extends Enum<E>> @NotNull E[] getEnumValues(@NotNull Class<E> clazz) {
        return SharedSecrets.getJavaLangAccess().getEnumConstantsShared(clazz);
    }
    // ceiling of maxOffset / 8
    private static int computeNumberOfBytesFromMaxOffset(int maxOffset) {
        return maxOffset % 8 == 0 ? maxOffset / 8 : 1 + maxOffset / 8;
    }

    public DefaultBitSet(@NotNull Class<E> keyType) {
        KEY_TYPE = keyType;
        KEYS = getEnumValues(keyType);
        NUM_KEYS = KEYS.length;

        OFFSETS = new int[NUM_KEYS + 1];
        for (int i = 0; i < NUM_KEYS; i++)
            OFFSETS[i + 1] = OFFSETS[i] + ((Sizable) KEYS[i]).sizeInBits();

        BITS = new byte[computeNumberOfBytesFromMaxOffset(OFFSETS[NUM_KEYS])];
    }
    public DefaultBitSet(@NotNull Class<E> keyType, @NotNull Lambda1<Integer, E> bitSizeGetter) {
        KEY_TYPE = keyType;
        KEYS = getEnumValues(keyType);
        NUM_KEYS = KEYS.length;

        OFFSETS = new int[NUM_KEYS + 1];
        for (int i = 1; i <= NUM_KEYS; i++)
            OFFSETS[i] = OFFSETS[i - 1] + bitSizeGetter.get(KEYS[i]);

        BITS = new byte[computeNumberOfBytesFromMaxOffset(OFFSETS[NUM_KEYS])];
    }

    private static int min(int i1, int i2, int i3) {
        return Math.min(Math.min(i1, i2), i3);
    }

    @Override
    public int size() {
        return OFFSETS[NUM_KEYS];
    }

    @Override
    public @NotNull Iterator<Bits> iterator() {
        return new BitSetIterator();
    }

    private int sizeOfValue(@NotNull E key) {
        int ord = key.ordinal();
        return OFFSETS[ord + 1] - OFFSETS[ord];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Bits getValue(@NotNull E key) {
        int numBits = sizeOfValue(key);
        if (numBits <= 8)
            return new BitsAsByte(getValueAsByte(key));
        if (numBits <= 16)
            return new BitsAsShort(getValueAsShort(key));
        if (numBits <= 32)
            return new BitsAsInt(getValueAsInt(key));
        return new BitsAsLong(getValueAsLong(key));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public byte getValueAsByte(@NotNull E key) {
        return (byte) getValue(key, 8);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public short getValueAsShort(@NotNull E key) {
        return (short) getValue(key, 16);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getValueAsInt(@NotNull E key) {
        return (int) getValue(key, 32);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public long getValueAsLong(@NotNull E key) {
        return getValue(key, 64);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getValueAsBool(@NotNull E key) {
        return (getValue(key, 1) & 1) != 0;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public char getValueAsChar(@NotNull E key) {
        return (char) getValue(key, 16);
    }

    private static final long EIGHT_BIT_TRUNCATION_CONSTANT = 0b1111_1111;

    // numBits = 1 or multiple of 8
    // returns that many bits from the
    long getValue(@NotNull E key, int numBits) {
        if (numBits == 1) {
            int ind = OFFSETS[key.ordinal()];
            return (BITS[ind / 8] >>> (7 - ind % 8)) & 1;
        }

        int ord = key.ordinal(), start = OFFSETS[ord], end = min(OFFSETS[ord + 1], start + numBits, BITS.length * 8), end8 = end / 8;
        int ind = start / 8;
        long result = 0;
        if (start % 8 != 0)
            result = EIGHT_BIT_TRUNCATION_CONSTANT & BITS[ind++] & ((1 << (8 - start % 8)) - 1);
        while (ind < end8)
            result = result << 8 | EIGHT_BIT_TRUNCATION_CONSTANT & BITS[ind++];
        if (end % 8 != 0) {
            int toShow = end % 8, left = 8 - toShow;
            result = result << toShow | EIGHT_BIT_TRUNCATION_CONSTANT & BITS[ind] >>> left & ((1 << toShow) - 1);
        }
        return result;
    }

    @Override
    public void setValue(@NotNull E key, long value) {
        int ord = key.ordinal(), start = OFFSETS[ord], end = min(OFFSETS[ord + 1], start + 64, BITS.length * 8), numBits = end - start;
        int ind = end / 8;
        if (end % 8 != 0) {
            int toAdd = end % 8, rest = 8 - toAdd;
            BITS[ind] &= (1 << rest) - 1;
            BITS[ind] |= (value & ((1 << toAdd) - 1)) << rest;
            value >>>= toAdd;
            numBits -= toAdd;
        }
        while (numBits >= 8) {
            BITS[--ind] = (byte) value;
            value >>>= 8;
            numBits -= 8;
        }
        if (numBits > 0) {
            ind--;
            int mask = (1 << numBits) - 1;
            BITS[ind] &= ~mask;
            BITS[ind] |= value & mask;
        }
    }

    @Override
    public void clear() {
        for (E key : KEYS)
            clearValue(key);
    }
    @Override
    public @NotNull Bits[] toArray() {
        Bits[] arr = new Bits[NUM_KEYS];
        for (int i = 0; i < NUM_KEYS; i++)
            arr[i] = getValue(KEYS[i]);
        return arr;
    }
    @Override
    public @NotNull byte[] toArray(@NotNull byte[] template) {
        byte[] arr = template.length >= NUM_KEYS ? template : new byte[NUM_KEYS];
        for (int i = 0; i < NUM_KEYS; i++)
            arr[i] = getValueAsByte(KEYS[i]);
        for (int i = NUM_KEYS; i < template.length; i++)
            arr[i] = 0;
        return arr;
    }
    @Override
    public @NotNull short[] toArray(@NotNull short[] template) {
        short[] arr = template.length >= NUM_KEYS ? template : new short[NUM_KEYS];
        for (int i = 0; i < NUM_KEYS; i++)
            arr[i] = getValueAsShort(KEYS[i]);
        for (int i = NUM_KEYS; i < template.length; i++)
            arr[i] = 0;
        return arr;
    }
    @Override
    public @NotNull int[] toArray(@NotNull int[] template) {
        int[] arr = template.length >= NUM_KEYS ? template : new int[NUM_KEYS];
        for (int i = 0; i < NUM_KEYS; i++)
            arr[i] = getValueAsInt(KEYS[i]);
        for (int i = NUM_KEYS; i < template.length; i++)
            arr[i] = 0;
        return arr;
    }
    @Override
    public @NotNull long[] toArray(@NotNull long[] template) {
        long[] arr = template.length >= NUM_KEYS ? template : new long[NUM_KEYS];
        for (int i = 0; i < NUM_KEYS; i++)
            arr[i] = getValueAsLong(KEYS[i]);
        for (int i = NUM_KEYS; i < template.length; i++)
            arr[i] = 0;
        return arr;
    }
    @Override
    public @NotNull boolean[] toArray(@NotNull boolean[] template) {
        boolean[] arr = template.length >= NUM_KEYS ? template : new boolean[NUM_KEYS];
        for (int i = 0; i < NUM_KEYS; i++)
            arr[i] = getValueAsBool(KEYS[i]);
        for (int i = NUM_KEYS; i < template.length; i++)
            arr[i] = false;
        return arr;
    }
    @Override
    public @NotNull char[] toArray(@NotNull char[] template) {
        char[] arr = template.length >= NUM_KEYS ? template : new char[NUM_KEYS];
        for (int i = 0; i < NUM_KEYS; i++)
            arr[i] = getValueAsChar(KEYS[i]);
        for (int i = NUM_KEYS; i < template.length; i++)
            arr[i] = 0;
        return arr;
    }

    private class BitSetIterator implements Iterator<Bits> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < NUM_KEYS;
        }
        @Override
        public Bits next() {
            return getValue(KEYS[index++]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (E key : KEYS)
            builder.append(", ").append(key).append("=").append(getValue(key, sizeOfValue(key)));
        return "{" + (builder.length() == 0 ? "" : builder.substring(2)) + "}";
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (E key : KEYS)
            hash ^= getValue(key, sizeOfValue(key));
        return hash;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        //noinspection unchecked
        return o instanceof DefaultBitSet<?> && KEY_TYPE == ((DefaultBitSet<?>) o).KEY_TYPE && equals((DefaultBitSet<E>) o) ||
                o instanceof BitSet<?> && equals((BitSet<E>) o);
    }
    private boolean equals(@NotNull DefaultBitSet<E> other) {
        for (E key : KEYS) {
            int s1 = sizeOfValue(key), s2 = other.sizeOfValue(key);
            if (s1 != s2)
                return false;
            long v1 = getValue(key, s1), v2 = getValue(key, s2);
            if (v1 != v2)
                return false;
        }
        return true;
    }
    private boolean equals(@NotNull BitSet<E> other) {
        for (E key : KEYS)
            if (getValueAsLong(key) != other.getValueAsLong(key))
                return false;
        return true;
    }
}
