package com.harium.keel.filter.selection.smooth;

import com.harium.keel.core.helper.ColorHelper;

public class ExponentialSmoothSelection implements SmoothSelection {

    private int r;
    private int g;
    private int b;

    protected float alpha = 0.5f;

    public ExponentialSmoothSelection(float alpha) {
        this.alpha = alpha;
    }

    public ExponentialSmoothSelection(float alpha, int initialValue) {
        this.alpha = alpha;
        setInitialColor(initialValue);
    }

    @Override
    public int smooth(int rgb) {
        int nr = ColorHelper.getRed(rgb);
        int ng = ColorHelper.getGreen(rgb);
        int nb = ColorHelper.getBlue(rgb);

        // It has to calculate separate channels otherwise it will mess with color
        r = (int) (alpha * nr + (1 - alpha) * r);
        g = (int) (alpha * ng + (1 - alpha) * g);
        b = (int) (alpha * nb + (1 - alpha) * b);

        //r = ColorHelper.clamp((int) (alpha * nr + (1 - alpha) * r));
        //g = ColorHelper.clamp((int) (alpha * ng + (1 - alpha) * g));
        //b = ColorHelper.clamp((int) (alpha * nb + (1 - alpha) * b));

        return ColorHelper.getRGB(r, g, b);
    }

    public void setInitialColor(int rgb) {
        r = ColorHelper.getRed(rgb);
        g = ColorHelper.getGreen(rgb);
        b = ColorHelper.getBlue(rgb);
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
