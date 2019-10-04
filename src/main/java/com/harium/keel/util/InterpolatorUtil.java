package com.harium.keel.util;

public class InterpolatorUtil {
    public static float lerp(float a, float b, float f) {
        return (a * (1.0f - f)) + (b * f);
    }

    /**
     * Assuming f is between 0 and 1
     * @param a - min value of interval
     * @param b - max value of interval
     * @param f - value to scale
     * @return scaled value
     */
    public static float changeScale(float a, float b, float f) {
        float delta = b - a;
        float k = f - a;
        return k / delta;
    }
}
