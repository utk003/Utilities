package io.github.utk003.util.math.solve;

public class QuarticFormulaTester {
    public static void main(String[] args) {
        QuarticFormula qf = new QuarticFormula();
        for (int i = -10; i <= 10; i++)
            for (int j = -10; j <= 10; j++)
                for (int k = -10; k <= 10; k++)
                    for (int l = -10; l <= 10; l++) {
                        int s = -(i + j + k + l), sp = i * j + i * k + j * k + i * l + j * l + k * l,
                                spp = -(i * j * k + i * j * l + i * k * l + j * k * l), p = i * j * k * l;
                        System.out.println("{" + i + ", " + j + ", " + k + ", " + l + "} -> " + qf.solve(1.0, s, sp, spp, p));
                    }
    }
}
