package com.harium.keel.modifier.color;

import com.harium.keel.core.Modifier;
import com.harium.keel.core.helper.ColorHelper;

import static com.harium.keel.modifier.color.RGBtoHSLModifier.ONE_THIRD;
import static com.harium.keel.modifier.color.RGBtoHSLModifier.TWO_THIRDS;

/**
 * Turn HSL float array into rgb integer
 */
public class HSLtoRGBModifier implements Modifier<float[], Integer> {

    private static final int MAX = 255;

    @Override
    public Integer apply(float[] hsl) {
        return hslToRgb(hsl);
    }

    public static int hslToRgb(float[] hsl) {
        float h = hsl[0];
        float s = hsl[1];
        float l = hsl[2];

        int r, g, b;

        if (s == 0) {
            r = g = b = (int) (l * MAX);
        } else {
            float v1, v2;

            v2 = (l < 0.5) ? (l * (1 + s)) : ((l + s) - (l * s));
            v1 = 2 * l - v2;

            r = (int) (MAX * HueToRGB(v1, v2, h + (ONE_THIRD)));
            g = (int) (MAX * HueToRGB(v1, v2, h));
            b = (int) (MAX * HueToRGB(v1, v2, h - (ONE_THIRD)));
        }

        return ColorHelper.getRGB(r, g, b);
    }

    public static float HueToRGB(float v1, float v2, float vH) {
        if (vH < 0) {
            vH += 1;
        } else if (vH > 1) {
            vH -= 1;
        }

        if ((6 * vH) < 1) {
            return (v1 + (v2 - v1) * 6 * vH);
        }

        if ((2 * vH) < 1) {
            return v2;
        }

        if ((3 * vH) < 2) {
            return (v1 + (v2 - v1) * ((TWO_THIRDS) - vH) * 6);
        }

        return v1;
    }
}
