package com.harium.keel.modifier.color;

import com.harium.keel.core.Modifier;
import com.harium.keel.core.helper.ColorHelper;

/**
 * Turn RGB int into HSL float
 * Range of 0 to 1
 */
public class RGBtoHSLModifier implements Modifier<Integer, float[]> {

    public static final float MAX = 0.003921569f;

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
     * @return hsl as an array of floats ranging 0 to 1
     */
    public static float[] getHSLArray(int r, int g, int b) {
        return fastHSL(r, g, b);
    }

    private static float[] fastHSL(int r, int g, int b) {
        float[] hsl = new float[3];
        float h, s, l, min, max;

        float fr = r * RGBtoHSLModifier.MAX;
        float fg = g * RGBtoHSLModifier.MAX;
        float fb = b * RGBtoHSLModifier.MAX;
        float d;

        if (fr > fg) {
            if (fr > fb) {
                max = fr;
                // R
                min = Math.min(fg, fb);
                d = max - min;
                float den = 6 * d;
                h = ((fg - fb) / den);
            } else {
                max = fb;
                min = fg;
                d = max - min;
                float den = 6 * d;
                h = (2.0f / 3) + ((fr - fg) / den);
            }

        } else {//g is higher
            if (fg > fb) {
                max = fg;
                min = Math.min(fr, fb);
                d = max - min;
                float den = 6 * d;
                h = (1.0f / 3) + ((fb - fr) / den);
            } else {
                max = fb;
                min = fr;
                if (max == min) {
                    // achromatic
                    hsl[0] = 0;
                    hsl[1] = 0;
                    hsl[2] = max;
                    return hsl;
                }
                d = max - min;
                float den = 6 * d;
                h = (2.0f / 3) + ((fr - fg) / den);
            }
        }

        if (h < 0) {
            h += 1;
        } else if (h > 1) {
            h -= 1;
        }

        l = (max + min);
        s = (l <= 1f) ? (d / l) : (d / (2 - l));
        l *= 0.5f;

        hsl[0] = h;
        hsl[1] = s;
        hsl[2] = l;

        return hsl;
    }

}
