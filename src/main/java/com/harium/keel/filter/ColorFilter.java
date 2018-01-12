package com.harium.keel.filter;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.filter.color.RGBColorStrategy;
import com.harium.keel.filter.search.flood.SoftFloodFillSearch;

public class ColorFilter extends SoftFloodFillSearch {

    protected int tolerance = 0x40;

    public ColorFilter(int w, int h) {
        this(w, h, Color.BLACK);
    }

    public ColorFilter(int w, int h, Color color) {
        super(w, h);
        selectionStrategy = new RGBColorStrategy(color, tolerance);
    }

    public ColorFilter(int w, int h, Color color, int tolerance) {
        this(w, h, color);
        setTolerance(tolerance);
    }

    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
        getColorStrategy().setTolerance(tolerance);
    }

    public void setColor(int color) {
        getColorStrategy().setColor(color);
    }

    public void setColor(Color color) {
        getColorStrategy().setColor(color);
    }

    RGBColorStrategy getColorStrategy() {
        return (RGBColorStrategy) selectionStrategy;
    }

    public int getColor() {
        return getColorStrategy().getColor();
    }

}
