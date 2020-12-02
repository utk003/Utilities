package me.utk.util.misc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * An abstract utility class for thread-centric utility functions.
 * <p>
 * This class provides these methods:
 * <ul>
 * <li>The {@code sleep} methods which pause the thread environment
 *     from which they were called for a specified amount of time.
 * </ul>
 * <p>
 * Additionally, this class contains a nested class {@code ThreadLocker},
 * which also provides some thread-locking-related functionality.
 *
 * @author Utkarsh Priyam
 * @version 12/1/20
 * @see ThreadLocker
 */
public abstract class ThreadUtil {
    private ThreadUtil() {
    }

    /**
     * Pauses the current thread environment for the specified number of milliseconds.
     *
     * @param ms The number of milliseconds to pause for
     */
    public static void sleep(long ms) {
        sleep(ms, TimeUnit.MILLISECONDS);
    }
    /**
     * Pauses the current thread environment for the specified number of time units,
     * which is itself specified through the argument {@code units}.
     *
     * @param time  The number of time units to pause for
     * @param units The units for the pause time
     */
    public static void sleep(long time, TimeUnit units) {
        try {
            Thread.sleep(units.toMillis(time));
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * A dual-purpose utility class focused on providing thread-locking functionality.
     * <p>
     * The {@code ThreadLocker} can perform two functions, depending on its initialization
     * and internal state during runtime:
     * <ul>
     * <li>When initialized appropriately and up until being deactivated by a call to the {@code deactivate} method,
     *     a {@code ThreadLocker} instance can pause the current thread for the specified time
     *     interval using {@link Object#wait()} and {@link Object#notify()}.
     * <li>Regardless of initialization or deactivation status, a {@code ThreadLocker} instance can also
     *     be used independently in client applications as an internal thread-locking object by
     *     calling the {@code wait} and {@code notify} methods directly.
     * </ul>
     *
     * @author Utkarsh Priyam
     * @version 12/1/20
     * @see Object#wait()
     * @see Object#notify()
     * @see Object#notifyAll()
     */
    public static class ThreadLocker {
        private final ScheduledExecutorService WAITER;

        /**
         * Creates a {@code ThreadLocker} in its deactivated state.
         */
        public ThreadLocker() {
            this(false);
        }
        /**
         * Creates a {@code ThreadLocker} in an activated or deactivated state depending on the given argument.
         *
         * @param canThreadWait If {@code true}, this object is initialized in an activated state; otherwise,
         *                      if {@code false}, the object is initialized in a deactivated state.
         */
        public ThreadLocker(boolean canThreadWait) {
            WAITER = canThreadWait ? Executors.newSingleThreadScheduledExecutor() : null;
        }

        /**
         * Pauses the current thread for the specified length of time if and only if
         * this {@code ThreadLocker} instance is in an activated state.
         * <p>
         * This method pauses the current thread using this {@code ThreadLocker} instance's
         * {@link Object#wait()} method and unpauses the thread by a subsequent call to {@link Object#notify()}.
         *
         * @param time  The number of time units to pause for
         * @param units The units for the pause time
         * @throws IllegalStateException If this {@code ThreadLocker} instance is currently in a deactivated state,
         *                               whether from initialization or as a result of a call to the {@code deactivate} method.
         * @throws InterruptedException  If using the {@code wait} method instead would throw this exception
         * @see Object#wait()
         * @see Object#notify()
         * @see Object#notifyAll()
         */
        public void waitFor(long time, TimeUnit units) throws InterruptedException {
            if (WAITER == null || WAITER.isShutdown())
                throw new IllegalStateException("This ThreadLocker cannot pause threads");

            WAITER.schedule(() -> {
                synchronized (this) {
                    notifyAll();
                }
            }, time, units);
            wait();
        }

        /**
         * Switches this {@code ThreadLocker} instance to a deactivated state.
         * <p>
         * When deactivated, this {@code ThreadLocker} instance can no longer use the {@code waitFor} method,
         * or else it will throw an exception. However, this object can still be used to block threads direcly
         * using {@link Object#wait()} and {@link Object#notify()}.
         *
         * @see Object#wait()
         * @see Object#notify()
         * @see Object#notifyAll()
         */
        public void deactivate() {
            if (WAITER != null)
                WAITER.shutdownNow();
        }
    }
}
