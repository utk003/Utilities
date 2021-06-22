package io.github.utk003.util.data.collection.bijection;

import java.util.EnumMap;

public class MapBackedBijectionTester {
    private enum Enum1 {
        V0, V1, V2, V3, V4, V5, V6, V7, V8, V9
    }
    private enum Enum2 {
        V0, V1, V2, V3, V4, V5, V6, V7, V8, V9
    }


    public static void main(String[] args) {
        /*
         * Expected output:
         *
         * {}
         * {V0=V0, V1=V1, V2=V2, V3=V3, V4=V4, V5=V5, V6=V6, V7=V7, V8=V8, V9=V9}
         * {V0=V0, V1=V1, V2=V2, V3=V3, V4=V4, V5=V5, V6=V6, V7=V7, V8=V8, V9=V9}
         * {V0=V0, V1=V1, V2=V2, V3=V3, V4=V4, V5=V5, V6=V6, V7=V7, V8=V8, V9=V9}
         * {}
         */

        MapBackedBijection<Enum1, Enum2> bijection = new MapBackedBijection<>(new EnumMap<>(Enum1.class), new EnumMap<>(Enum2.class));

        // start with an empty bijection
        System.out.println(bijection);

        // output should be all corresponding values map together
        // because all other pairs fail due to conflicts with existing mappings
        for (Enum1 e1 : Enum1.values())
            for (Enum2 e2 : Enum2.values())
                bijection.addPairing(e1, e2);
        System.out.println(bijection);

        // add everything again --> no change to bijection
        for (Enum1 e1 : Enum1.values())
            for (Enum2 e2 : Enum2.values())
                bijection.addPairing(e1, e2);
        System.out.println(bijection);

        // remove all pairings except the ones that are in the bijection --> should not change
        for (Enum1 e1 : Enum1.values())
            for (Enum2 e2 : Enum2.values())
                if (!bijection.containsPairing(e1, e2))
                    bijection.removePairing(e1, e2);
        System.out.println(bijection);

        // remove everything --> bijection should be empty
        for (Enum1 e1 : Enum1.values())
            for (Enum2 e2 : Enum2.values())
                bijection.removePairing(e1, e2);
        System.out.println(bijection);
    }
}
