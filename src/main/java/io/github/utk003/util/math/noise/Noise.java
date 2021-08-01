package io.github.utk003.util.math.noise;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;

@ScheduledForRelease(inVersion = "v3.0.0")
@RequiresDocumentation
public interface Noise {
    float get(float x);
    double get(double x);

    float get(float x, float y);
    double get(double x, double y);

    float get(float x, float y, float z);
    double get(double x, double y, double z);

    float get(float x, float y, float z, float w);
    double get(double x, double y, double z, double w);

    float get(@NotNull float[] pos);
    double get(@NotNull double[] pos);
}
