package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.helper.ColorHelper;

public class RGBColorStrategy extends RGBToleranceStrategy implements ColorStrategy {

    public RGBColorStrategy() {
        super();
        strength = 0.5f;
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
        if (strength != 1) {
            if (hasBaseColor()) {
                return ColorHelper.isColor(rgb, baseRGB, (int) (minToleranceRed * strength), (int) (maxToleranceRed * strength), (int) (minToleranceGreen * strength), (int) (maxToleranceGreen * strength), (int) (minToleranceBlue * strength), (int) (maxToleranceBlue * strength));
            } else {
                return ColorHelper.isColor(rgb, color, (int) (minToleranceRed * strength), (int) (maxToleranceRed * strength), (int) (minToleranceGreen * strength), (int) (maxToleranceGreen * strength), (int) (minToleranceBlue * strength), (int) (maxToleranceBlue * strength));
            }
        } else {
            if (hasBaseColor()) {
                return ColorHelper.isColor(rgb, baseRGB, minToleranceRed, maxToleranceRed, minToleranceGreen, maxToleranceGreen, minToleranceBlue, maxToleranceBlue);
            } else {
                return ColorHelper.isColor(rgb, color, minToleranceRed, maxToleranceRed, minToleranceGreen, maxToleranceGreen, minToleranceBlue, maxToleranceBlue);
            }
        }
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
