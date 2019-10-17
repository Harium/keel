package com.harium.keel.filter.selection.skin;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.strategy.SelectionStrategy;
import com.harium.keel.filter.selection.SimpleToleranceStrategy;

/**
 * Based on: Nusirwan Anwar bin Abdul Rahman, Kit Chong Wei and John See
 * RGB-H-CbCr Skin Colour Model for Human Face Detection
 */
public class SkinColorRGBHCbCrStrategy extends SimpleToleranceStrategy implements SelectionStrategy {

    public SkinColorRGBHCbCrStrategy() {
        super();
    }

    public SkinColorRGBHCbCrStrategy(int tolerance) {
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
        boolean ruleA = SkinColorKovacStrategy.isSkin(rgb);

        final int R = ColorHelper.getRed(rgb);
        final int G = ColorHelper.getGreen(rgb);
        final int B = ColorHelper.getBlue(rgb);

        //final int Y  = ColorHelper.getY(R,G,B);
        final int CB = ColorHelper.getCB(R, G, B);
        final int CR = ColorHelper.getCR(R, G, B);

        final float H = ColorHelper.getH(R, G, B);

        boolean rule3 = CR <= 1.5862 * CB + 20;
        boolean rule4 = CR >= 0.3448 * CB + 76.2069;
        boolean rule5 = CR >= -4.5652 * CB + 234.5652;
        boolean rule6 = CR <= -1.15 * CB + 301.75;
        boolean rule7 = CR <= -2.2857 * CB + 432.8;

        boolean ruleB = rule3 && rule4 && rule5 && rule6 && rule7;
        boolean ruleC = H < 25 && H > 230;

        return ruleA && ruleB && ruleC;
    }

}
