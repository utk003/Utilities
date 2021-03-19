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
 * A finitely-modifiable mutable reference for objects of data type {@code K}.
 * <p>
 * This class is quite similar to {@link Reference}, but it
 * provides a limit on the number of times the reference can
 * be modified. Initializing the reference in the constructor
 * counts as a modification.
 * <p>
 * For a specialized variation of this class that can only be set
 * a single time, check out {@link OnceModifiableReference}.
 *
 * @param <K> The type of the object to wrap
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version March 19, 2021
 * @see OnceModifiableReference
 * @see Reference
 * @since 1.3.0
 */
public final class FinitelyModifiableReference<K> {
    private int numModificationsLeft;
    private K value;

    /**
     * Creates a reference initialized to {@code null} with the specified modification limit.
     *
     * @param numModificationsAllowed The number of modifications allowed for this reference
     */
    public FinitelyModifiableReference(int numModificationsAllowed) {
        this.numModificationsLeft = Math.max(0, numModificationsAllowed);
        this.value = null;
    }
    /**
     * Creates a reference initialized to {@code init} with the specified modification limit.
     * <p>
     * This initialization in the constructor counts as
     * a modification, so this constructor throws an
     * {@link UnsupportedOperationException} if
     * {@code numModificationsAllowed <= 0}.
     * <p>
     * This constructor is equivalent to doing
     * <pre>
     * ref = new FinitelyModifiableReference(numModificationsAllowed);
     * ref.setValue(init);
     * </pre>
     *
     * @param numModificationsAllowed The number of modifications allowed for this reference
     * @param init                    The value to initialize this reference to
     */
    public FinitelyModifiableReference(int numModificationsAllowed, K init) {
        this(numModificationsAllowed);
        this.setValue(init);
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
     * @throws UnsupportedOperationException If the modification limit for this reference has been reached
     */
    public void setValue(K newValue) {
        if (numModificationsLeft <= 0)
            throw new UnsupportedOperationException("Reference modification limit reached");

        numModificationsLeft--; // reduce # modifications left
        value = newValue;       // set new value into reference
    }

    /**
     * Returns the number of modifications left that are still supported by this reference.
     * <p>
     * If the return value of this method is not positive,
     * then any call to {@link #setValue(Object)} will
     * result in an {@link UnsupportedOperationException}.
     *
     * @return The number of times this reference can still be modified
     * @see #setValue(Object)
     */
    public int getNumModificationsLeft() {
        return numModificationsLeft;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public String toString() {
        return "<modifiable reference(" + numModificationsLeft + ")>=" + value;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public boolean equals(Object o) {
        return this == o || o instanceof FinitelyModifiableReference &&
                Objects.equals(value, ((FinitelyModifiableReference<?>) o).value) &&
                numModificationsLeft == ((FinitelyModifiableReference<?>) o).numModificationsLeft;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public int hashCode() {
        return (value == null ? 0 : value.hashCode()) ^ numModificationsLeft;
    }
}
