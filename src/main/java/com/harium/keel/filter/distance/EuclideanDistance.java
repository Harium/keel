package com.harium.keel.filter.distance;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.strategy.DistanceStrategy;

public class EuclideanDistance implements DistanceStrategy {
    @Override
    public float distance(int rgb, int otherRgb) {
        int dr = ColorHelper.getRed(rgb) - ColorHelper.getRed(otherRgb);
        int dg = ColorHelper.getGreen(rgb) - ColorHelper.getGreen(otherRgb);
        int db = ColorHelper.getBlue(rgb) - ColorHelper.getBlue(otherRgb);
        return (float) Math.sqrt(dr * dr + dg * dg + db * db);
    }
}
