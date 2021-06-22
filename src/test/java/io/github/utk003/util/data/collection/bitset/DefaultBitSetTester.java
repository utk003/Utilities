package io.github.utk003.util.data.collection.bitset;

public class DefaultBitSetTester {
    private static final int size = 21;
    private enum Enum implements BitSet.Sizable {
        one {
            @Override
            public int sizeInBits() {
                return size;
            }
        }
    }
    public static void main(String[] args) {
        DefaultBitSet<Enum> bitSet = new DefaultBitSet<>(Enum.class);
        for (int i = 0; i < 10000000; i++) {
            int rand = (int) ((1 << size) * Math.random());
            bitSet.setValue(Enum.one, rand);
            String orig = toString(rand, size), ret = toString(bitSet.getValue(Enum.one, size), size);
            if (!orig.equals(ret))
                System.out.println("Failure: \"" + orig + "\" -> \"" + ret + "\"");
        }
    }

    private static String toString(byte[] arr) {
        StringBuilder builder = new StringBuilder();
        for (byte b : arr)
            builder.append(", ").append(toString(b, 8));
        return "[" + (builder.length() == 0 ? "" : builder.substring(2)) + "]";
    }
    private static String toString(long num, int numBits) {
        StringBuilder builder = new StringBuilder();
        for (int i = numBits - 1; i >= 0; i--)
            builder.append((num >>> i) & 1);
        return builder.toString();
    }
}
