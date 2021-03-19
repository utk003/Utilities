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
 * A once-modifiable mutable reference for objects of data type {@code K}.
 * <p>
 * This class is equivalent to {@link FinitelyModifiableReference}, but with
 * a limit of exactly one modification. Note that initializing the reference
 * in the constructor counts as a modification, so this class can be used as
 * a {@code final} instance variable that gets initialized in an {@code init()}
 * method rather than in the constructor itself.
 *
 * @param <K> The type of the object to wrap
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version March 19, 2021
 * @see FinitelyModifiableReference
 * @since 1.3.0
 */
public final class OnceModifiableReference<K> {
    private boolean hasBeenModified;
    private K value;

    /**
     * Creates a once-modifiable reference initialized to {@code null}.
     */
    public OnceModifiableReference() {
        this.hasBeenModified = false;
        this.value = null;
    }
    /**
     * Creates a once-modifiable reference initialized to {@code init}.
     * <p>
     * This initialization in the constructor counts as a modification.
     * <p>
     * This constructor is equivalent to doing
     * <pre>
     * ref = new OnceModifiableReference();
     * ref.setValue(init);
     * </pre>
     *
     * @param init The value to initialize this reference to
     */
    public OnceModifiableReference(K init) {
        this.hasBeenModified = true;
        this.value = init;
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
     * to the new value stored in {@code newValue} if and only if
     * modifications to this reference are still allowed.
     *
     * @param newValue The new value of the reference
     * @throws UnsupportedOperationException If this reference has already been modified
     * @see #canBeModified()
     */
    public void setValue(K newValue) {
        if (hasBeenModified)
            throw new UnsupportedOperationException("Reference modification limit reached");

        hasBeenModified = true; // prevent future modifications
        value = newValue;       // set new value into reference
    }

    /**
     * Returns whether or not this reference can still be modified.
     * <p>
     * If the return value of this method is {@code false},
     * then any call to {@link #setValue(Object)} will
     * result in an {@link UnsupportedOperationException}.
     *
     * @return {@code true}, if this reference can still be modified; otherwise, {@code false}
     * @see #setValue(Object)
     */
    public boolean canBeModified() {
        return !hasBeenModified;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public String toString() {
        return "<modifiable reference(" + (hasBeenModified ? 0 : 1) + ")>=" + value;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public boolean equals(Object o) {
        return this == o || o instanceof OnceModifiableReference &&
                Objects.equals(value, ((OnceModifiableReference<?>) o).value) &&
                hasBeenModified == ((OnceModifiableReference<?>) o).hasBeenModified;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public int hashCode() {
        // if not modified, value = null, so we can just return ~0
        // instead of using the conditional to check
        return hasBeenModified ? value == null ? 0 : value.hashCode() : ~0;
    }
}
