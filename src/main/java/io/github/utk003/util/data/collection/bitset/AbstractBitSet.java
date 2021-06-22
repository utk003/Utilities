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

import org.jetbrains.annotations.NotNull;

public abstract class AbstractBitSet<E extends Enum<E>> implements BitSet<E> {
    /**
     * {@inheritDoc}
     */
    @Override
    public byte getValueAsByte(@NotNull E key) {
        return (byte) getValueAsLong(key);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public short getValueAsShort(@NotNull E key) {
        return (short) getValueAsLong(key);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getValueAsInt(@NotNull E key) {
        return (int) getValueAsLong(key);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getValueAsBool(@NotNull E key) {
        return (getValueAsLong(key) & 1) != 0;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public char getValueAsChar(@NotNull E key) {
        return (char) getValueAsLong(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(@NotNull E key, byte value) {
        setValue(key, (long) value);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(@NotNull E key, short value) {
        setValue(key, (long) value);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(@NotNull E key, int value) {
        setValue(key, (long) value);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(@NotNull E key, boolean value) {
        setValue(key, value ? 1L : 0L);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(@NotNull E key, char value) {
        setValue(key, (long) value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearValue(@NotNull E key) {
        setValue(key, 0L);
    }

    protected static final class BitsAsByte implements Bits {
        private final byte VALUE;

        /**
         * Create a new {@code BitsAsByte} with the specified byte value.
         *
         * @param value The value contained by this bit-holder
         */
        public BitsAsByte(byte value) {
            VALUE = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public byte asByte() {
            return VALUE;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public short asShort() {
            return VALUE;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public int asInt() {
            return VALUE;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public long asLong() {
            return VALUE;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean asBool() {
            return (VALUE & 1) != 0;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public char asChar() {
            return (char) (int) VALUE;
        }
    }

    protected static final class BitsAsShort implements Bits {
        private final short VALUE;

        /**
         * Create a new {@code BitsAsShort} with the specified byte value.
         *
         * @param value The value contained by this bit-holder
         */
        public BitsAsShort(short value) {
            VALUE = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public byte asByte() {
            return (byte) VALUE;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public short asShort() {
            return VALUE;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public int asInt() {
            return VALUE;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public long asLong() {
            return VALUE;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean asBool() {
            return (VALUE & 1) != 0;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public char asChar() {
            return (char) VALUE;
        }
    }

    protected static final class BitsAsInt implements Bits {
        private final int VALUE;

        /**
         * Create a new {@code BitsAsInt} with the specified byte value.
         *
         * @param value The value contained by this bit-holder
         */
        public BitsAsInt(int value) {
            VALUE = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public byte asByte() {
            return (byte) VALUE;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public short asShort() {
            return (short) VALUE;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public int asInt() {
            return VALUE;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public long asLong() {
            return VALUE;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean asBool() {
            return (VALUE & 1) != 0;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public char asChar() {
            return (char) VALUE;
        }
    }

    protected static final class BitsAsLong implements Bits {
        private final long VALUE;

        /**
         * Create a new {@code BitsAsLong} with the specified byte value.
         *
         * @param value The value contained by this bit-holder
         */
        public BitsAsLong(long value) {
            VALUE = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public byte asByte() {
            return (byte) VALUE;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public short asShort() {
            return (short) VALUE;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public int asInt() {
            return (int) VALUE;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public long asLong() {
            return VALUE;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean asBool() {
            return (VALUE & 1) != 0;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public char asChar() {
            return (char) VALUE;
        }
    }
}
