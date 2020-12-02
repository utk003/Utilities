/*
MIT License

Copyright (c) 2020 Utkarsh Priyam

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

package me.utk.util.data;

/**
 * A wrapper class for objects of data type {@code K}.
 * <p>
 * This class is primarily designed for usage in conjunction
 * with applications where typically immutable objects are stored
 * as final variables. This class provides a simple manner
 * of modifying the values of such objects.
 *
 * @param <K> The type of the object to wrap
 * @author Utkarsh Priyam
 * @version 12/1/20
 */
public final class ClassWrapper<K> {
    /**
     * A publicly accessible reference to the object being stored.
     * <p>
     * This value can also be accessed through the {@code getValue} method
     * and modified through the {@code setValue} method.
     */
    public K value;

    /**
     * Creates a wrapper initialized to {@code null}.
     */
    public ClassWrapper() {
        this(null);
    }
    /**
     * Creates a wrapper initialized to {@code init}.
     *
     * @param init The value to initialize this object to
     */
    public ClassWrapper(K init) {
        value = init;
    }

    /**
     * Returns the object stored inside this wrapper, within {@code value}.
     *
     * @return The object enclosed within this wrapper
     */
    public K getValue() {
        return value;
    }

    /**
     * Sets the object stored in this wrapper at {@code value} to the new value
     * stored in {@code newValue}.
     *
     * @param newValue The new value of the object
     */
    public void setValue(K newValue) {
        value = newValue;
    }
}
