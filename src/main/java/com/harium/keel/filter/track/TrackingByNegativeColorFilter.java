package com.harium.keel.filter.track;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.filter.ColorPointFilter;
import com.harium.keel.filter.selection.NegativeColorStrategy;
import com.harium.keel.filter.search.flood.FloodFillSearch;

public class TrackingByNegativeColorFilter extends ColorPointFilter {

    public TrackingByNegativeColorFilter(int w, int h) {
        super(w, h);
        this.filter = new FloodFillSearch(w, h);
    }

    public TrackingByNegativeColorFilter(int w, int h, Color color) {
        super(w, h);

        this.filter = new FloodFillSearch(w, h);
        setPixelStrategy(new NegativeColorStrategy(color, 0x40));
    }

    public TrackingByNegativeColorFilter(int w, int h, Color color, int tolerance) {
        this(w, h, color);
        getColorStrategy().setTolerance(tolerance);
    }

}
