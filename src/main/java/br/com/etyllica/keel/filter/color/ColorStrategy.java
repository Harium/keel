package br.com.etyllica.keel.filter.color;

import br.com.etyllica.commons.graphics.Color;
import br.com.etyllica.keel.core.helper.ColorHelper;

public class ColorStrategy extends ToleranceStrategy {

    protected int color = Color.BLACK.getRGB();

    public ColorStrategy() {
        super();
    }

    public ColorStrategy(Color color) {
        this(color.getRGB());
    }

    public ColorStrategy(Color color, int tolerance) {
        this(color);

        setTolerance(tolerance);
    }

    public ColorStrategy(Color color, int maxTolerance, int minTolerance) {
        this(color);

        setTolerance(maxTolerance, minTolerance);
    }

    public ColorStrategy(Color color, int redTolerance, int greenTolerance, int blueTolerance) {
        this(color);

        setTolerance(redTolerance, greenTolerance, blueTolerance);
    }

    public ColorStrategy(int color) {
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
