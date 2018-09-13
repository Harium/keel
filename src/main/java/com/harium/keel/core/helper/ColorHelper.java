package com.harium.keel.core.helper;


public class ColorHelper {

    private static final int YUV_OFFSET = 0x80;
    public static final float MAX = 0xff;

    public static boolean isColor(int rgb, int color) {
        int r = getRed(rgb);
        int g = getGreen(rgb);
        int b = getBlue(rgb);

        if ((r == getRed(color)) && (g == getGreen(color)) && (b == getBlue(color))) {
            return true;
        }

        return false;
    }

    public static boolean isColor(int rgb, int color, int tolerance) {
        return isColor(rgb, color, tolerance, tolerance);
    }

    public static boolean isColor(int rgb, int color, int minTolerance, int maxTolerance) {
        return isColor(rgb, color, minTolerance, maxTolerance, minTolerance, maxTolerance, minTolerance, maxTolerance);
    }

    public static boolean isColor(int rgb, int color, int minToleranceRed, int maxToleranceRed, int minToleranceGreen, int maxToleranceGreen, int minToleranceBlue, int maxToleranceBlue) {
        int r = getRed(rgb);
        int g = getGreen(rgb);
        int b = getBlue(rgb);

        int cr = getRed(color);
        int cg = getGreen(color);
        int cb = getBlue(color);

        if ((r >= cr - minToleranceRed) && (r <= cr + maxToleranceRed) &&
                (g >= cg - minToleranceGreen) && (g <= cg + maxToleranceGreen) &&
                (b >= cb - minToleranceBlue) && (b <= cb + maxToleranceBlue)) {
            return true;
        }

        return false;
    }

    public static boolean isDarkerColor(int rgb, int color, int tolerance) {
        return isDarkerColor(rgb, color, tolerance, tolerance, tolerance);
    }

    public static boolean isDarkerColor(int rgb, int color, int minToleranceRed, int minToleranceGreen, int minToleranceBlue) {
        int r = getRed(rgb);
        int g = getGreen(rgb);
        int b = getBlue(rgb);

        int cr = getRed(color);
        int cg = getGreen(color);
        int cb = getBlue(color);

        if ((r <= cr) && (r >= cr - minToleranceRed) &&
                (g <= cg) && (g >= cg - minToleranceGreen) &&
                (b <= cb) && (b >= cb - minToleranceBlue)) {
            return true;
        }

        return false;
    }

    public static int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    public static int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    public static int getBlue(int rgb) {
        return rgb & 0xFF;
    }

    public static int getRGB(int red, int green, int blue) {
        int alpha = 0xff;
        int rgb = alpha;
        rgb = (rgb << 8) + red;
        rgb = (rgb << 8) + green;
        rgb = (rgb << 8) + blue;

        return rgb;
    }

    /**
     * YCbCr conversion from: https://en.wikipedia.org/wiki/YUV
     *
     * @param r - red channel
     * @param g - green channel
     * @param b - blue channel
     * @return Y channel
     */
    public static int getY(int r, int g, int b) {
        return (int) (0.299 * r + 0.587 * g + 0.114 * b);
    }

    /**
     * YCbCr conversion from: https://en.wikipedia.org/wiki/YUV
     *
     * @param r - red channel
     * @param g - green channel
     * @param b - blue channel
     * @return Cb channel
     */
    public static int getCB(int r, int g, int b) {
        return (int) (-0.169 * r - 0.331 * g + 0.499 * b + YUV_OFFSET);
    }

    /**
     * YCbCr conversion from: https://en.wikipedia.org/wiki/YUV
     *
     * @param r - red channel
     * @param g - green channel
     * @param b - blue channel
     * @return Cr channel
     */
    public static int getCR(int r, int g, int b) {
        return (int) (0.499 * r - 0.418 * g - 0.0813 * b + YUV_OFFSET);
    }

    public static int getY(int rgb) {
        int r = getRed(rgb);
        int g = getGreen(rgb);
        int b = getBlue(rgb);

        return getY(r, g, b);
    }

    public static int getCB(int rgb) {
        int r = getRed(rgb);
        int g = getGreen(rgb);
        int b = getBlue(rgb);

        return getCB(r, g, b);
    }

    public static int getCR(int rgb) {
        int r = getRed(rgb);
        int g = getGreen(rgb);
        int b = getBlue(rgb);

        return getCR(r, g, b);
    }

    /**
     * YCbCr conversion from: https://en.wikipedia.org/wiki/YUV
     *
     * @param y  - Y channel
     * @param cb - CB channel
     * @param cr - CR channel
     * @return rgb
     */
    public static int fromYCbCr(int y, int cb, int cr) {
        int cbc = cb - YUV_OFFSET;
        int crc = cr - YUV_OFFSET;
        int r = y + crc + (crc >> 2) + (crc >> 3) + (crc >> 5);
        int g = y - ((cbc >> 2) + (cbc >> 4) + (cbc >> 5)) - ((crc >> 1) + (crc >> 3) + (crc >> 4) + (crc >> 5));
        int b = y + cbc + (cbc >> 1) + (cbc >> 2) + (cbc >> 6);

        /* Using float operations
        int r = (int) (y + 1.402 * (cr - YUV_OFFSET));
        int g = (int) (y - 0.344 * (cb - YUV_OFFSET) - 0.714 * (cr - YUV_OFFSET));
        int b = (int) (y + 1.772 * (cb - YUV_OFFSET));
        r = clamp(r);
        g = clamp(g);
        b = clamp(b);
        */

        return getRGB(r, g, b);
    }

    public static int clamp(int a) {
        if (a < 0) {
            return 0;
        } else if (a > 0xff) {
            return 0xff;
        }
        return a;
    }

    /**
     * Code from: http://www.javascripter.net/faq/rgb2hsv.htm
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public static float[] getHSVArray(int r, int g, int b) {
        float[] hsv = new float[3];

        float rf = r / MAX;
        float gf = g / MAX;
        float bf = b / MAX;

        float minRGB = Math.min(rf, Math.min(gf, bf));
        float maxRGB = Math.max(rf, Math.max(gf, bf));

        float computedH = 0;
        float computedS = 0;
        float computedV = minRGB;

        // Black-gray-white
        if (minRGB != maxRGB) {
            // Colors other than black-gray-white:
            float d = (rf == minRGB) ? gf - bf : ((bf == minRGB) ? rf - gf : bf - rf);
            float h = (rf == minRGB) ? 3 : ((bf == minRGB) ? 1 : 5);
            computedH = 60 * (h - d / (maxRGB - minRGB));
            computedS = (maxRGB - minRGB) / maxRGB;
            computedV = maxRGB;
        }

        hsv[0] = computedH;
        hsv[1] = computedS;
        hsv[2] = computedV;
        return hsv;
    }

    public static float[] getHSVArray(int rgb) {
        int r = getRed(rgb);
        int g = getGreen(rgb);
        int b = getBlue(rgb);

        return getHSVArray(r, g, b);
    }

    public static float getH(int r, int g, int b) {
        float rf = r / MAX;
        float gf = g / MAX;
        float bf = b / MAX;

        float minRGB = Math.min(rf, Math.min(gf, bf));
        float maxRGB = Math.max(rf, Math.max(gf, bf));

        float computedH = 0;

        // Black-gray-white
        if (minRGB == maxRGB) {
            return 0;
        }

        // Colors other than black-gray-white:
        float d = (rf == minRGB) ? gf - bf : ((bf == minRGB) ? rf - gf : bf - rf);
        float h = (rf == minRGB) ? 3 : ((bf == minRGB) ? 1 : 5);
        computedH = 60 * (h - d / (maxRGB - minRGB));

        return computedH;
    }

    /**
     * Algorithm to transform from HSV to RGB
     * https://www.cs.rit.edu/~ncs/color/t_convert.html
     *
     * @param h
     * @param s
     * @param v
     * @return rgb color
     */
    public static int fromHSV(float h, float s, float v) {
        float r = v;
        float g = v;
        float b = v;

        int i;
        float f, p, q, t;

        if (s == 0) {
            // achromatic (grey)
            int ri = (int) (r * 0xff);
            int gi = (int) (g * 0xff);
            int bi = (int) (b * 0xff);

            return getRGB(ri, gi, bi);
        }

        h /= 60;            // sector 0 to 5
        i = (int) Math.floor(h);
        f = h - i;            // factorial part of h
        p = v * (1 - s);
        q = v * (1 - s * f);
        t = v * (1 - s * (1 - f));

        switch (i) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;
            case 1:
                r = q;
                g = v;
                b = p;
                break;
            case 2:
                r = p;
                g = v;
                b = t;
                break;
            case 3:
                r = p;
                g = q;
                b = v;
                break;
            case 4:
                r = t;
                g = p;
                b = v;
                break;
            // case 5:
            default:
                r = v;
                g = p;
                b = q;
                break;
        }

        int ri = (int) (r * 0xff);
        int gi = (int) (g * 0xff);
        int bi = (int) (b * 0xff);

        return getRGB(ri, gi, bi);
    }

    public static float[] getHSLArray(int rgb) {
        int r = getRed(rgb);
        int g = getGreen(rgb);
        int b = getBlue(rgb);

        return getHSLArray(r, g, b);
    }

    /**
     * Get HSL Color from RGB
     * https://www.programmingalgorithms.com/algorithm/rgb-to-hsl?lang=C%2B%2B
     *
     * @param r
     * @param g
     * @param b
     * @return
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

            h = (int)(hue * 360);
        }

        hsl[0] = h;
        hsl[1] = s;
        hsl[2] = l;

        return hsl;
    }

    /**
     * https://www.programmingalgorithms.com/algorithm/hsl-to-rgb?lang=C%2B%2B
     */
    public static int fromHSL(float h, float s, float l) {
        int r, g, b;

        if (s == 0) {
            r = g = b = (int) (l * MAX);
        } else {
            float v1, v2;
            float hue = h / 360f;

            v2 = (l < 0.5) ? (l * (1 + s)) : ((l + s) - (l * s));
            v1 = 2 * l - v2;

            r = (int) (MAX * HueToRGB(v1, v2, hue + (1.0f / 3)));
            g = (int) (MAX * HueToRGB(v1, v2, hue));
            b = (int) (MAX * HueToRGB(v1, v2, hue - (1.0f / 3)));
        }

        return getRGB(r, g, b);
    }

    public static float HueToRGB(float v1, float v2, float vH) {
        if (vH < 0) {
            vH += 1;
        }

        if (vH > 1) {
            vH -= 1;
        }

        if ((6 * vH) < 1) {
            return (v1 + (v2 - v1) * 6 * vH);
        }

        if ((2 * vH) < 1) {
            return v2;
        }

        if ((3 * vH) < 2) {
            return (v1 + (v2 - v1) * ((2.0f / 3) - vH) * 6);
        }

        return v1;
    }

}
