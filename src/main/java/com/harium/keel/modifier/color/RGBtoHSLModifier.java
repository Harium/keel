package com.harium.keel.modifier.color;

import com.harium.keel.core.Modifier;
import com.harium.keel.core.helper.ColorHelper;

public class RGBtoHSLModifier implements Modifier<Integer, float[]> {

    public static final int HUE_RANGE = 360;
    private static final float MAX = 0xff;

    @Override
    public float[] apply(Integer rgb) {
        int r = ColorHelper.getRed(rgb);
        int g = ColorHelper.getGreen(rgb);
        int b = ColorHelper.getBlue(rgb);
        return getHSLArray(r, g, b);
    }

    /**
     * Get HSL Color from RGB
     * https://www.programmingalgorithms.com/algorithm/rgb-to-hsl?lang=C%2B%2B
     *
     * @param r
     * @param g
     * @param b
     * @return hsl as an array of floats
     */
    public static float[] getHSLArray(int r, int g, int b) {
        float[] hsl = new float[3];
        float h, s, l;

        float rf = r / MAX;
        float gf = g / MAX;
        float bf = b / MAX;

        float minRGB = Math.min(rf, Math.min(gf, bf));
        float maxRGB = Math.max(rf, Math.max(gf, bf));

        float delta = maxRGB - minRGB;

        l = (maxRGB + minRGB) / 2;

        if (delta == 0) {
            h = 0;
            s = 0.0f;
        } else {
            s = (l <= 0.5) ? (delta / (maxRGB + minRGB)) : (delta / (2 - maxRGB - minRGB));

            float hue;

            if (rf == maxRGB) {
                hue = ((gf - bf) / 6) / delta;
            } else if (gf == maxRGB) {
                hue = (1.0f / 3) + ((bf - rf) / 6) / delta;
            } else {
                hue = (2.0f / 3) + ((rf - gf) / 6) / delta;
            }

            if (hue < 0) {
                hue += 1;
            } else if (hue > 1) {
                hue -= 1;
            }

            h = (int) (hue * HUE_RANGE);
        }

        hsl[0] = h;
        hsl[1] = s;
        hsl[2] = l;

        return hsl;
    }
}
