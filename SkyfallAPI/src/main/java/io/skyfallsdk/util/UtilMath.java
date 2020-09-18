package io.skyfallsdk.util;

public final class UtilMath {

    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static double clamp(double num, double min, double max) {
        return num < min ? min : (Math.min(num, max));
    }

    public static float clamp(float num, float min, float max) {
        return num < min ? min : (Math.min(num, max));
    }

    public static long clamp(long num, long min, long max) {
        return num < min ? min : (Math.min(num, max));
    }

    public static int clamp(int num, int min, int max) {
        return num < min ? min : (Math.min(num, max));
    }
}
