package com.harium.keel.filter.color;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.commons.math.EtylMath;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.strategy.SelectionStrategy;

public class HSVColorStrategy implements ColorStrategy, SelectionStrategy {

    private float weakFactor = 2;
    private int color;
    private float h;
    private float s;
    private float v;

    private float hTolerance;
    private float sTolerance;
    private float vTolerance;

    /**
     * Tolerance will be transformed to angle to match Hue
     * @param tolerance - range from 0 to 1
     */
    public HSVColorStrategy(float tolerance) {
        // Turn hTolerance into angle
        this(tolerance * 360, tolerance, tolerance);
    }

    /**
     *
     * @param hTolerance - range from 0 to 360
     * @param sTolerance - range from 0 to 1
     * @param lTolerance - range from 0 to 1
     */
    public HSVColorStrategy(float hTolerance, float sTolerance, float vTolerance) {
        this.hTolerance = hTolerance;
        this.sTolerance = sTolerance;
        this.vTolerance = vTolerance;
    }

    public HSVColorStrategy(Color color, float hTolerance, float sTolerance, float vTolerance) {
        this(hTolerance, sTolerance, vTolerance);
        setColor(color);
    }

    public void setColor(Color color) {
        setColor(color.getRGB());
    }

    public void setColor(int color) {
        this.color = color;

        float[] hsv = ColorHelper.getHSVArray(color);
        h = hsv[0];
        s = hsv[1];
        v = hsv[2];
    }

    @Override
    public boolean validateColor(int rgb, int j, int i) {
        float[] hsv = ColorHelper.getHSVArray(rgb);

        int diffH = (int) EtylMath.diffMod(hsv[0], h);
        int diffS = (int) EtylMath.diffMod(hsv[1], s);
        int diffV = (int) EtylMath.diffMod(hsv[2], v);

        return (diffH < hTolerance && diffS < sTolerance && diffV < vTolerance);
    }

    @Override
    public boolean softValidateColor(int rgb, int j, int i, int reference) {
        float[] hsv = ColorHelper.getHSLArray(rgb);

        int diffH = (int) EtylMath.diffMod(hsv[0], h);
        int diffS = (int) EtylMath.diffMod(hsv[1], s);
        int diffV = (int) EtylMath.diffMod(hsv[2], v);

        return (diffH < hTolerance * weakFactor && diffS < sTolerance * weakFactor && diffV < vTolerance * weakFactor);
    }

    public float getHTolerance() {
        return hTolerance;
    }

    public void setHTolerance(float hTolerance) {
        this.hTolerance = hTolerance;
    }

    public float getSTolerance() {
        return sTolerance;
    }

    public void setSTolerance(float sTolerance) {
        this.sTolerance = sTolerance;
    }

    public float getVTolerance() {
        return vTolerance;
    }

    public void setVTolerance(float vTolerance) {
        this.vTolerance = vTolerance;
    }
}
