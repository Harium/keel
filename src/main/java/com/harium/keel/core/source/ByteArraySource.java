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

    public void setWidth(int w) {
        this.w = w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    public void setHeight(int h) {
        this.h = h;
    }

    public void setArray(byte[] array) {
        this.array = array;
    }
}
