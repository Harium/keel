package com.harium.keel.core.source;

public class IntArraySource extends ImageSourceImpl {

    int w, h;
    int[] array;

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
        return array[x + w * y];
    }
}
