package io.github.utk003.util.misc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Indicates that the given class, method, field, etc. will be refactored in a future release.
 * <p>
 * This annotation can specify the refactored item's planned new location, if known,
 * as well as the planned refactor version, if known. For example:
 * <pre>
 * &#064;ScheduledForRefactor
 * public class LoremIpsum {
 *     &#064;ScheduledForRefactor(to = "DolorSitAmet", inVersion = "v0.0.0")
 *     public static void main(String[] args) {
 *     }
 * }
 * </pre>
 * <p>
 * If neither the {@code to} field nor the {@code inVersion} field is defined, this annotation can serve
 * as an open-ended marker for an upcoming API change (ie {@link ScheduledForRelease &#064;ScheduledForRelease}
 * or {@link org.jetbrains.annotations.ApiStatus.ScheduledForRemoval &#064;ApiStatus.ScheduledForRemoval}). In such
 * a case, the annotation can be updated in the future with a specific version and/or a more appropriate annotation.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 20, 2021
 * @since 3.0.0
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({TYPE, METHOD, FIELD, CONSTRUCTOR, PACKAGE})
@ScheduledForRelease(inVersion = "v3.0.0")
public @interface ScheduledForRefactor {
    /**
     * The target/resulting location of the refactor, if known.
     *
     * @return The final location of the refactored item, or {@code ""} if unknown
     */
    String to() default "";

    /**
     * The planned version for the refactor to occur in, if known.
     *
     * @return When the refactor will occur, or {@code ""} if unknown
     */
    String inVersion() default "";
}
