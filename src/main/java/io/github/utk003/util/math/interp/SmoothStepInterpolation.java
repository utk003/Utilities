package io.github.utk003.util.math.interp;

public abstract class SmoothStepInterpolation {
    private SmoothStepInterpolation() {
    }

    public static float interpolate(float e0, float e1, float weight) {
        if (weight <= 0.0f)
            return e0;
        if (weight >= 1.0f)
            return e1;
        return (e1 - e0) * (3.0f - weight * 2.0f) * weight * weight + e0;
    }

    public static double interpolate(double e0, double e1, double weight) {
        if (weight <= 0.0f)
            return e0;
        if (weight >= 1.0f)
            return e1;
        return (e1 - e0) * (3.0 - weight * 2.0) * weight * weight + e0;
    }
}
