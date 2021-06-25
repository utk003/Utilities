package io.github.utk003.util.math.solve;

public class CubicFormulaTester {
    public static void main(String[] args) {
        CubicFormula cf = new CubicFormula();
        for (int i = -10; i <= 10; i++)
            for (int j = -10; j <= 10; j++)
                for (int k = -10; k <= 10; k++) {
                    int s = -(i + j + k), sp = i * j + i * k + j * k, p = -i * j * k;
                    System.out.println("{" + i + ", " + j + ", " + k + "} -> " + cf.solve(1.0, s, sp, p));
                }
    }
}
