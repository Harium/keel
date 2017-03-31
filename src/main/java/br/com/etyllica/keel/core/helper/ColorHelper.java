package br.com.etyllica.keel.core.helper;

public class ColorHelper {

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

        int rgb = red;
        rgb = (rgb << 8) + green;
        rgb = (rgb << 8) + blue;

        return rgb;
    }

    public static int getY(int r, int g, int b) {
        return (int) (0.299 * r + 0.587 * g + 0.114 * b);
    }

    public static int getCB(int r, int g, int b) {
        return (int) (-0.16874 * r - 0.33126 * g + 0.50000 * b);
    }

    public static int getCR(int r, int g, int b) {
        return (int) (0.50000 * r - 0.41869 * g - 0.08131 * b);
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

        float rf = r / 255;
        float gf = g / 255;
        float bf = b / 255;

        float minRGB = Math.min(rf, Math.min(gf, bf));
        float maxRGB = Math.max(rf, Math.max(gf, bf));

        float computedH = 0;
        float computedS = 0;
        float computedV = 0;

        // Black-gray-white
        if (minRGB == maxRGB) {
            computedV = minRGB;
            hsv[2] = computedV;
        }

        // Colors other than black-gray-white:
        float d = (rf == minRGB) ? gf - bf : ((bf == minRGB) ? rf - gf : bf - rf);
        float h = (rf == minRGB) ? 3 : ((bf == minRGB) ? 1 : 5);
        computedH = 60 * (h - d / (maxRGB - minRGB));
        computedS = (maxRGB - minRGB) / maxRGB;
        computedV = maxRGB;

        hsv[0] = computedH;
        hsv[1] = computedS;
        hsv[2] = computedV;
        return hsv;
    }

    public static float getH(int r, int g, int b) {
        float rf = r / 255;
        float gf = g / 255;
        float bf = b / 255;

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

}
