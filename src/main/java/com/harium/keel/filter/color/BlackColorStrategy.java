package com.harium.keel.filter.color;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.etyl.commons.graphics.Color;

public class BlackColorStrategy extends SimpleToleranceStrategy {

    @Override
    public boolean validateColor(int rgb, int j, int i) {
        return ColorHelper.isColor(rgb, Color.BLACK.getRGB(), this.tolerance);
    }

}
