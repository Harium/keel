package com.harium.keel.filter.color;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.strategy.PixelStrategy;

public class AvgColorStrategy implements ColorStrategy, PixelStrategy {
    float weakFactor = 2;

    private int color;
    private int avg;

    private int minTolerance;
    private int maxTolerance;

    public AvgColorStrategy(int tolerance) {
        this.minTolerance = tolerance;
        this.maxTolerance = tolerance;
    }

    public AvgColorStrategy(int maxTolerance, int minTolerance) {
        this.minTolerance = minTolerance;
        this.maxTolerance = maxTolerance;
    }

    public AvgColorStrategy(Color color, int maxTolerance, int minTolerance) {
        this(maxTolerance, minTolerance);
        setColor(color);
    }

    public void setColor(Color color) {
        setColor(color.getRGB());
    }

    public void setColor(int color) {
        this.color = color;
        avg = avgColor(color);
    }

    private int avgColor(Color color) {
        return avgColor(color.getRGB());
    }

    private int avgColor(int color) {
        int r = ColorHelper.getRed(color);
        int g = ColorHelper.getGreen(color);
        int b = ColorHelper.getBlue(color);
        return (r + g + b) / 3;
    }

    @Override
    public boolean validateColor(int rgb, int j, int i) {
        int avgRGB = avgColor(rgb);

        int diff = avgRGB - avg;
        if (diff < 0) {
            return -diff < minTolerance;
        } else if (diff > 0) {
            return diff < maxTolerance;
        } else {
            return true;
        }
    }

    @Override
    public boolean strongValidateColor(int rgb, int j, int i, int reference) {
        int avg = avgColor(reference);
        int avgRGB = avgColor(rgb);

        int diff = avgRGB - avg;

        if (diff < 0) {
            return -diff < minTolerance / weakFactor;
        } else if (diff > 0) {
            return diff < maxTolerance / weakFactor;
        } else {
            return true;
        }
    }
}
