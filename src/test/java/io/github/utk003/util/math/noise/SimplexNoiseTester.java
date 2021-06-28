package io.github.utk003.util.math.noise;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SimplexNoiseTester {
    public static void main(String[] args) {
        test2D();
        test3D();
        test4D();
    }

    private static void test2D() {
        SimplexNoise simplex = SimplexNoise.aperiodic();
        float min = 100, max = -100;
        for (int x = 0; x < 10000; x++)
            for (int y = 0; y < 10000; y++) {
                float xf = x / 500.0f - 10.0f, yf = y / 500.0f - 10.0f;
                float value = simplex.get(xf, yf);
                min = Math.min(min, value);
                max = Math.max(max, value);
            }
        System.out.println(min + " " + max);
    }
    private static void test3D() {
        SimplexNoise simplex = SimplexNoise.aperiodic();
        float min = 100, max = -100;
        for (int x = 0; x < 500; x++)
            for (int y = 0; y < 500; y++)
                for (int z = 0; z < 500; z++) {
                    float xf = x / 100.0f - 2.5f, yf = y / 100.0f - 2.5f, zf = z / 100.0f - 2.5f;
                    float value = simplex.get(xf, yf, zf);
                    min = Math.min(min, value);
                    max = Math.max(max, value);
                }
        System.out.println(min + " " + max);
    }
    private static void test4D() {
        SimplexNoise simplex = SimplexNoise.aperiodic();
        float min = 100, max = -100;
        for (int x = 0; x < 100; x++)
            for (int y = 0; y < 100; y++)
                for (int z = 0; z < 100; z++)
                    for (int w = 0; w < 100; w++) {
                        float xf = x / 50.0f - 1.0f, yf = y / 50.0f - 1.0f, zf = z / 50.0f - 1.0f, wf = w / 50.0f - 1.0f;
                        float value = simplex.get(xf, yf, zf, wf);
                        min = Math.min(min, value);
                        max = Math.max(max, value);
                    }
        System.out.println(min + " " + max);
    }
}
