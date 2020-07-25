package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;

public abstract class ReferenceColorStrategy implements ColorStrategy {

    protected int color = Color.BLACK.getRGB();

    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }
}
