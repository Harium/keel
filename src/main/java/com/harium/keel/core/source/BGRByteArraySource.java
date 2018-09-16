package com.harium.keel.core.source;

import com.harium.keel.core.helper.ColorHelper;

/**
 * BGR Byte Array, without alpha channel
 */
public class BGRByteArraySource extends RGBByteArraySource {

    public BGRByteArraySource(int w, int h, byte[] array) {
        super(w, h, array);
    }

    @Override
    public int getR(int x, int y) {
        return array[index(x, y) + 2] + 128;
    }

    @Override
    public int getG(int x, int y) {
        return array[index(x, y) + 1] + 128;
    }

    @Override
    public int getB(int x, int y) {
        return array[index(x, y)] + 128;
    }

    @Override
    public void setRGB(int x, int y, int rgb) {
        byte r = (byte) (ColorHelper.getRed(rgb) - 128);
        byte g = (byte) (ColorHelper.getGreen(rgb) - 128);
        byte b = (byte) (ColorHelper.getBlue(rgb) - 128);

        int index = index(x, y);
        array[index + 2] = r;
        array[index + 1] = g;
        array[index] = b;
    }
}
