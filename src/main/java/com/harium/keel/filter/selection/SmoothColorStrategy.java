package com.harium.keel.filter.selection;

import com.harium.keel.filter.smooth.ExponentialSmooth;
import com.harium.keel.filter.smooth.SmoothFilter;

public class SmoothColorStrategy implements ColorStrategy {

    private ColorStrategy colorStrategy;
    private SmoothFilter smoothFilter = new ExponentialSmooth(0.5f);

    public SmoothColorStrategy(ColorStrategy colorStrategy) {
        super();
        this.colorStrategy = colorStrategy;
    }

    @Override
    public boolean valid(int rgb, int x, int y) {
        boolean valid = colorStrategy.valid(rgb, x, y);
        if (valid) {
            double color = smoothFilter.smooth(rgb);
            colorStrategy.setColor((int) color);
        }
        return valid;
    }

    @Override
    public void setColor(int color) {
        colorStrategy.setColor(color);
        smoothFilter.setInitialValue(color);
    }

    @Override
    public int getColor() {
        return colorStrategy.getColor();
    }

    public SmoothFilter getSmoothFilter() {
        return smoothFilter;
    }

    public void setSmoothFilter(SmoothFilter smoothFilter) {
        this.smoothFilter = smoothFilter;
    }
}
