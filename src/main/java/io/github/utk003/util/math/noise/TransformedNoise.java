package io.github.utk003.util.math.noise;

import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@ApiStatus.Experimental
@RequiresDocumentation
public final class TransformedNoise implements Noise {
    private final @NotNull Map<Noise, NoiseTransformationModule> MODULES = new HashMap<>();
    private final @NotNull Function<Noise, NoiseTransformationModule> FUNC = (n) -> new NoiseTransformationModule();

    public @NotNull NoiseTransformationModule getOrAddTransformation(@NotNull Noise noise) {
        return MODULES.computeIfAbsent(noise, FUNC);
    }
    public void removeTransformation(@NotNull Noise noise) {
        MODULES.remove(noise);
    }

    public void replaceTransformation(@NotNull Noise noise, @NotNull NoiseTransformationModule transformationModule) {
        MODULES.put(noise, transformationModule);
    }

    public void composeAll(@NotNull NoiseTransformationModule wrapper) {
        for (NoiseTransformationModule transformationModule : MODULES.values())
            transformationModule.compose(wrapper);
    }
    public void scaleAll(double scaleFactor) {
        for (NoiseTransformationModule transformationModule : MODULES.values())
            transformationModule.amplitudeModulationStrength *= scaleFactor;
    }

    @Override
    public float get(float x) {
        float noise = 0.0f;
        for (Map.Entry<Noise, NoiseTransformationModule> entry : MODULES.entrySet())
            noise += entry.getValue().get(entry.getKey(), x);
        return noise;
    }
    @Override
    public double get(double x) {
        double noise = 0.0;
        for (Map.Entry<Noise, NoiseTransformationModule> entry : MODULES.entrySet())
            noise += entry.getValue().get(entry.getKey(), x);
        return noise;
    }

    @Override
    public float get(float x, float y) {
        float noise = 0.0f;
        for (Map.Entry<Noise, NoiseTransformationModule> entry : MODULES.entrySet())
            noise += entry.getValue().get(entry.getKey(), x, y);
        return noise;
    }
    @Override
    public double get(double x, double y) {
        double noise = 0.0;
        for (Map.Entry<Noise, NoiseTransformationModule> entry : MODULES.entrySet())
            noise += entry.getValue().get(entry.getKey(), x, y);
        return noise;
    }

    @Override
    public float get(float x, float y, float z) {
        float noise = 0.0f;
        for (Map.Entry<Noise, NoiseTransformationModule> entry : MODULES.entrySet())
            noise += entry.getValue().get(entry.getKey(), x, y, z);
        return noise;
    }
    @Override
    public double get(double x, double y, double z) {
        double noise = 0.0;
        for (Map.Entry<Noise, NoiseTransformationModule> entry : MODULES.entrySet())
            noise += entry.getValue().get(entry.getKey(), x, y, z);
        return noise;
    }

    @Override
    public float get(float x, float y, float z, float w) {
        float noise = 0.0f;
        for (Map.Entry<Noise, NoiseTransformationModule> entry : MODULES.entrySet())
            noise += entry.getValue().get(entry.getKey(), x, y, z, w);
        return noise;
    }
    @Override
    public double get(double x, double y, double z, double w) {
        double noise = 0.0;
        for (Map.Entry<Noise, NoiseTransformationModule> entry : MODULES.entrySet())
            noise += entry.getValue().get(entry.getKey(), x, y, z, w);
        return noise;
    }

    @Override
    public float get(@NotNull float[] pos) {
        float[] newPos = new float[pos.length];
        float noise = 0.0f;
        for (Map.Entry<Noise, NoiseTransformationModule> entry : MODULES.entrySet()) {
            System.arraycopy(pos, 0, newPos, 0, pos.length);
            noise += entry.getValue().get(entry.getKey(), newPos);
        }
        return noise;
    }
    @Override
    public double get(@NotNull double[] pos) {
        double[] newPos = new double[pos.length];
        double noise = 0.0;
        for (Map.Entry<Noise, NoiseTransformationModule> entry : MODULES.entrySet()) {
            System.arraycopy(pos, 0, newPos, 0, pos.length);
            noise += entry.getValue().get(entry.getKey(), newPos);
        }
        return noise;
    }

    // AM * Noise[FM1 (x1 - S1), FM2 (x2 - S2), ..., FMn (xn - Sn)] + AS
    public static final class NoiseTransformationModule {
        public static final class DimensionalModulator {
            private static final @NotNull DimensionalModulator[] EMPTY_ARRAY = new DimensionalModulator[0];

            public double positionalOffset, frequencyModulationStrength;

            public DimensionalModulator() {
                this(0.0, 1.0);
            }
            public DimensionalModulator(double positionalOffset, double frequencyModulation) {
                this.positionalOffset = positionalOffset;
                this.frequencyModulationStrength = frequencyModulation;
            }

            private float apply(float f) {
                return (float) frequencyModulationStrength * (f - (float) positionalOffset);
            }
            private double apply(double d) {
                return frequencyModulationStrength * (d - positionalOffset);
            }

            private @NotNull DimensionalModulator compose(@NotNull DimensionalModulator doBefore) {
                // f1(x-p1) --> f1( f2(x-p2) - p1 ) --> f1( f2(x - p2 - p1/f2) ) --> f1.f2.(x - p2 - p1/f2)
                // f = f1.f2
                // p = p2 + p1/f2
                this.positionalOffset = doBefore.positionalOffset + this.positionalOffset / doBefore.frequencyModulationStrength;
                this.frequencyModulationStrength *= doBefore.frequencyModulationStrength;
                return this;
            }
            public static @NotNull DimensionalModulator compose(@NotNull DimensionalModulator dm1, @NotNull DimensionalModulator dm2) {
                return new DimensionalModulator().compose(dm1).compose(dm2);
            }
        }

        public double amplitudeModulationStrength;
        private @NotNull DimensionalModulator defaultDimensionalModulator;
        private @Nullable DimensionalModulator[] dimensionalModulators;

        private @NotNull DimensionalModulator d1Modulator, d2Modulator, d3Modulator, d4Modulator;

        // dimension >= 0
        public @NotNull DimensionalModulator getEffectiveDimensionalModulator(int dimension) {
            if (0 <= dimension && dimension < dimensionalModulators.length) {
                DimensionalModulator dimMod = dimensionalModulators[dimension];
                if (dimMod != null)
                    return dimMod;
            }
            return defaultDimensionalModulator;
        }

        public NoiseTransformationModule() {
            this(1.0, new DimensionalModulator(), DimensionalModulator.EMPTY_ARRAY);
        }
        public NoiseTransformationModule(double amplitudeModulation,
                                         @NotNull DimensionalModulator defaultDimensionalModulator,
                                         @Nullable DimensionalModulator[] allDimensionalModulators) {
            this.defaultDimensionalModulator = defaultDimensionalModulator;
            this.dimensionalModulators = allDimensionalModulators;

            this.d1Modulator = getEffectiveDimensionalModulator(0);
            this.d2Modulator = getEffectiveDimensionalModulator(1);
            this.d3Modulator = getEffectiveDimensionalModulator(2);
            this.d4Modulator = getEffectiveDimensionalModulator(3);

            amplitudeModulationStrength = amplitudeModulation;
        }

        public void setDimensionalModulator(int dimension, @Nullable DimensionalModulator modulator) {
            if (dimension >= dimensionalModulators.length) {
                DimensionalModulator[] modulators = new DimensionalModulator[dimension + 1];
                System.arraycopy(dimensionalModulators, 0, modulators, 0, dimensionalModulators.length);
                dimensionalModulators = modulators;
            }
            dimensionalModulators[dimension] = modulator;

            switch (dimension) {
                case 0:
                    this.d1Modulator = modulator != null ? modulator : defaultDimensionalModulator;
                    break;
                case 1:
                    this.d2Modulator = modulator != null ? modulator : defaultDimensionalModulator;
                    break;
                case 2:
                    this.d3Modulator = modulator != null ? modulator : defaultDimensionalModulator;
                    break;
                case 3:
                    this.d4Modulator = modulator != null ? modulator : defaultDimensionalModulator;
                    break;

                default:
                    break;
            }
        }
        public @Nullable DimensionalModulator getDimensionalModulator(int dimension) {
            return 0 <= dimension && dimension < dimensionalModulators.length ?
                    dimensionalModulators[dimension] : null;
        }

        public void setDefaultDimensionalModulator(@NotNull DimensionalModulator newDefault) {
            defaultDimensionalModulator = newDefault;

            this.d1Modulator = getEffectiveDimensionalModulator(0);
            this.d2Modulator = getEffectiveDimensionalModulator(1);
            this.d3Modulator = getEffectiveDimensionalModulator(2);
            this.d4Modulator = getEffectiveDimensionalModulator(3);
        }
        public @NotNull DimensionalModulator getDefaultModulator() {
            return defaultDimensionalModulator;
        }

        public void simplify() {
            int len = dimensionalModulators.length;
            while (len > 0)
                if (dimensionalModulators[--len] != null) {
                    len++;
                    break;
                }

            if (len == 0)
                dimensionalModulators = DimensionalModulator.EMPTY_ARRAY;
            else {
                DimensionalModulator[] modulators = new DimensionalModulator[len];
                System.arraycopy(dimensionalModulators, 0, modulators, 0, len);
                dimensionalModulators = modulators;
            }
        }

        private @NotNull NoiseTransformationModule compose(@NotNull NoiseTransformationModule wrapper) {
            this.amplitudeModulationStrength *= wrapper.amplitudeModulationStrength;

            DimensionalModulator[] dimensionalModulators =
                    this.dimensionalModulators.length >= wrapper.dimensionalModulators.length ?
                            this.dimensionalModulators : new DimensionalModulator[wrapper.dimensionalModulators.length];

            for (int i = 0; i < dimensionalModulators.length; i++)
                dimensionalModulators[i] = DimensionalModulator.compose(
                        this.getEffectiveDimensionalModulator(i),
                        wrapper.getEffectiveDimensionalModulator(i)
                );
            this.dimensionalModulators = dimensionalModulators;

            this.defaultDimensionalModulator = DimensionalModulator.compose(
                    this.defaultDimensionalModulator,
                    wrapper.defaultDimensionalModulator
            );

            this.d1Modulator = getEffectiveDimensionalModulator(0);
            this.d2Modulator = getEffectiveDimensionalModulator(1);
            this.d3Modulator = getEffectiveDimensionalModulator(2);
            this.d4Modulator = getEffectiveDimensionalModulator(3);

            return this;
        }
        public static @NotNull NoiseTransformationModule compose(@NotNull NoiseTransformationModule m1, @NotNull NoiseTransformationModule m2) {
            return new NoiseTransformationModule().compose(m1).compose(m2);
        }

        private float get(@NotNull Noise noise, float x) {
            return (float) amplitudeModulationStrength * noise.get(d1Modulator.apply(x));
        }
        private double get(@NotNull Noise noise, double x) {
            return amplitudeModulationStrength * noise.get(d1Modulator.apply(x));
        }

        private float get(@NotNull Noise noise, float x, float y) {
            return (float) amplitudeModulationStrength * noise.get(
                    d1Modulator.apply(x),
                    d2Modulator.apply(y)
            );
        }
        private double get(@NotNull Noise noise, double x, double y) {
            return amplitudeModulationStrength * noise.get(
                    d1Modulator.apply(x),
                    d2Modulator.apply(y)
            );
        }

        private float get(@NotNull Noise noise, float x, float y, float z) {
            return (float) amplitudeModulationStrength * noise.get(
                    d1Modulator.apply(x),
                    d2Modulator.apply(y),
                    d3Modulator.apply(z)
            );
        }
        private double get(@NotNull Noise noise, double x, double y, double z) {
            return amplitudeModulationStrength * noise.get(
                    d1Modulator.apply(x),
                    d2Modulator.apply(y),
                    d3Modulator.apply(z)
            );
        }

        private float get(@NotNull Noise noise, float x, float y, float z, float w) {
            return (float) amplitudeModulationStrength * noise.get(
                    d1Modulator.apply(x),
                    d2Modulator.apply(y),
                    d3Modulator.apply(z),
                    d4Modulator.apply(w)
            );
        }
        private double get(@NotNull Noise noise, double x, double y, double z, double w) {
            return amplitudeModulationStrength * noise.get(
                    d1Modulator.apply(x),
                    d2Modulator.apply(y),
                    d3Modulator.apply(z),
                    d4Modulator.apply(w)
            );
        }

        private float get(@NotNull Noise noise, @NotNull float[] pos) {
            for (int i = 0; i < pos.length; i++)
                pos[i] = getEffectiveDimensionalModulator(i).apply(pos[i]);
            return (float) amplitudeModulationStrength * noise.get(pos);
        }
        private double get(@NotNull Noise noise, @NotNull double[] pos) {
            for (int i = 0; i < pos.length; i++)
                pos[i] = getEffectiveDimensionalModulator(i).apply(pos[i]);
            return amplitudeModulationStrength * noise.get(pos);
        }
    }
}
