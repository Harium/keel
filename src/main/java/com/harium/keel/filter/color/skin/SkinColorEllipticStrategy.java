package com.harium.keel.filter.color.skin;

import com.harium.etyl.geometry.Ellipse;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.strategy.SelectionStrategy;
import com.harium.keel.filter.color.SimpleToleranceStrategy;


public class SkinColorEllipticStrategy extends SimpleToleranceStrategy implements SelectionStrategy {

    public static final Ellipse SKIN_ELLIPSE = new Ellipse(140, 140, 133, 32, 316);
    public static final Ellipse SKIN_STRONG_ELLIPSE = new Ellipse(140, 140, 123, 18, 316);

    public SkinColorEllipticStrategy() {
        super();
    }

    public SkinColorEllipticStrategy(int tolerance) {
        super(tolerance);
    }

    @Override
    public boolean validateColor(int rgb, int j, int i) {
        return inEllipse(SKIN_ELLIPSE, rgb);
    }

    @Override
    public boolean softValidateColor(int baseColor, int rgb, int j, int i) {
        return inEllipse(SKIN_STRONG_ELLIPSE, rgb);
    }

    private boolean inEllipse(Ellipse ellipse, int rgb) {
        int r = ColorHelper.getRed(rgb);
        int g = ColorHelper.getGreen(rgb);
        int b = ColorHelper.getBlue(rgb);

        int px = r;
        int py = b + (g - b);

        return ellipse.contains(px, py);
    }
}
