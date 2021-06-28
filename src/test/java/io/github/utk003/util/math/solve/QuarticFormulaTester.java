package io.github.utk003.util.math.solve;

import io.github.utk003.util.math.complex.ComplexDouble;
import io.github.utk003.util.math.complex.ComplexFloat;
import org.jetbrains.annotations.NotNull;

public class QuarticFormulaTester {
    public static void main(String[] args) {
        QuarticFormula qf = new QuarticFormula();
//        test(qf, 1, 1, 1, 1);
        for (int i = -10; i <= 10; i++)
            for (int j = -10; j <= 10; j++)
                for (int k = -10; k <= 10; k++)
                    for (int l = -10; l <= 10; l++)
                        test(qf, i, j, k, l);
    }

    private static @NotNull String string(int i) {
        return i > 0 ? " + " + (i == 1 ? "" : i) : " - " + (i == -1 ? "" : (-i));
    }

    private static void test(@NotNull QuarticFormula qf, int i, int j, int k, int l) {
        int s = -(i + j + k + l), sp = i * j + i * k + j * k + i * l + j * l + k * l,
                spp = -(i * j * k + i * j * l + i * k * l + j * k * l), p = i * j * k * l;
//        System.out.print("{" + i + ", " + j + ", " + k + ", " + l + "} -> x^4");
//        if (s != 0)
//            System.out.print(string(s) + "x^3");
//        if (sp != 0)
//            System.out.print(string(sp) + "x^2");
//        if (spp != 0)
//            System.out.print(string(spp) + "x");
//        if (p != 0)
//            System.out.print((p > 0 ? " + " + p : " - " + (-p)));
//        System.out.println();
        QuarticFormula.QuarticSolution<ComplexFloat> sol = qf.solve(1.0f, s, sp, spp, p);
        sol.root1.round();
        sol.root2.round();
        sol.root3.round();
        sol.root4.round();
        System.out.println("{" + i + ", " + j + ", " + k + ", " + l + "} -> " + sol);
    }
}
