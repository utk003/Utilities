package io.github.utk003.util.misc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Indicates that the given class, method, field, etc. lacks documentation.
 * <p>
 * This annotation can be used on class, method, field, package, interface,
 * constructor, or (other) type definitions. For example:
 * <pre>
 * &#064;RequiresDocumentation
 * public class LoremIpsum {
 * }
 *
 * &#064;RequiresDocumentation
 * public static void main(String[] args) {
 * }
 * </pre>
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 20, 2021
 * @since 3.0.0
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({TYPE, METHOD, FIELD, CONSTRUCTOR, PACKAGE})
@ScheduledForRelease(inVersion = "v3.0.0")
public @interface RequiresDocumentation {
}
