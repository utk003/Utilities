package io.github.utk003.util.misc.annotations;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * Indicates that the given class, method, field, etc. will be officially "released" in the future.
 * <p>
 * Any item marked by this annotation is not quite ready to be considered an official part of the API
 * but is planned be released by the specified version. This annotation plays a role half way between
 * no annotation and the {@link org.jetbrains.annotations.ApiStatus.Experimental &#064;ApiStatus.Experimental}
 * annotation: The API is not quite as volatile as indicated by {@code @ApiStatus.Experimental},
 * but still possibly more volatile than a "fully released" feature.
 * <p>
 * This annotation can specify planned version of release, if known. For example:
 * <pre>
 * &#064;ScheduledForRelease
 * public class LoremIpsum {
 *     &#064;ScheduledForRelease(inVersion = "v0.0.0")
 *     public static void main(String[] args) {
 *     }
 * }
 * </pre>
 * <p>
 * If the {@code inVersion} fields is not defined, this annotation can serve as an open-ended marker
 * for an upcoming release. In such a case, the annotation can be updated in the future with a specific version.
 * <p>
 * The removal-equivalent of this annotation is
 * {@link org.jetbrains.annotations.ApiStatus.ScheduledForRemoval &#064;ApiStatus.ScheduledForRemoval}.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 20, 2021
 * @since 3.0.0
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({TYPE, METHOD, FIELD, CONSTRUCTOR, PACKAGE})
@ScheduledForRelease(inVersion = "v3.0.0")
public @interface ScheduledForRelease {
    /**
     * The planned version for the release to occur in, if known.
     *
     * @return When the item will be released, or {@code ""} if unknown
     */
    String inVersion() default "";
}
