package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.helper.ColorHelper;

public class RGBColorStrategy extends RGBToleranceStrategy {

    public RGBColorStrategy() {
        super();
    }

    public RGBColorStrategy(Color color) {
        this(color.getRGB());
    }

    public RGBColorStrategy(Color color, int tolerance) {
        this(color);

        setTolerance(tolerance);
    }

    public RGBColorStrategy(Color color, int maxTolerance, int minTolerance) {
        this(color);

        setTolerance(maxTolerance, minTolerance);
    }

    public RGBColorStrategy(Color color, int redTolerance, int greenTolerance, int blueTolerance) {
        this(color);

        setTolerance(redTolerance, greenTolerance, blueTolerance);
    }

    public RGBColorStrategy(int color) {
        super();
        setColor(color);
    }

    @Override
    public boolean valid(int rgb, int j, int i) {
        return ColorHelper.isColor(rgb, color, minToleranceRed, maxToleranceRed, minToleranceGreen, maxToleranceGreen, minToleranceBlue, maxToleranceBlue);
    }

    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color.getRGB();
    }

}
