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

package io.github.utk003.util.data.reference;

import java.util.Objects;

/**
 * A mutable reference for objects of data type {@code K}.
 * <p>
 * This class is primarily designed for usage in conjunction
 * with applications where typically immutable objects are stored
 * as final variables. This class provides a simple manner
 * of modifying the values of such objects.
 * <p>
 * Additionally, this class can be used similarly to references in
 * other languages, such as in the following example:
 * <pre>
 * method_signature func(Ref&lt;Type&gt; param) {
 *     param.value = new_value;
 * }
 *
 * main() {
 *     Ref&lt;Type&gt; ref = init_value;
 *     func(ref);
 *     System.out.println(ref.value); // new_value
 * }
 * </pre>
 * <p>
 * This reference class is entirely identical to {@link Ref}.
 *
 * @param <K> The type of the object to wrap
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version March 19, 2021
 * @see Ref
 * @since 1.3.0
 */
public final class Reference<K> {
    /**
     * A publicly accessible reference to the object being stored.
     * <p>
     * This value can also be accessed through the {@code getValue} method
     * and modified through the {@code setValue} method.
     */
    public K value;

    /**
     * Creates a reference initialized to {@code null}.
     */
    public Reference() {
        this(null);
    }
    /**
     * Creates a reference initialized to {@code init}.
     *
     * @param init The value to initialize this reference to
     */
    public Reference(K init) {
        value = init;
    }

    /**
     * Returns the object stored inside this reference (within {@code value}).
     *
     * @return The object enclosed within this reference
     */
    public K getValue() {
        return value;
    }

    /**
     * Sets the object stored in this reference (in {@code value})
     * to the new value stored in {@code newValue}.
     *
     * @param newValue The new value of the reference
     */
    public void setValue(K newValue) {
        value = newValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public String toString() {
        return "<reference>=" + value;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public boolean equals(Object o) {
        return this == o || o instanceof Reference && Objects.equals(value, ((Reference<?>) o).value);
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }
}