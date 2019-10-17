package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.strategy.BaseSelectionStrategy;

import java.util.ArrayList;
import java.util.List;

public class MultipleColorStrategy extends BaseSelectionStrategy {

    protected List<RGBColorStrategy> colors = new ArrayList<RGBColorStrategy>();

    public MultipleColorStrategy() {
        super();
    }

    @Override
    public boolean valid(int rgb, int x, int y) {

        boolean result = false;

        for (RGBColorStrategy strategy : colors) {
            if (strategy.valid(rgb, x, y)) {
                result = true;
                break;
            }
        }

        return result;
    }

    @Override
    public void setBaseRGB(int baseRGB) {

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
