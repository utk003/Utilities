package io.github.utk003.util.misc;

public class VerifierTester {
    public static void main(String[] args) {
        // check return types with these variables
        String tempS;
        int tempI;
        long tempL;
        float tempF;
        double tempD;
        boolean tempB;

        // false & true
        Verifier.requireFalse(false);
        Verifier.requireFalse(false, "");

        Verifier.requireTrue(true);
        Verifier.requireTrue(true, "");

        // null & not-null
        Verifier.requireNull(null);
        Verifier.requireNull(null, "");

        tempS = Verifier.requireNotNull("");
        tempS = Verifier.requireNotNull("", "");

        // primitives ==
        tempB = Verifier.requireEqual(false, false);
        tempI = Verifier.requireEqual(0, 0);
        tempL = Verifier.requireEqual(0L, 0L);
        tempF = Verifier.requireEqual(0.0f, 0.0f);
        tempD = Verifier.requireEqual(0.0, 0.0);

        tempB = Verifier.requireEqual(false, false, "");
        tempI = Verifier.requireEqual(0, 0, "");
        tempL = Verifier.requireEqual(0L, 0L, "");
        tempF = Verifier.requireEqual(0.0f, 0.0f, "");
        tempD = Verifier.requireEqual(0.0, 0.0, "");

        // primitives !=
        Verifier.requireNotEqual(false, true);
        Verifier.requireNotEqual(0, 1);
        Verifier.requireNotEqual(0L, 1L);
        Verifier.requireNotEqual(0.0f, 1.0f);
        Verifier.requireNotEqual(0.0, 1.0);

        Verifier.requireNotEqual(false, true, "");
        Verifier.requireNotEqual(0, 1, "");
        Verifier.requireNotEqual(0L, 1L, "");
        Verifier.requireNotEqual(0.0f, 1.0f, "");
        Verifier.requireNotEqual(0.0, 1.0, "");

        // .equals()
        Verifier.requireEqual("", "");
        Verifier.requireEqual("", "", "");

        Verifier.requireNotEqual(null, "");
        Verifier.requireNotEqual(null, "", "");

        // ==
        String s1 = new String(""), s2 = new String(""); // create 2 *new* Strings

        tempS = Verifier.requireExactlyEqual(s1, s1);
        tempS = Verifier.requireExactlyEqual(s1, s1, "");

        Verifier.requireNotExactlyEqual(s1, s2);
        Verifier.requireNotExactlyEqual(s1, s2, "");

        // complete
        System.out.println("Verifier tested successfully");
    }
}
