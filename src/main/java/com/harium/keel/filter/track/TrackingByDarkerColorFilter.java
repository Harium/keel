package com.harium.keel.filter.track;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.filter.ColorPointFilter;
import com.harium.keel.filter.selection.DarkerColorStrategy;
import com.harium.keel.filter.search.flood.FloodFillSearch;

public class TrackingByDarkerColorFilter extends ColorPointFilter {

    public TrackingByDarkerColorFilter(int w, int h) {
        super(w, h);

        this.filter = new FloodFillSearch(w, h);
    }

    public TrackingByDarkerColorFilter(int w, int h, Color color) {
        super(w, h);

        filter = new FloodFillSearch(w, h);
        filter.setSelectionStrategy(new DarkerColorStrategy(color, 0x40));
    }

    public TrackingByDarkerColorFilter(int w, int h, Color color, int tolerance) {
        this(w, h, color);

        getColorStrategy().setTolerance(tolerance);
    }

}
