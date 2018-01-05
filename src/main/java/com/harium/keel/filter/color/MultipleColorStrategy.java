package com.harium.keel.filter.color;

import com.harium.keel.core.strategy.PixelStrategy;
import com.harium.etyl.commons.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class MultipleColorStrategy implements PixelStrategy {

    protected List<RGBColorStrategy> colors = new ArrayList<RGBColorStrategy>();

    public MultipleColorStrategy() {
        super();
    }

    @Override
    public boolean validateColor(int rgb, int j, int i) {

        boolean result = false;

        for (RGBColorStrategy strategy : colors) {
            if (strategy.validateColor(rgb, j, i)) {
                result = true;
                break;
            }
        }

        return result;
    }

    @Override
    public boolean strongValidateColor(int rgb, int j, int i, int reference) {
        return validateColor(rgb, j, i);
    }

    public void addColor(Color color, int tolerance) {
        colors.add(new RGBColorStrategy(color, tolerance));
    }

    public void addColor(Color color, int maxTolerance, int minTolerance) {
        colors.add(new RGBColorStrategy(color, maxTolerance, minTolerance));
    }

    public void addColor(Color color, int redTolerance, int greenTolerance, int blueTolerance) {
        colors.add(new RGBColorStrategy(color, redTolerance, greenTolerance, blueTolerance));
    }

}
