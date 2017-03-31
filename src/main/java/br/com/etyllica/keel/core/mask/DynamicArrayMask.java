package br.com.etyllica.keel.core.mask;


import br.com.etyllica.keel.core.mask.strategy.DynamicMaskStrategy;
import br.com.etyllica.keel.core.mask.strategy.EmptyMaskStrategy;

public class DynamicArrayMask implements DynamicMask {

    private int w;
    private int h;
    protected int[][] mask;
    private DynamicMaskStrategy maskStrategy = new EmptyMaskStrategy();

    public DynamicArrayMask(int w, int h) {
        super();

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

}
