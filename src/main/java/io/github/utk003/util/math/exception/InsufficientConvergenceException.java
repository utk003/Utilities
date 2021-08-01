package io.github.utk003.util.math.exception;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;

@ScheduledForRelease(inVersion = "v3.0.0")
@RequiresDocumentation
public class InsufficientConvergenceException extends IllegalStateException {
    public InsufficientConvergenceException() {
        super();
    }

    public InsufficientConvergenceException(@NotNull String message) {
        super(message);
    }

    public static @NotNull InsufficientConvergenceException getFor(@NotNull String func, @NotNull Object... args) {
        String argsString;
        switch (args.length) {
            case 0:
                argsString = "()";
                break;
            case 1:
                argsString = "(" + args[0] + ")";
                break;
            case 2:
                argsString = "(" + args[0] + ", " + args[1] + ")";
                break;
            case 3:
                argsString = "(" + args[0] + ", " + args[1] + ", " + args[2] + ")";
                break;
            default:
                StringBuilder builder = new StringBuilder();
                for (Object arg : args)
                    builder.append(", ").append(arg);
                argsString = "(" + builder.substring(2) + ")";
                break;
        }
        return new InsufficientConvergenceException("Exceeded maximum threshold count for " + func + argsString);
    }
}
