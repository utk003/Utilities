package io.github.utk003.util.misc.annotations;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * Indicates that the given class, method, field, etc. will
 * be modified in the future to accommodate new features.
 * <p>
 * Any item marked by this annotation is has upcoming features,
 * each described in its own {@code @PlannedFeature} annotation.
 * This annotation can specify planned version of release, if known.
 * <p>
 * Example usages include:
 * <pre>
 * &#064;PlannedFeature("public static void main(String[] args)")
 * &#064;PlannedFeature(
 *     value = "protected void dolorSit(int amet)",
 *     description = "Do &lt;<em>some action</em>&gt;",
 *     version = "v0.0.0"
 * )
 * public class LoremIpsum {
 * }
 * </pre>
 * <p>
 * Multiple {@code @PlannedFeature} annotations can be applied to a single
 * definition, as shown in the example above.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 20, 2021
 * @since 3.0.0
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({TYPE, METHOD, FIELD, CONSTRUCTOR, PACKAGE})
@ScheduledForRelease(inVersion = "v3.0.0")
@Repeatable(PlannedFeatures.class)
public @interface PlannedFeature {
    /**
     * The key element of the planned feature
     *
     * @return A "title" for or core aspect of the feature
     */
    String value();
    /**
     * A brief description of the feature to be added, if otherwise unclear.
     *
     * @return An explanation of the feature, or optionally {@code ""}
     */
    String description() default "";
    /**
     * The planned version for the feature to be released in, if known.
     *
     * @return When the feature will be released, or {@code ""} if unknown
     */
    String inVersion() default "";
}
