package io.github.utk003.util.math.solve;

import io.github.utk003.util.math.complex.ComplexDouble;
import io.github.utk003.util.math.complex.ComplexFloat;

public class CubicFormulaTester {
    public static void main(String[] args) {
        CubicFormula cf = new CubicFormula();
        for (int i = -10; i <= 10; i++)
            for (int j = -10; j <= 10; j++)
                for (int k = -10; k <= 10; k++) {
                    int s = -(i + j + k), sp = i * j + i * k + j * k, p = -i * j * k;
                    CubicFormula.CubicSolution<ComplexFloat> sol = cf.solve(1.0f, s, sp, p);
                    sol.root1.round();
                    sol.root2.round();
                    sol.root3.round();
                    System.out.println("{" + i + ", " + j + ", " + k + "} -> " + sol);
                }
    }
}
