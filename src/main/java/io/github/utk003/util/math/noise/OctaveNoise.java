package io.github.utk003.util.math.noise;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

@ScheduledForRelease(inVersion = "v3.0.0")
@RequiresDocumentation
public final class OctaveNoise implements Noise {
    @FunctionalInterface
    public interface NoiseConstructor {
        Noise getNoiseMaker(long octaveNumber);
    }

    private final @NotNull NoiseConstructor NOISE_CONSTRUCTOR;
    public OctaveNoise(@NotNull NoiseConstructor noiseMaker) {
        NOISE_CONSTRUCTOR = noiseMaker;
        NOISE_LIST = new NoiseList(noiseMaker);
    }
    public OctaveNoise(int numOctaves, @NotNull NoiseConstructor noiseMaker) {
        this(noiseMaker);
        setOctaveCount(numOctaves);
    }

    private int numOctaves = 6;
    private double persistence = 0.35, lacunarity = 2.0, frequency = 1.0;

    public int getOctaveCount() {
        return numOctaves;
    }
    public void setOctaveCount(int numOctaves) {
        this.numOctaves = numOctaves;
    }

    public double getPersistence() {
        return persistence;
    }
    public void setPersistence(double persistence) {
        this.persistence = persistence;
    }

    public double getLacunarity() {
        return lacunarity;
    }
    public void setLacunarity(double lacunarity) {
        this.lacunarity = lacunarity;
    }

    public double getFrequency() {
        return frequency;
    }
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double unadjustedMaximumValue() {
        // v1 + p * v2 + pp * v3 + ... + p(o-1) * vo
        double max = 0.0, persistence = 1.0, absPersistence = Math.abs(this.persistence);
        for (int i = 0; i < numOctaves; i++) {
            max += persistence;
            persistence *= absPersistence;
        }
        return max;
    }

    private class NoiseList implements Iterable<Noise> {
        private NoiseList(@NotNull NoiseConstructor noiseConstructor) {
            tail = ROOT = new Node(noiseConstructor);
        }

        class Node {
            final int OCTAVE_INDEX;
            final @NotNull Noise NOISE;
            @Nullable Node next;

            private Node(@NotNull NoiseConstructor noiseConstructor) {
                OCTAVE_INDEX = 0;
                NOISE = noiseConstructor.getNoiseMaker(0);
            }
            private Node(int ind) {
                OCTAVE_INDEX = ind;
                NOISE = NOISE_CONSTRUCTOR.getNoiseMaker(ind);
            }
        }

        private final @NotNull Node ROOT;
        private @NotNull Node tail;

        private synchronized void extendList(int targetOctaves) {
            int numOctavesInList = tail.OCTAVE_INDEX + 1;
            while (targetOctaves > numOctavesInList)
                tail = tail.next = new Node(numOctavesInList++);
        }
        public @NotNull Iterator<Noise> iterator() {
            return new Itr();
        }

        private class Itr implements Iterator<Noise> {
            private @Nullable Node node = ROOT;
            private final int NUM_OCTAVES = numOctaves;
            public Itr() {
                extendList(NUM_OCTAVES);
            }

            @Override
            public boolean hasNext() {
                return node != null && node.OCTAVE_INDEX < NUM_OCTAVES;
            }
            @Override
            public Noise next() {
                //noinspection ConstantConditions
                Noise noise = node.NOISE;
                node = node.next;
                return noise;
            }
        }
    }

    private final @NotNull NoiseList NOISE_LIST;

    @Override
    public float get(float x) {
        float sum = 0.0f;
        double persistence = 1.0;

        x *= frequency;

        for (Noise noise : NOISE_LIST) {
            sum += persistence * noise.get(x);
            persistence *= this.persistence;

            x *= lacunarity;
        }

        return sum;
    }
    @Override
    public double get(double x) {
        double sum = 0.0, persistence = 1.0;

        x *= frequency;

        for (Noise noise : NOISE_LIST) {
            sum += persistence * noise.get(x);
            persistence *= this.persistence;

            x *= lacunarity;
        }

        return sum;
    }
    @Override
    public float get(float x, float y) {
        float sum = 0.0f;
        double persistence = 1.0;

        x *= frequency;
        y *= frequency;

        for (Noise noise : NOISE_LIST) {
            sum += persistence * noise.get(x, y);
            persistence *= this.persistence;

            x *= lacunarity;
            y *= lacunarity;
        }

        return sum;
    }
    @Override
    public double get(double x, double y) {
        double sum = 0.0, persistence = 1.0;

        x *= frequency;
        y *= frequency;

        for (Noise noise : NOISE_LIST) {
            sum += persistence * noise.get(x, y);
            persistence *= this.persistence;

            x *= lacunarity;
            y *= lacunarity;
        }

        return sum;
    }
    @Override
    public float get(float x, float y, float z) {
        float sum = 0.0f;
        double persistence = 1.0;

        x *= frequency;
        y *= frequency;
        z *= frequency;

        for (Noise noise : NOISE_LIST) {
            sum += persistence * noise.get(x, y, z);
            persistence *= this.persistence;

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
        }

        return sum;
    }
    @Override
    public double get(double x, double y, double z) {
        double sum = 0.0, persistence = 1.0;

        x *= frequency;
        y *= frequency;
        z *= frequency;

        for (Noise noise : NOISE_LIST) {
            sum += persistence * noise.get(x, y, z);
            persistence *= this.persistence;

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
        }

        return sum;
    }
    @Override
    public float get(float x, float y, float z, float w) {
        float sum = 0.0f;
        double persistence = 1.0;

        x *= frequency;
        y *= frequency;
        z *= frequency;
        w *= frequency;

        for (Noise noise : NOISE_LIST) {
            sum += persistence * noise.get(x, y, z, w);
            persistence *= this.persistence;

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
            w *= lacunarity;
        }

        return sum;
    }
    @Override
    public double get(double x, double y, double z, double w) {
        double sum = 0.0, persistence = 1.0;

        x *= frequency;
        y *= frequency;
        z *= frequency;
        w *= frequency;

        for (Noise noise : NOISE_LIST) {
            sum += persistence * noise.get(x, y, z, w);
            persistence *= this.persistence;

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
            w *= lacunarity;
        }

        return sum;
    }

    private void multiply(@NotNull float[] arr, float mult) {
        for (int i = 0; i < arr.length; i++)
            arr[i] *= mult;
    }
    private void multiply(@NotNull double[] arr, double mult) {
        for (int i = 0; i < arr.length; i++)
            arr[i] *= mult;
    }

    @Override
    public float get(@NotNull float[] pos) {
        float sum = 0.0f;
        double persistence = 1.0;

        multiply(pos, (float) frequency);

        for (Noise noise : NOISE_LIST) {
            sum += persistence * noise.get(pos);
            persistence *= this.persistence;

            multiply(pos, (float) lacunarity);
        }

        return sum;
    }
    @Override
    public double get(@NotNull double[] pos) {
        double sum = 0.0, persistence = 1.0;

        multiply(pos, frequency);

        for (Noise noise : NOISE_LIST) {
            sum += persistence * noise.get(pos);
            persistence *= this.persistence;

            multiply(pos, lacunarity);
        }

        return sum;
    }
}
