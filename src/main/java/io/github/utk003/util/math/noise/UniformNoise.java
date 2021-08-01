package io.github.utk003.util.math.noise;

import io.github.utk003.util.math.advanced.Erf;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;

import static io.github.utk003.util.math.Constants.SQRT_2;

@ScheduledForRelease(inVersion = "v3.0.0")
@RequiresDocumentation
public final class UniformNoise implements Noise {
    private final @NotNull Noise SOURCE;
    private final double STANDARD_DEVIATION;
    public UniformNoise(@NotNull Noise sourceNoise, double standardDeviation) {
        SOURCE = sourceNoise;
        STANDARD_DEVIATION = standardDeviation;
    }

    public @NotNull Noise getSourceRaw() {
        return SOURCE;
    }
    public <N extends Noise> @NotNull N getSource() {
        //noinspection unchecked
        return (N) SOURCE;
    }

    private float getUniformEquivalent(float f) {
        return Erf.erf(f / (float) (STANDARD_DEVIATION * SQRT_2));
    }
    private double getUniformEquivalent(double d) {
        return Erf.erf(d / (STANDARD_DEVIATION * SQRT_2));
    }

    @Override
    public float get(float x) {
        return getUniformEquivalent(SOURCE.get(x));
    }
    @Override
    public double get(double x) {
        return getUniformEquivalent(SOURCE.get(x));
    }

    @Override
    public float get(float x, float y) {
        return getUniformEquivalent(SOURCE.get(x, y));
    }
    @Override
    public double get(double x, double y) {
        return getUniformEquivalent(SOURCE.get(x, y));
    }
    @Override
    public float get(float x, float y, float z) {
        return getUniformEquivalent(SOURCE.get(x, y, z));
    }
    @Override
    public double get(double x, double y, double z) {
        return getUniformEquivalent(SOURCE.get(x, y, z));
    }
    @Override
    public float get(float x, float y, float z, float w) {
        return getUniformEquivalent(SOURCE.get(x, y, z, w));
    }
    @Override
    public double get(double x, double y, double z, double w) {
        return getUniformEquivalent(SOURCE.get(x, y, z, w));
    }
    @Override
    public float get(@NotNull float[] pos) {
        return getUniformEquivalent(SOURCE.get(pos));
    }
    @Override
    public double get(@NotNull double[] pos) {
        return getUniformEquivalent(SOURCE.get(pos));
    }
}
