package com.harium.keel.core.source;

import com.harium.keel.core.helper.ColorHelper;

import static com.harium.keel.core.helper.ColorHelper.clamp;

/**
 * YUV 420 Byte Array, without alpha channel
 * Based on Android format (YCbCr NV21)
 */
public class YUV420Source extends ByteArraySource {

    public YUV420Source(int w, int h, byte[] array) {
        super(w, h, array);
    }

    /**
     * Code from: https://stackoverflow.com/a/8394202/7030976
     *
     * @param x
     * @param y
     * @return rgb color
     */
    @Override
    public int getRGB(int x, int y) {
        int frameSize = w * h;
        int index = y * w + x;
        int uIndex = frameSize + (y >> 1) * w + (x & ~1);

        int yi = (0xff & ((int) array[index]));
        int v = (0xff & ((int) array[uIndex]));
        int u = (0xff & ((int) array[uIndex + 1]));
        yi = yi < 16 ? 16 : yi;

        int r = (int) (1.164f * (yi - 16) + 1.596f * (v - 128));
        int g = (int) (1.164f * (yi - 16) - 0.813f * (v - 128) - 0.391f * (u - 128));
        int b = (int) (1.164f * (yi - 16) + 2.018f * (u - 128));

        r = clamp(r);
        g = clamp(g);
        b = clamp(b);

        return ColorHelper.getRGB(r, g, b);
    }

    @Override
    public int getGray(int x, int y) {
        int index = y * w + x;
        int gray = (0xff & ((int) array[index])) - 16;
        return clamp(gray);
    }

    /**
     * Code from: https://stackoverflow.com/a/13055615/7030976
     *
     * @param x
     * @param y
     * @param rgb
     */
    @Override
    public void setRGB(int x, int y, int rgb) {
        int index = y * w + x;
        int frameSize = w * h;

        int r = ColorHelper.getRed(rgb);
        int g = ColorHelper.getGreen(rgb);
        int b = ColorHelper.getBlue(rgb);

        int yi = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
        int u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
        int v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;

        array[index] = (byte) clamp(yi);
        int uIndex = frameSize + (y >> 1) * w + (x & ~1);

        array[uIndex] = (byte) clamp(v);
        array[uIndex + 1] = (byte) clamp(u);
    }
}
