package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.strategy.BaseSelectionStrategy;

public abstract class ReferenceColorStrategy extends BaseSelectionStrategy implements ColorStrategy {

    protected int color = Color.BLACK.getRGB();
    protected int baseRGB = Integer.MIN_VALUE;

    protected boolean hasBaseColor() {
        return baseRGB != Integer.MIN_VALUE;
    }

    public int getBaseRGB() {
        return baseRGB;
    }

    @Override
    public void setBaseRGB(int baseRGB) {
        this.baseRGB = baseRGB;
    }

    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }
}
