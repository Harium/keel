package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.modifier.color.RGBtoHSLModifier;

public class HSLColorStrategy extends ReferenceColorStrategy {

    private static final RGBtoHSLModifier MODIFIER = new RGBtoHSLModifier();

    protected float minH;
    protected float maxH;

    protected float minS;
    protected float maxS;

    protected float minL;
    protected float maxL;

    /**
     * Tolerance will be transformed to angle to match Hue
     *
     * @param tolerance - range from 0 to 1
     */
    public HSLColorStrategy(int color, float tolerance) {
        this(color, tolerance, tolerance, tolerance);
    }

    public HSLColorStrategy(Color color, float hTolerance, float sTolerance, float lTolerance) {
        this(color.getRGB(), hTolerance, sTolerance, lTolerance);
    }

    /**
     * @param hTolerance - range from 0 to 1
     * @param sTolerance - range from 0 to 1
     * @param lTolerance - range from 0 to 1
     */
    public HSLColorStrategy(int color, float hTolerance, float sTolerance, float lTolerance) {
        setColor(color);
        setTolerance(hTolerance, sTolerance, lTolerance);
    }

    public void setTolerance(float hTolerance, float sTolerance, float lTolerance) {
        float[] hsl = MODIFIER.apply(color);
        float h = hsl[0];
        float s = hsl[1];
        float l = hsl[2];

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

        this.minL = ColorHelper.clamp(l - lTolerance, 0, 1);
        this.maxL = ColorHelper.clamp(l + lTolerance, 0, 1);
    }

    public void setColor(Color color) {
        setColor(color.getRGB());
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean valid(int rgb, int j, int i) {
        float[] hsl = MODIFIER.apply(rgb);

        float h = hsl[0];
        float s = hsl[1];
        float l = hsl[2];

        return !(h > maxH) && !(h < minH) && !(s < minS) && !(s > maxS) && !(l < minL) && !(l > maxL);
    }

}
