package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.commons.math.EtylMath;
import com.harium.keel.core.helper.ColorHelper;

public class HSVColorStrategy extends ReferenceColorStrategy {

    private float h;
    private float s;
    private float v;

    private float hTolerance;
    private float sTolerance;
    private float vTolerance;

    /**
     * Tolerance will be transformed to angle to match Hue
     *
     * @param tolerance - range from 0 to 1
     */
    public HSVColorStrategy(float tolerance) {
        // Turn hTolerance into angle
        this(tolerance * 360, tolerance, tolerance);
    }

    /**
     * @param hTolerance - range from 0 to 360
     * @param sTolerance - range from 0 to 1
     * @param vTolerance - range from 0 to 1
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
    public boolean valid(int rgb, int x, int y) {
        float[] hsv = ColorHelper.getHSVArray(rgb);

        int diffH = (int) EtylMath.diffMod(hsv[0], h);
        int diffS = (int) EtylMath.diffMod(hsv[1], s);
        int diffV = (int) EtylMath.diffMod(hsv[2], v);

        if (strength == 1) {
            return (diffH < hTolerance && diffS < sTolerance && diffV < vTolerance);
        } else {
            return (diffH < hTolerance * strength && diffS < sTolerance * strength && diffV < vTolerance * strength);
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

    public float getVTolerance() {
        return vTolerance;
    }

    public void setVTolerance(float vTolerance) {
        this.vTolerance = vTolerance;
    }
}
