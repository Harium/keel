package com.harium.keel.core.source;

import com.harium.keel.core.helper.ColorHelper;

public class OneBandSource extends ImageSourceImpl {

    protected int w, h;
    protected int[] array;

    public OneBandSource(int w, int h, int[] array) {
        grayscale = true;
        this.w = w;
        this.h = h;
        this.array = array;
    }

    public OneBandSource(ImageSource fastBitmap) {
        grayscale = true;
        OneBandSource copy = copy(fastBitmap);
        this.w = copy.getWidth();
        this.h = copy.getHeight();
        this.array = copy.array;
    }

    public OneBandSource(int width, int height) {
        this(width, height, new int[width * height]);
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
    public void setGray(int x, int y, int gray) {
        setRGB(x, y, gray);
    }

    @Override
    public void setRGB(int x, int y, int rgb) {
        array[index(x, y)] = rgb;
    }

    @Override
    public void setRGB(int x, int y, int rgb, int alpha) {
        // Ignore alpha
        setRGB(x, y, rgb);
    }

    private int index(int x, int y) {
        return y + w * x;
    }

    private static int index(int x, int y, int w) {
        return y + w * x;
    }

    public ImageSource toGrayScaleRGB() {
        MatrixSource source = new MatrixSource(w, h);
        int w = source.getWidth();
        int h = source.getHeight();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int value = array[index(j, i)];
                int rgb = ColorHelper.getRGB(value, value, value);
                source.setRGB(j, i, rgb);
            }
        }

        return source;
    }

    public static OneBandSource fromRed(ImageSource source) {
        int w = source.getWidth();
        int h = source.getHeight();
        int[] array = new int[w * h];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                array[index(j, i, w)] = source.getR(j, i);
            }
        }

        OneBandSource output = new OneBandSource(w, h, array);
        return output;
    }

    public static OneBandSource fromGreen(ImageSource source) {
        int w = source.getWidth();
        int h = source.getHeight();
        int[] array = new int[w * h];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                array[index(j, i, w)] = source.getG(j, i);
            }
        }

        OneBandSource output = new OneBandSource(w, h, array);
        return output;
    }

    public static OneBandSource fromBlue(ImageSource source) {
        int w = source.getWidth();
        int h = source.getHeight();
        int[] array = new int[w * h];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                array[index(j, i, w)] = source.getB(j, i);
            }
        }

        OneBandSource output = new OneBandSource(w, h, array);
        return output;
    }

    public static OneBandSource fromAlpha(ImageSource source) {
        int w = source.getWidth();
        int h = source.getHeight();
        int[] array = new int[w * h];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                array[index(j, i, w)] = source.getA(j, i);
            }
        }

        OneBandSource output = new OneBandSource(w, h, array);
        return output;
    }

    public static OneBandSource copy(ImageSource source) {
        return fromBlue(source);
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }
}
