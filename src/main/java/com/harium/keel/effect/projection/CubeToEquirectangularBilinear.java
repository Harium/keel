package com.harium.keel.effect.projection;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

public class CubeToEquirectangularBilinear extends CubeToEquirectangular {

    @Override
    protected int getPixel(ImageSource input, int xPixel, int yPixel) {
        int imax = input.getHeight() - 1;
        int jmax = input.getWidth() - 1;

        int oi1, oj1, oi2, oj2;

        oi1 = yPixel;
        oj1 = xPixel;

        oi2 = (yPixel == imax) ? yPixel : yPixel + 1;
        oj2 = (xPixel == jmax) ? xPixel : xPixel + 1;

        int rgb1 = input.getRGB(oj1, oi1);
        int rgb2 = input.getRGB(oj2, oi1);
        int rgb3 = input.getRGB(oj1, oi2);
        int rgb4 = input.getRGB(oj2, oi2);

        int p1 = ColorHelper.getRed(rgb1);
        int p2 = ColorHelper.getRed(rgb2);
        int p3 = ColorHelper.getRed(rgb3);
        int p4 = ColorHelper.getRed(rgb4);

        int r = (p1 + p2 + p3 + p4) / 4;

        // get four points (green)
        p1 = ColorHelper.getGreen(rgb1);
        p2 = ColorHelper.getGreen(rgb2);
        p3 = ColorHelper.getGreen(rgb3);
        p4 = ColorHelper.getGreen(rgb4);

        int g = (p1 + p2 + p3 + p4) / 4;

        // get four points (blue)
        p1 = ColorHelper.getBlue(rgb1);
        p2 = ColorHelper.getBlue(rgb2);
        p3 = ColorHelper.getBlue(rgb3);
        p4 = ColorHelper.getBlue(rgb4);

        int b = (p1 + p2 + p3 + p4) / 4;

        int rgb = ColorHelper.getRGB(r, g, b);
        return rgb;
    }
}
