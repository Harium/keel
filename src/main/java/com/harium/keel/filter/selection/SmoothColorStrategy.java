package com.harium.keel.filter.selection;

import com.harium.keel.filter.selection.smooth.SmoothSelection;
import com.harium.keel.filter.selection.smooth.ExponentialSmoothSelection;

public class SmoothColorStrategy implements ColorStrategy {

    private int color;
    private ColorStrategy colorStrategy;
    private SmoothSelection smoothFilter = new ExponentialSmoothSelection(0.5f);

    public SmoothColorStrategy(ColorStrategy colorStrategy) {
        super();
        this.colorStrategy = colorStrategy;
    }

    @Override
    public boolean valid(int rgb, int x, int y) {
        boolean valid = colorStrategy.valid(rgb, x, y);
        if (valid) {
            // Update color
            color = smoothFilter.smooth(rgb);
            colorStrategy.setColor(color);
        }
        return valid;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
        reset();
    }

    public void reset() {
        colorStrategy.setColor(color);
        smoothFilter.setInitialColor(color);
    }

    @Override
    public int getColor() {
        return color;
    }

    public SmoothSelection getSmoothFilter() {
        return smoothFilter;
    }

    public void setSmoothFilter(SmoothSelection smoothFilter) {
        this.smoothFilter = smoothFilter;
        reset();
    }

    public ColorStrategy getColorStrategy() {
        return colorStrategy;
    }

    public void setColorStrategy(ColorStrategy colorStrategy) {
        this.colorStrategy = colorStrategy;
        reset();
    }
}
