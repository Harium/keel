package com.harium.keel.filter;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.filter.search.flood.FloodFillSearch;
import com.harium.keel.filter.selection.ColorStrategy;
import com.harium.keel.filter.selection.RGBColorStrategy;
import com.harium.keel.filter.selection.SmoothColorStrategy;

public class ColorFilter extends FloodFillSearch {

    protected int tolerance = 0x40;

    public ColorFilter(int w, int h) {
        this(w, h, Color.BLACK);
    }

    public ColorFilter(int w, int h, Color color) {
        super(w, h);
        selectionStrategy = new SmoothColorStrategy(new RGBColorStrategy(color, tolerance));
    }

    public ColorFilter(int w, int h, ColorStrategy colorStrategy) {
        super(w, h);
        selectionStrategy = colorStrategy;
    }

    public void setColor(int color) {
        getColorStrategy().setColor(color);
    }

    public void setColor(Color color) {
        getColorStrategy().setColor(color.getRGB());
    }

    public ColorStrategy getColorStrategy() {
        return (ColorStrategy)selectionStrategy;
    }

    public int getColor() {
        return getColorStrategy().getColor();
    }

}
