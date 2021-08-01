package io.github.utk003.util.math.interp;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ScheduledForRelease(inVersion = "v3.0.0")
@RequiresDocumentation
public interface Interpolatable {
    enum InterpolationType {
        LINEAR_STEP {
            @Override
            public float interpolate(float e0, float e1, float weight) {
                return LinearStepInterpolation.interpolate(e0, e1, weight);
            }
            @Override
            public double interpolate(double e0, double e1, double weight) {
                return LinearStepInterpolation.interpolate(e0, e1, weight);
            }
        },
        SMOOTH_STEP {
            @Override
            public float interpolate(float e0, float e1, float weight) {
                return SmoothStepInterpolation.interpolate(e0, e1, weight);
            }
            @Override
            public double interpolate(double e0, double e1, double weight) {
                return SmoothStepInterpolation.interpolate(e0, e1, weight);
            }
        },
        SMOOTHER_STEP {
            @Override
            public float interpolate(float e0, float e1, float weight) {
                return SmootherStepInterpolation.interpolate(e0, e1, weight);
            }
            @Override
            public double interpolate(double e0, double e1, double weight) {
                return SmootherStepInterpolation.interpolate(e0, e1, weight);
            }
        };

        public abstract float interpolate(float e0, float e1, float weight);
        public abstract double interpolate(double e0, double e1, double weight);

        public static final @NotNull InterpolationType DEFAULT = SMOOTH_STEP;
    }

    @NotNull InterpolationType getInterpolationType();
    @Contract("_ -> this")
    @NotNull Interpolatable setInterpolationType(@NotNull InterpolationType interpolationType);
}
