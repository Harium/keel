package com.harium.keel.core.mask;


import com.harium.keel.core.mask.strategy.DynamicMaskStrategy;
import com.harium.keel.core.mask.strategy.EmptyMaskStrategy;
import com.harium.keel.core.source.ImageSource;

public class DynamicArrayMask implements DynamicMask {

    private int w;
    private int h;
    protected int[][] mask;
    private DynamicMaskStrategy maskStrategy = new EmptyMaskStrategy();

    public DynamicArrayMask(int w, int h) {
        super();
        init(w, h);
    }

    public void init(int w, int h) {
        this.w = w;
        this.h = h;

        mask = new int[w][h];
        maskStrategy.reset(mask);
    }

    public int[][] getMask() {
        return mask;
    }

    @Override
    public boolean isUnknown(int px, int py) {
        int status = mask[px][py];

        return DynamicPixel.isUnknown(status);
    }

    @Override
    public boolean isValid(int px, int py) {
        int status = mask[px][py];

        return DynamicPixel.isValid(status);
    }

    @Override
    public boolean isTouched(int px, int py) {
        int status = mask[px][py];

        return DynamicPixel.isTouched(status);
    }

    @Override
    public void setValid(int px, int py) {

        int status = mask[px][py];

        mask[px][py] = DynamicPixel.setValid(status);
    }

    @Override
    public void setInvalid(int px, int py) {

        int status = mask[px][py];

        mask[px][py] = DynamicPixel.setInvalid(status);
    }

    @Override
    public void setTouched(int px, int py) {

        int status = mask[px][py];

        mask[px][py] = DynamicPixel.setTouched(status);
    }

    @Override
    public int getW() {
        return w;
    }

    @Override
    public int getH() {
        return h;
    }

    @Override
    public void update(ImageSource source) {
        maskStrategy.update(source, mask);
    }

}
