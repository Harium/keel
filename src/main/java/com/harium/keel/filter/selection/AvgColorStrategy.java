package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.helper.ColorHelper;

public class AvgColorStrategy extends ReferenceColorStrategy {

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
    public boolean valid(int rgb, int x, int y) {
        int avg = this.avg;
        if (isSoftSelection()) {
            avg = avgColor(baseRGB);
        }
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

}
