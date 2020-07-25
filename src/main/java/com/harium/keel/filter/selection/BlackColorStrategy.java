package com.harium.keel.filter.selection;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.etyl.commons.graphics.Color;

public class BlackColorStrategy extends SimpleToleranceStrategy {

    @Override
    public boolean valid(int rgb, int x, int y) {
        return ColorHelper.isColor(rgb, Color.BLACK.getRGB(), this.tolerance);
    }

}
