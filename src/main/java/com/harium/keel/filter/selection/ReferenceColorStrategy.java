package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.strategy.BaseSelectionStrategy;

public abstract class ReferenceColorStrategy extends BaseSelectionStrategy implements ColorStrategy {

    protected int INVALID_COLOR = Integer.MAX_VALUE;

    protected int color = Color.BLACK.getRGB();
    protected int baseRGB = INVALID_COLOR;

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

    public void setColor(Color color) {
        this.color = color.getRGB();
    }

    public void resetBaseColor() {
        baseRGB = INVALID_COLOR;
    }

}
