package com.harium.keel.core.source;

import com.harium.keel.core.helper.ColorHelper;

/**
 * Binary Image Source made from 0, and 1's
 */
public class BinarySource extends ImageSourceImpl {

    protected int w, h;
    protected byte[] array;

    public BinarySource(int w, int h, byte[] array) {
        super();
        grayscale = true;
        this.w = w;
        this.h = h;
        this.array = array;
    }

    public BinarySource(ImageSource fastBitmap) {
        super();
        grayscale = true;
        BinarySource copy = copy(fastBitmap);
        this.w = copy.getWidth();
        this.h = copy.getHeight();
        this.array = copy.array;
    }

    public BinarySource(int width, int height) {
        this(width, height, new byte[width * height]);
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
        return array[index(x, y)];
    }

    @Override
    public int getA(int x, int y) {
        return 255;
    }

    @Override
    public int getR(int x, int y) {
        return getRGB(x, y);
    }

    @Override
    public int getG(int x, int y) {
        return getRGB(x, y);
    }

    @Override
    public int getB(int x, int y) {
        return getRGB(x, y);
    }

    @Override
    public void setRGB(int x, int y, int rgb) {
        array[index(x, y)] = (byte) rgb;
    }

    @Override
    public void setRGB(int x, int y, int rgb, int alpha) {
        // Ignore alpha
        setRGB(x, y, rgb);
    }

    @Override
    public int getGray(int x, int y) {
        return getB(x, y) == 0 ? 0 : 255;
    }

    @Override
    public void setGray(int x, int y, int gray) {
        setRGB(x, y, gray);
    }

    private int index(int x, int y) {
        return x + w * y;
    }

    private static int index(int x, int y, int w) {
        return x + w * y;
    }

    public ImageSource toGrayScaleRGB() {
        MatrixSource source = new MatrixSource(w, h);
        int w = source.getWidth();
        int h = source.getHeight();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int value = this.array[index(j, i)] * 255;
                int rgb = ColorHelper.getRGB(value, value, value);
                source.setRGB(j, i, rgb);
            }
        }

        return source;
    }

    public static BinarySource fromRed(ImageSource source) {
        int w = source.getWidth();
        int h = source.getHeight();
        byte[] array = new byte[w * h];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < h; j++) {
                byte b = toBinary(source.getR(j, i));
                array[index(j, i, w)] = b;
            }
        }

        BinarySource output = new BinarySource(w, h, array);
        return output;
    }

    public static byte toBinary(int value) {
        return (byte) (value / 255);
    }

    public static BinarySource fromGreen(ImageSource source) {
        int w = source.getWidth();
        int h = source.getHeight();
        byte[] array = new byte[w * h];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                array[index(j, i, w)] = toBinary(source.getG(j, i));
            }
        }

        BinarySource output = new BinarySource(w, h, array);
        return output;
    }

    public static BinarySource fromBlue(ImageSource source) {
        int w = source.getWidth();
        int h = source.getHeight();
        byte[] array = new byte[w * h];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < h; j++) {
                array[index(j, i, w)] = toBinary(source.getB(j, i));
            }
        }

        BinarySource output = new BinarySource(w, h, array);
        return output;
    }

    public static BinarySource fromAlpha(ImageSource source) {
        int w = source.getWidth();
        int h = source.getHeight();
        byte[] array = new byte[w * h];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < h; j++) {
                array[index(j, i, w)] = toBinary(source.getA(j, i));
            }
        }

        BinarySource output = new BinarySource(w, h, array);
        return output;
    }

    public static BinarySource copy(ImageSource source) {
        return fromBlue(source);
    }

    public byte[] getArray() {
        return array;
    }

    public void setArray(byte[] array) {
        this.array = array;
    }
}
