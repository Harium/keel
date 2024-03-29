package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.geometry.math.EtylMath;
import com.harium.keel.core.helper.ColorHelper;

public class YCbCrColorStrategy extends ReferenceColorStrategy {

    private int y;
    private int cb;
    private int cr;

    private int chromaBTolerance;
    private int chromaRTolerance;
    private int lumaTolerance;

    public YCbCrColorStrategy(int tolerance) {
        this(tolerance, tolerance, tolerance);
    }

    public YCbCrColorStrategy(int lumaTolerance, int chromaTolerance) {
        this(chromaTolerance, chromaTolerance, lumaTolerance);
    }

    public YCbCrColorStrategy(int lumaTolerance, int chromaBTolerance, int chromaRTolerance) {
        this.chromaBTolerance = chromaBTolerance;
        this.chromaRTolerance = chromaRTolerance;
        this.lumaTolerance = lumaTolerance;
    }

    public YCbCrColorStrategy(Color color, int lumaTolerance, int chromaTolerance) {
        this(color, chromaTolerance, chromaTolerance, lumaTolerance);
    }

    public YCbCrColorStrategy(Color color, int lumaTolerance, int chromaBTolerance, int chromaRTolerance) {
        this(lumaTolerance, chromaBTolerance, chromaRTolerance);
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
    public boolean valid(int rgb, int j, int i) {
        int r = ColorHelper.getRed(rgb);
        int g = ColorHelper.getGreen(rgb);
        int b = ColorHelper.getBlue(rgb);

        int ry = ColorHelper.getY(r, g, b);
        int rcB = ColorHelper.getCB(r, g, b);
        int rcR = ColorHelper.getCR(r, g, b);

        int diffY = EtylMath.diffMod(ry, y);
        int diffCB = EtylMath.diffMod(rcB, cb);
        int diffCR = EtylMath.diffMod(rcR, cr);

        return (diffY < lumaTolerance && diffCB < chromaBTolerance && diffCR < chromaRTolerance);
    }

    public int getChromaBTolerance() {
        return chromaBTolerance;
    }

    public void setChromaBTolerance(int chromaBTolerance) {
        this.chromaBTolerance = chromaBTolerance;
    }

    public int getChromaRTolerance() {
        return chromaRTolerance;
    }

    public void setChromaRTolerance(int chromaRTolerance) {
        this.chromaRTolerance = chromaRTolerance;
    }

    public int getLumaTolerance() {
        return lumaTolerance;
    }

    public void setLumaTolerance(int lumaTolerance) {
        this.lumaTolerance = lumaTolerance;
    }

    public int getColor() {
        return color;
    }
}
