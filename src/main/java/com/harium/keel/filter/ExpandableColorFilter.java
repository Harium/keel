package com.harium.keel.filter;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.filter.color.ColorStrategy;
import com.harium.keel.filter.search.flood.ExpandableFloodFillSearch;

public class ExpandableColorFilter extends ColorPointFilter {

    protected int tolerance = 0x40;

    public ExpandableColorFilter(int w, int h) {
        this(w, h, Color.BLACK);
    }

    public ExpandableColorFilter(int w, int h, Color color) {
        super(w, h);

        filter = new ExpandableFloodFillSearch(w, h);
        setPixelStrategy(new ColorStrategy(color, tolerance));
    }

    public ExpandableColorFilter(int w, int h, Color color, int tolerance) {
        this(w, h, color);
        getColorStrategy().setTolerance(tolerance);
    }

}
