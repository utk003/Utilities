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

package io.github.utk003.util.data.collection.slice;

import org.jetbrains.annotations.NotNull;

/**
 * An exception that is thrown to indicate that a {@link Slice} has been accessed
 * with an illegal index.
 * <p>
 * If this exception is thrown, the index provided is either negative, greater than or equal to
 * the size of the array, or results in the creation of a slice with negative length.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 11, 2021
 * @see Slice
 * @see Slice#length()
 * @since 2.1.0
 */
public class SliceIndexOutOfBoundsException extends ArrayIndexOutOfBoundsException {
    /**
     * Constructs an <code>SliceIndexOutOfBoundsException</code> with no detail message.
     */
    public SliceIndexOutOfBoundsException() {
        super();
    }

    /**
     * Constructs a new <code>SliceIndexOutOfBoundsException</code>
     * with an argument indicating the illegal index.
     *
     * @param index the illegal index.
     */
    public SliceIndexOutOfBoundsException(int index) {
        super("Slice index out of range: " + index);
    }

    /**
     * Constructs an <code>SliceIndexOutOfBoundsException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public SliceIndexOutOfBoundsException(@NotNull String msg) {
        super(msg);
    }
}
