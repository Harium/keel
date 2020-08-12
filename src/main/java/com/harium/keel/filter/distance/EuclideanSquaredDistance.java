package com.harium.keel.filter.distance;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.strategy.DistanceStrategy;

public class EuclideanSquaredDistance implements DistanceStrategy {

    private static final float MAX = 1f / (255 * 255 + 255 * 255 + 255 * 255);

    @Override
    public float distance(int rgb, int otherRgb) {
        int dr = ColorHelper.getRed(rgb) - ColorHelper.getRed(otherRgb);
        int dg = ColorHelper.getGreen(rgb) - ColorHelper.getGreen(otherRgb);
        int db = ColorHelper.getBlue(rgb) - ColorHelper.getBlue(otherRgb);
        return (dr * dr + dg * dg + db * db) * MAX;
    }
}
