package com.harium.keel.core.source;

public abstract class ByteArraySource extends ImageSourceImpl {

    protected int w, h;
    protected byte[] array;

    public ByteArraySource(int w, int h, byte[] array) {
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

}
