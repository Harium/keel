package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.commons.math.EtylMath;
import com.harium.keel.core.helper.ColorHelper;

public class HSLColorStrategy extends ReferenceColorStrategy {

    private float h;
    private float s;
    private float l;

    private float hTolerance;
    private float sTolerance;
    private float lTolerance;

    /**
     * Tolerance will be transformed to angle to match Hue
     *
     * @param tolerance - range from 0 to 1
     */
    public HSLColorStrategy(float tolerance) {
        // Turn hTolerance into angle
        this(tolerance * 360, tolerance, tolerance);
    }

    /**
     * @param hTolerance - range from 0 to 360
     * @param sTolerance - range from 0 to 1
     * @param lTolerance - range from 0 to 1
     */
    public HSLColorStrategy(float hTolerance, float sTolerance, float lTolerance) {
        this.hTolerance = hTolerance;
        this.sTolerance = sTolerance;
        this.lTolerance = lTolerance;
    }

    public HSLColorStrategy(Color color, float hTolerance, float sTolerance, float lTolerance) {
        this(hTolerance, sTolerance, lTolerance);
        setColor(color);
    }

    public void setColor(Color color) {
        setColor(color.getRGB());
    }

    public void setColor(int color) {
        this.color = color;

        float[] hsl = ColorHelper.getHSLArray(color);
        h = hsl[0];
        s = hsl[1];
        l = hsl[2];
    }

    @Override
    public boolean valid(int rgb, int j, int i) {
        float[] hsl = ColorHelper.getHSLArray(rgb);

        int diffH = (int) EtylMath.diffMod(hsl[0], h);
        int diffS = (int) EtylMath.diffMod(hsl[1], s);
        int diffL = (int) EtylMath.diffMod(hsl[2], l);

        if (strength == 1) {
            return (diffH < hTolerance && diffS < sTolerance && diffL < lTolerance);
        } else {
            return (diffH < hTolerance * strength && diffS < sTolerance * strength && diffL < lTolerance * strength);
        }
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

    public float getLTolerance() {
        return lTolerance;
    }

    public void setLTolerance(float lTolerance) {
        this.lTolerance = lTolerance;
    }
}
