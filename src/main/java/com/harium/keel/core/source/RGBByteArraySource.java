package com.harium.keel.core.source;

import com.harium.keel.core.helper.ColorHelper;

/**
 * RGB Byte Array, without alpha channel
 */
public class RGBByteArraySource extends ImageSourceImpl {

    protected int w, h;
    protected byte[] array;

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
    public void setRGB(int x, int y, int rgb, int alpha) {
        // Ignore alpha
        setRGB(x, y, rgb);
    }

    @Override
    public void setRGB(int x, int y, int rgb) {
        byte r = (byte) (ColorHelper.getRed(rgb) - 128);
        byte g = (byte) (ColorHelper.getGreen(rgb) - 128);
        byte b = (byte) (ColorHelper.getBlue(rgb) - 128);

        int index = index(x, y);
        array[index] = r;
        array[index + 1] = g;
        array[index + 2] = b;
    }

    @Override
    public int getR(int x, int y) {
        return array[index(x, y)] + 128;
    }

    @Override
    public int getG(int x, int y) {
        return array[index(x, y) + 1] + 128;
    }

    @Override
    public int getB(int x, int y) {
        return array[index(x, y) + 2] + 128;
    }

    @Override
    public int getA(int x, int y) {
        return 0xff;
    }

    protected int index(int x, int y) {
        return x * 3 + w * 3 * y;
    }

}
