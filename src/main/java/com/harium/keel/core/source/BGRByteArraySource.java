package com.harium.keel.core.source;

public class BGRByteArraySource extends RGBByteArraySource {

    public BGRByteArraySource(int w, int h, byte[] array) {
        super(w, h, array);
    }

    @Override
    public int getR(int x, int y) {
        return array[x * 3 + 2 + w * 3 * y] + 128;
    }

    @Override
    public int getG(int x, int y) {
        return array[x * 3 + 1 + w * 3 * y] + 128;
    }

    @Override
    public int getB(int x, int y) {
        return array[x * 3 + 0 + w * 3 * y] + 128;
    }
}
