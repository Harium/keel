package com.harium.keel.core.source;

public class IntArraySource extends ImageSourceImpl {

    protected int w, h;
    protected int[] array;

    public IntArraySource(int w, int h, int[] array) {
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
        return array[index(x, y)];
    }

    @Override
    public void setRGB(int x, int y, int rgb) {
        array[index(x, y)] = rgb;
    }

    private int index(int x, int y) {
        return x + w * y;
    }

    public void setArray(int[] array) {
        this.array = array;
    }
}
