package com.harium.keel.filter;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.custom.CustomFilter;
import com.harium.keel.filter.selection.RGBColorStrategy;
import com.harium.keel.filter.search.ColoredPointSearch;

public class ColorPointFilter extends CustomFilter {

    public ColorPointFilter(int w, int h) {
        super();
        filter = new ColoredPointSearch(w, h, Color.BLACK);
    }

    public ColorPointFilter(int w, int h, Color color) {
        this(w, h);
        setColor(color);
    }

    public void setTolerance(int tolerance) {
        getColorStrategy().setTolerance(tolerance);
    }

    public int getColor() {
        return getColorStrategy().getColor();
    }

    public void setColor(int color) {
        getColorStrategy().setColor(color);
    }

    public void setColor(Color color) {
        getColorStrategy().setColor(color);
    }

    public RGBColorStrategy getColorStrategy() {
        return ((RGBColorStrategy) filter.getSelectionStrategy());
    }

}
