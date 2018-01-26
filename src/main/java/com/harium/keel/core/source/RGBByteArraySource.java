package com.harium.keel.core.source;

import com.harium.keel.core.helper.ColorHelper;

public class RGBByteArraySource implements ImageSource {

    int w, h;
    byte[] array;

    public RGBByteArraySource(int w, int h, byte[] array) {
        this.w = w;
        this.h = h;
        this.array = array;
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    @Override
    public int getRGB(int x, int y) {
        int r = getR(x, y);
        int g = getG(x, y);
        int b = getB(x, y);

        int rgb = ColorHelper.getRGB(r, g, b);
        return rgb;
    }

    @Override
    public int getR(int x, int y) {
        return array[x * 3 + 0 + w * 3 * y] + 128;
    }

    @Override
    public int getG(int x, int y) {
        return array[x * 3 + 1 + w * 3 * y] + 128;
    }

    @Override
    public int getB(int x, int y) {
        return array[x * 3 + 2 + w * 3 * y] + 128;
    }
}
