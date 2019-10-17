package com.harium.keel.filter.selection.skin;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.strategy.SelectionStrategy;
import com.harium.keel.filter.selection.SimpleToleranceStrategy;

public class SkinColorStrategy extends SimpleToleranceStrategy implements SelectionStrategy {

    public SkinColorStrategy() {
        super();
    }

    public SkinColorStrategy(int tolerance) {
        super(tolerance);
    }

    @Override
    public boolean valid(int rgb, int x, int y) {
        return isSkin(rgb, tolerance);
    }

    public static boolean isSkin(int rgb) {
        return isSkin(rgb, 0);
    }

    public static boolean isSkin(int rgb, int tolerance) {

        int r = ColorHelper.getRed(rgb);
        int g = ColorHelper.getGreen(rgb);
        int b = ColorHelper.getBlue(rgb);

        float x = r;
        float y = b + (g - b);

        float maxTolerance = tolerance;
        float minTolerance = tolerance;

        float my = (8 * x) / 9 - 40 / 9;

        if (x > 105 && x < 175) {
            minTolerance = tolerance * 1.3f;
        }

        return x > 40 && x < 230 && (y > my - minTolerance && y < my + maxTolerance);
    }

}
