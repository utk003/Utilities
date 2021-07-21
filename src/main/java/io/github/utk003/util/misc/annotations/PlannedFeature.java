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
