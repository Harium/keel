package com.harium.keel.core.source;

import com.harium.keel.core.helper.ColorHelper;

public abstract class ImageSourceImpl implements ImageSource {

    public int getA(int x, int y) {
        return ColorHelper.getAlpha(getRGB(x, y));
    }

    public int getR(int x, int y) {
        return ColorHelper.getRed(getRGB(x, y));
    }

    public int getG(int x, int y) {
        return ColorHelper.getGreen(getRGB(x, y));
    }

    public int getB(int x, int y) {
        return ColorHelper.getBlue(getRGB(x, y));
    }

}
