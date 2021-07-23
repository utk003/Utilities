package io.github.utk003.util.misc;

public class VerifierTester {
    @SuppressWarnings({"UnusedAssignment", "ConstantConditions"})
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
        Verifier.requireFalse(false, new RuntimeException());
        Verifier.requireFalse(false, RuntimeException.class);
        Verifier.requireFalse(false, RuntimeException.class, "");

        Verifier.requireTrue(true);
        Verifier.requireTrue(true, "");
        Verifier.requireTrue(true, new RuntimeException());
        Verifier.requireTrue(true, RuntimeException.class);
        Verifier.requireTrue(true, RuntimeException.class, "");

        // null & not-null
        Verifier.requireNull(null);
        Verifier.requireNull(null, "");
        Verifier.requireNull(null, new RuntimeException());
        Verifier.requireNull(null, RuntimeException.class);
        Verifier.requireNull(null, RuntimeException.class, "");

        tempS = Verifier.requireNotNull("");
        tempS = Verifier.requireNotNull("", "");
        tempS = Verifier.requireNotNull("", new RuntimeException());
        tempS = Verifier.requireNotNull("", RuntimeException.class);
        tempS = Verifier.requireNotNull("", RuntimeException.class, "");

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

        tempB = Verifier.requireEqual(false, false, new RuntimeException());
        tempI = Verifier.requireEqual(0, 0, new RuntimeException());
        tempL = Verifier.requireEqual(0L, 0L, new RuntimeException());
        tempF = Verifier.requireEqual(0.0f, 0.0f, new RuntimeException());
        tempD = Verifier.requireEqual(0.0, 0.0, new RuntimeException());

        tempB = Verifier.requireEqual(false, false, RuntimeException.class);
        tempI = Verifier.requireEqual(0, 0, RuntimeException.class);
        tempL = Verifier.requireEqual(0L, 0L, RuntimeException.class);
        tempF = Verifier.requireEqual(0.0f, 0.0f, RuntimeException.class);
        tempD = Verifier.requireEqual(0.0, 0.0, RuntimeException.class);

        tempB = Verifier.requireEqual(false, false, RuntimeException.class, "");
        tempI = Verifier.requireEqual(0, 0, RuntimeException.class, "");
        tempL = Verifier.requireEqual(0L, 0L, RuntimeException.class, "");
        tempF = Verifier.requireEqual(0.0f, 0.0f, RuntimeException.class, "");
        tempD = Verifier.requireEqual(0.0, 0.0, RuntimeException.class, "");

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

        Verifier.requireNotEqual(false, true, new RuntimeException());
        Verifier.requireNotEqual(0, 1, new RuntimeException());
        Verifier.requireNotEqual(0L, 1L, new RuntimeException());
        Verifier.requireNotEqual(0.0f, 1.0f, new RuntimeException());
        Verifier.requireNotEqual(0.0, 1.0, new RuntimeException());

        Verifier.requireNotEqual(false, true, RuntimeException.class);
        Verifier.requireNotEqual(0, 1, RuntimeException.class);
        Verifier.requireNotEqual(0L, 1L, RuntimeException.class);
        Verifier.requireNotEqual(0.0f, 1.0f, RuntimeException.class);
        Verifier.requireNotEqual(0.0, 1.0, RuntimeException.class);

        Verifier.requireNotEqual(false, true, RuntimeException.class, "");
        Verifier.requireNotEqual(0, 1, RuntimeException.class, "");
        Verifier.requireNotEqual(0L, 1L, RuntimeException.class, "");
        Verifier.requireNotEqual(0.0f, 1.0f, RuntimeException.class, "");
        Verifier.requireNotEqual(0.0, 1.0, RuntimeException.class, "");

        // .equals()
        Verifier.requireEqual("", "");
        Verifier.requireEqual("", "", "");
        Verifier.requireEqual("", "", new RuntimeException());
        Verifier.requireEqual("", "", RuntimeException.class);
        Verifier.requireEqual("", "", RuntimeException.class, "");

        Verifier.requireNotEqual(null, "");
        Verifier.requireNotEqual(null, "", "");
        Verifier.requireNotEqual(null, "", new RuntimeException());
        Verifier.requireNotEqual(null, "", RuntimeException.class);
        Verifier.requireNotEqual(null, "", RuntimeException.class, "");

        // ==
        //noinspection StringOperationCanBeSimplified
        String s1 = new String(""), s2 = new String(""); // create 2 *new* Strings

        tempS = Verifier.requireExactlyEqual(s1, s1);
        tempS = Verifier.requireExactlyEqual(s1, s1, "");
        tempS = Verifier.requireExactlyEqual(s1, s1, new RuntimeException());
        tempS = Verifier.requireExactlyEqual(s1, s1, RuntimeException.class);
        tempS = Verifier.requireExactlyEqual(s1, s1, RuntimeException.class, "");

        Verifier.requireNotExactlyEqual(s1, s2);
        Verifier.requireNotExactlyEqual(s1, s2, "");
        Verifier.requireNotExactlyEqual(s1, s2, new RuntimeException());
        Verifier.requireNotExactlyEqual(s1, s2, RuntimeException.class);
        Verifier.requireNotExactlyEqual(s1, s2, RuntimeException.class, "");

        // complete
        System.out.println("Verifier tested successfully");
    }
}
