package io.github.utk003.util.math.noise;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PerlinNoiseTester {
    public static void main(String[] args) throws Exception {
        PerlinNoise perlin = PerlinNoise.aperiodic(10);
        BufferedImage image = new BufferedImage(10000, 10000, BufferedImage.TYPE_INT_ARGB);
//        float min = 100, max = -100;
        float sqrt2 = (float) Math.sqrt(2);
        for (int x = 0; x < 10000; x++)
            for (int y = 0; y < 10000; y++) {
                float xf = x / 50.0f - 100.0f, yf = y / 50.0f - 100.0f;
                int val = Math.min(255, (int) ((perlin.get(xf, yf) / sqrt2 + 0.5f) * 256));
                image.setRGB(x, y, new Color(val, val, val).getRGB());
            }
        ImageIO.write(image, "png", new File("out2.png"));
    }
}
