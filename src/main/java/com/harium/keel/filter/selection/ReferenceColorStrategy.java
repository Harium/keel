package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.strategy.SelectionStrategy;

public abstract class ReferenceColorStrategy implements SelectionStrategy, ColorStrategy {

    protected int color = Color.BLACK.getRGB();

    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }
}
