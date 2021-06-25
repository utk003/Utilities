package io.github.utk003.util.math;

@SuppressWarnings("ManualMinMaxCalculation")
public abstract class FastMath {
    private FastMath() {
    }

    public static int floor(float f) {
        int i = (int) f;
        return i > f ? i - 1 : i;
    }
    public static long floor(double d) {
        long l = (long) d;
        return l > d ? l - 1 : l;
    }

    public static int ciel(float f) {
        int i = (int) f;
        return i < f ? i + 1 : i;
    }
    public static long ciel(double d) {
        long l = (long) d;
        return l < d ? l + 1 : l;
    }

    public static byte clamp(byte val, byte low, byte high) {
        return val <= low ? low : val >= high ? high : val;
    }
    public static short clamp(short val, short low, short high) {
        return val <= low ? low : val >= high ? high : val;
    }
    public static int clamp(int val, int low, int high) {
        return val <= low ? low : val >= high ? high : val;
    }
    public static long clamp(long val, long low, long high) {
        return val <= low ? low : val >= high ? high : val;
    }
    public static float clamp(float val, float low, float high) {
        return val <= low ? low : val >= high ? high : val;
    }
    public static double clamp(double val, double low, double high) {
        return val <= low ? low : val >= high ? high : val;
    }

    public static int shift(int val, int high) {
        return (val % high + high) % high;
    }
    public static long shift(long val, long high) {
        return (val % high + high) % high;
    }
    public static float shift(float val, float high) {
        return (val % high + high) % high;
    }
    public static double shift(double val, double high) {
        return (val % high + high) % high;
    }

    public static int shift(int val, int low, int high) {
        return shift(val - low, high - low) + low;
    }
    public static long shift(long val, long low, long high) {
        return shift(val - low, high - low) + low;
    }
    public static float shift(float val, float low, float high) {
        return shift(val - low, high - low) + low;
    }
    public static double shift(double val, double low, double high) {
        return shift(val - low, high - low) + low;
    }
}
