package io.github.utk003.util.data.collection.bijection;

import io.github.utk003.util.data.collection.slice.Slice;
import org.jetbrains.annotations.NotNull;

/**
 * An exception that is thrown to indicate that a {@link Bijection}
 * is in an illegal state, relating either to the execution of
 * a particular operation or to an internal error.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 29, 2021
 * @see Bijection
 * @since 3.0.0
 */
public class IllegalBijectiveStateException extends IllegalStateException {
    /**
     * Constructs an IllegalBijectiveStateException with no detail message.
     * A detail message is a String that describes this particular exception.
     */
    public IllegalBijectiveStateException() {
        super();
    }

    /**
     * Constructs an IllegalBijectiveStateException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * @param s the String that contains a detailed message
     */
    public IllegalBijectiveStateException(String s) {
        super(s);
    }

    /**
     * Returns a new {@code IllegalBijectiveStateException}
     * with a message reflecting an internal loss of bijectivity.
     *
     * @return A new {@code IllegalBijectiveStateException} reflecting loss of bijectivity
     */
    public static @NotNull IllegalBijectiveStateException lossOfBijectivity() {
        return new IllegalBijectiveStateException("Internal loss of bijectivity");
    }
    /**
     * Returns a new {@code IllegalBijectiveStateException}
     * with a message reflecting that the specified pairing
     * could not be added to a {@link Bijection} due to a
     * collision with a pre-existing pairing in that bijection.
     *
     * @param pairing The {@link Bijection.Pairing Pairing} that couldn't be added to the {@code Bijection}
     * @return A new {@code IllegalBijectiveStateException} reflecting a pairing collision
     */
    public static @NotNull IllegalBijectiveStateException failedPairingAddition(@NotNull Bijection.Pairing<?, ?> pairing) {
        return new IllegalBijectiveStateException("Pairing " + pairing + " conflicts with pre-existing pairing");
    }
}
