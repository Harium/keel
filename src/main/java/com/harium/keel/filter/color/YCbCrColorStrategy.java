package com.harium.keel.filter.color;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.commons.math.EtylMath;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.strategy.PixelStrategy;

public class YCbCrColorStrategy implements ColorStrategy, PixelStrategy {

    private float weakFactor = 2;
    private int color;
    private int y;
    private int cb;
    private int cr;

    private int chromaTolerance;
    private int lumaTolerance;

    public YCbCrColorStrategy(int tolerance) {
        this.chromaTolerance = tolerance;
        this.lumaTolerance = tolerance;
    }

    public YCbCrColorStrategy(int chromaTolerance, int lumaTolerance) {
        this.chromaTolerance = chromaTolerance;
        this.lumaTolerance = lumaTolerance;
    }

    public YCbCrColorStrategy(Color color, int chromaTolerance, int lumaTolerance) {
        this(chromaTolerance, lumaTolerance);
        setColor(color);
    }

    public void setColor(Color color) {
        setColor(color.getRGB());
    }

    public void setColor(int color) {
        this.color = color;

        int r = ColorHelper.getRed(color);
        int g = ColorHelper.getGreen(color);
        int b = ColorHelper.getBlue(color);

        y = ColorHelper.getY(r, g, b);
        cb = ColorHelper.getCB(r, g, b);
        cr = ColorHelper.getCR(r, g, b);
    }

    @Override
    public boolean validateColor(int rgb, int j, int i) {
        int r = ColorHelper.getRed(rgb);
        int g = ColorHelper.getGreen(rgb);
        int b = ColorHelper.getBlue(rgb);

        int ry = ColorHelper.getY(r, g, b);
        int rcB = ColorHelper.getCB(r, g, b);
        int rcR = ColorHelper.getCR(r, g, b);

        int diffY = (int) EtylMath.diffMod(ry, y);
        int diffCB = (int) EtylMath.diffMod(rcB, cb);
        int diffCR = (int) EtylMath.diffMod(rcR, cr);

        return (diffY < lumaTolerance && diffCB < chromaTolerance && diffCR < chromaTolerance);
    }

    @Override
    public boolean strongValidateColor(int rgb, int j, int i, int reference) {
        int r = ColorHelper.getRed(rgb);
        int g = ColorHelper.getGreen(rgb);
        int b = ColorHelper.getBlue(rgb);

        int ry = ColorHelper.getY(r, g, b);
        int rcB = ColorHelper.getCB(r, g, b);
        int rcR = ColorHelper.getCR(r, g, b);

        int diffY = (int) EtylMath.diffMod(ry, y);
        int diffCB = (int) EtylMath.diffMod(rcB, cb);
        int diffCR = (int) EtylMath.diffMod(rcR, cr);

        return (diffY < lumaTolerance * weakFactor && diffCB < chromaTolerance * weakFactor && diffCR < chromaTolerance * weakFactor);
    }
}
