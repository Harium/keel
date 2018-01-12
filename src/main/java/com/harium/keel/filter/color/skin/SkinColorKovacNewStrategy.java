package com.harium.keel.filter.color.skin;

import com.harium.etyl.commons.math.EtylMath;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.strategy.SelectionStrategy;
import com.harium.keel.filter.color.SimpleToleranceStrategy;

/**
 * Based on: Jure Kovač, Peter Peer, and Franc Solina - Human Skin Colour Clustering for Face Detection
 */
public class SkinColorKovacNewStrategy extends SimpleToleranceStrategy implements SelectionStrategy {

    public SkinColorKovacNewStrategy() {
        super();
    }

    public SkinColorKovacNewStrategy(int tolerance) {
        super(tolerance);
    }

    @Override
    public boolean validateColor(int rgb, int j, int i) {
        return isSkin(rgb, tolerance);
    }

    public static boolean isSkin(int rgb) {
        return isSkin(rgb, 0);
    }

    public static boolean isSkin(int rgb, int tolerance) {
        final int R = ColorHelper.getRed(rgb);
        final int G = ColorHelper.getGreen(rgb);
        final int B = ColorHelper.getBlue(rgb);

        final int R_MIN = 95 - tolerance / 2;//Default is 95
        final int G_MIN = 40 - tolerance / 3;//Default is 40
        final int B_MIN = 20 - tolerance / 4;//Default is 20

        final double RG_MOD = EtylMath.diffMod(R, G);
        final double RB_MOD = EtylMath.diffMod(R, B);
        final double R_MIN_MOD = R - Math.min(G, B);

        //Standard illumination
        boolean firstRule = (R > R_MIN && R < 220 && G > G_MIN && B > B_MIN) &&
                R_MIN_MOD > 15 &&
                RG_MOD >= 10 &&
                RB_MOD <= 110; //Very important rule

        //Strong illumination
        boolean secondRule = R > 195 && R < 245 && G > 210 && B > 170 &&
                RG_MOD <= 15 && R > B && G > B &&
                RG_MOD + RB_MOD > 80;

        //Darker skin color
        boolean thirdRule = R <= 145 && R > 60 && G > G_MIN * 2 && B > B_MIN * 2 &&
                R >= G && R >= B &&
                R_MIN_MOD < 38 &&
                RG_MOD >= 3 &&
                RB_MOD + RG_MOD < 70; //Important Rule

        //Remove
        //122 102 78 RG_MOD=20 R-B=44
        //147 121 88 RG_MOD=26 R-B=59
        //109 92 72 RG_MOD=17 R-B=37
        //149 134 111 RG_MOD=15 R-B=38
        //135 113 89 RG_MOD=22 R-B=46

        //Add
        //108 112 89 RG_MOD=-4 R-B=19
        //96 85 65 RG_MOD=11 R-B=31
        //132 125 115 RG_MOD=7 R-B=17
        //97 90 99 RG_MOD=7 R-B=-2
        //104 107 89 RG_MOD=-3 R-B=15

        //106 103 94 RG_MOD=3 R-B=12
        //98 96 84 RG_MOD=2 R-B=14
        //105 92 73 RG_MOD=13
        //99 86 67 RG_MOD=13 R-B = 32
        //105 93 71 RG_MOD=12 R-B = 34
        //103 96 78 RG_MOD=7
        //96 89 73 RG_MOD=7
        // 95 88 72
        //122 123 107
        //125 126 110
        //105 104 99
        //101 91 89

        return firstRule || secondRule || thirdRule;
    }

    @Override
    public boolean softValidateColor(int baseColor, int rgb, int j, int i) {
        return validateColor(rgb, j, i);
    }

}
