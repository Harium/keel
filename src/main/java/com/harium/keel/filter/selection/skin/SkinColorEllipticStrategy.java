package com.harium.keel.filter.selection.skin;

import com.harium.etyl.geometry.Ellipse;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.filter.selection.SimpleToleranceStrategy;


public class SkinColorEllipticStrategy extends SimpleToleranceStrategy {

    public static final Ellipse SKIN_ELLIPSE = new Ellipse(140, 140, 133, 32, 316);
    public static final Ellipse SKIN_STRONG_ELLIPSE = new Ellipse(140, 140, 123, 18, 316);

    public SkinColorEllipticStrategy() {
        super();
    }

    public SkinColorEllipticStrategy(int tolerance) {
        super(tolerance);
    }

    @Override
    public boolean valid(int rgb, int x, int y) {
        return inEllipse(SKIN_ELLIPSE, rgb);
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
