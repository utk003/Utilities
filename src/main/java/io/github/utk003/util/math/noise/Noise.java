package io.github.utk003.util.math.noise;

import org.jetbrains.annotations.NotNull;

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
