package io.github.utk003.util.math.interp;

public abstract class LinearStepInterpolation {
    private LinearStepInterpolation() {
    }

    public static float interpolate(float e0, float e1, float weight) {
        if (weight <= 0.0f)
            return e0;
        if (weight >= 1.0f)
            return e1;
        return (e1 - e0) * weight + e0;
    }

    public static double interpolate(double e0, double e1, double weight) {
        if (weight <= 0.0f)
            return e0;
        if (weight >= 1.0f)
            return e1;
        return (e1 - e0) * weight + e0;
    }
}
