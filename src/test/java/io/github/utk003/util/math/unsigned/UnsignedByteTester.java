package io.github.utk003.util.math.unsigned;

import io.github.utk003.util.misc.Verifier;

public class UnsignedByteTester {
    public static void main(String[] args) {
        for (int i = 0; i <= 255; i++) {
            System.out.print("doing " + i + ":\t");
            UnsignedByte uByte = Verifier.requireNotNull(UnsignedByte.wrap(i));
            System.out.print(uByte.asBinaryString() + "\t");
            System.out.print(uByte.shortValue() + "\t");
            System.out.print(uByte.intValue() + "\t");
            System.out.print(uByte.longValue() + "\t");
            System.out.print(uByte.floatValue() + " \t");
            System.out.print(uByte.doubleValue() + "\n");
        }
    }
}
