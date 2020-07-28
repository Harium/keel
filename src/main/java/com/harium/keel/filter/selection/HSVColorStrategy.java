package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.commons.math.EtylMath;
import com.harium.keel.core.helper.ColorHelper;

public class HSVColorStrategy extends ReferenceColorStrategy {

    protected float minH;
    protected float maxH;

    protected float minS;
    protected float maxS;

    protected float minV;
    protected float maxV;

    public HSVColorStrategy(int color, float tolerance) {
        this(color, tolerance, tolerance, tolerance);
    }

    public HSVColorStrategy(Color color, float hTolerance, float sTolerance, float lTolerance) {
        this(color.getRGB(), hTolerance, sTolerance, lTolerance);
    }

    /**
     * @param hTolerance - range from 0 to 1
     * @param sTolerance - range from 0 to 1
     * @param vTolerance - range from 0 to 1
     */
    public HSVColorStrategy(int color, float hTolerance, float sTolerance, float vTolerance) {
        setColor(color);
        setTolerance(hTolerance, sTolerance, vTolerance);
    }

    public void setTolerance(float hTolerance, float sTolerance, float vTolerance) {
        float[] hsv = calculateHSV(color);
        float h = hsv[0];
        float s = hsv[1];
        float v = hsv[2];

        this.minH = h - hTolerance;
        if (minH < 0) {
            minH += 1;
        }
        this.maxH = h + hTolerance;
        if (maxH > 1) {
            maxH -= 1;
        }

        this.minS = ColorHelper.clamp(s - sTolerance, 0, 1);
        this.maxS = ColorHelper.clamp(s + sTolerance, 0, 1);

        this.minV = ColorHelper.clamp(v - vTolerance, 0, 1);
        this.maxV = ColorHelper.clamp(v + vTolerance, 0, 1);
    }

    public void setColor(Color color) {
        setColor(color.getRGB());
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean valid(int rgb, int j, int i) {
        float[] hsv = calculateHSV(rgb);

        float h = hsv[0];
        float s = hsv[1];
        float v = hsv[2];

        return !(h < minH) && !(h > maxH) && !(s < minS) && !(s > maxS) && !(v < minV) && !(v > maxV);
    }

    private float[] calculateHSV(int rgb) {
        return ColorHelper.getHSVArray(rgb);
    }

    public float getMinH() {
        return minH;
    }

    public void setMinH(float minH) {
        this.minH = minH;
    }

    public float getMaxH() {
        return maxH;
    }

    public void setMaxH(float maxH) {
        this.maxH = maxH;
    }

    public float getMinS() {
        return minS;
    }

    public void setMinS(float minS) {
        this.minS = minS;
    }

    public float getMaxS() {
        return maxS;
    }

    public void setMaxS(float maxS) {
        this.maxS = maxS;
    }

    public float getMinV() {
        return minV;
    }

    public void setMinV(float minV) {
        this.minV = minV;
    }

    public float getMaxV() {
        return maxV;
    }

    public void setMaxV(float maxV) {
        this.maxV = maxV;
    }
}
