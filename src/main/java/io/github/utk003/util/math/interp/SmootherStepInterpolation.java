package io.github.utk003.util.math.interp;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;

@ScheduledForRelease(inVersion = "v3.0.0")
@RequiresDocumentation
public abstract class SmootherStepInterpolation {
    private SmootherStepInterpolation() {
    }

    public static float interpolate(float e0, float e1, float weight) {
        if (weight <= 0.0f)
            return e0;
        if (weight >= 1.0f)
            return e1;
        return (e1 - e0) * ((weight * (weight * 6.0f - 15.0f) + 10.0f) * weight * weight * weight) + e0;
    }

    public static double interpolate(double e0, double e1, double weight) {
        if (weight <= 0.0)
            return e0;
        if (weight >= 1.0)
            return e1;
        return (e1 - e0) * ((weight * (weight * 6.0 - 15.0) + 10.0) * weight * weight * weight) + e0;
    }
}
