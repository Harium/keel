package com.harium.keel.filter.color;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.etyl.commons.graphics.Color;

public class RGBColorStrategy extends RGBToleranceStrategy implements ColorStrategy {

    protected int color = Color.BLACK.getRGB();

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
    public boolean validateColor(int rgb, int j, int i) {
        return ColorHelper.isColor(rgb, this.color, minToleranceRed, maxToleranceRed, minToleranceGreen, maxToleranceGreen, minToleranceBlue, maxToleranceBlue);
    }

    @Override
    public boolean strongValidateColor(int rgb, int j, int i, int reference) {
        int weakFactor = 2;
        return ColorHelper.isColor(rgb, reference, minToleranceRed / weakFactor, maxToleranceRed / weakFactor, minToleranceGreen / weakFactor, maxToleranceGreen / weakFactor, minToleranceBlue / weakFactor, maxToleranceBlue / weakFactor);
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color.getRGB();
    }

}
