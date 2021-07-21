package io.github.utk003.util.misc.annotations;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * A helper annotation for {@link PlannedFeature &#064;PlannedFeature}
 * to enable repeated {@code @PlannedFeature} annotations.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 20, 2021
 * @see Repeatable
 * @since 3.0.0
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({TYPE, METHOD, FIELD, CONSTRUCTOR, PACKAGE})
@ScheduledForRelease(inVersion = "v3.0.0")
public @interface PlannedFeatures {
    /**
     * The {@code value()} method required by {@link Repeatable}.
     *
     * @return All planned features
     */
    PlannedFeature[] value();
}
