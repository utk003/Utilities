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
