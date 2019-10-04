package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.util.InterpolatorUtil;

import java.util.Map;
import java.util.TreeMap;

public abstract class IntervalColorMap implements ColorMap {

    protected TreeMap<Float, Color> intervals = new TreeMap<>();

    @Override
    public Color getColor(float value) {
        return closestValue(value);
    }

    Color closestValue(float key) {
        Map.Entry<Float, Color> low = intervals.floorEntry(key);
        Map.Entry<Float, Color> high = intervals.ceilingEntry(key);

        Color l = low.getValue();
        Color h = high.getValue();

        float f = InterpolatorUtil.changeScale(low.getKey(), high.getKey(), key);

        int r = (int) (l.getRed() * (1.0 - f) + h.getRed() * f);
        int g = (int) (l.getGreen() * (1.0 - f) + h.getGreen() * f);
        int b = (int) (l.getBlue() * (1.0 - f) + h.getBlue() * f);

        Color color = new Color(r, g, b);
        return color;
    }

}
