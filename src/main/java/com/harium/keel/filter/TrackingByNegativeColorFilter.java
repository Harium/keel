package com.harium.keel.filter;

import com.harium.keel.core.strategy.ComponentValidationStrategy;
import com.harium.keel.filter.color.NegativeColorStrategy;
import com.harium.keel.filter.search.flood.FloodFillSearch;
import com.harium.etyl.commons.graphics.Color;

public class TrackingByNegativeColorFilter extends ColorPointFilter {

    public TrackingByNegativeColorFilter(int w, int h) {
        super(w, h);

        this.searchStrategy = new FloodFillSearch(w, h);
    }

    public TrackingByNegativeColorFilter(int w, int h, Color color) {
        super(w, h);

        this.searchStrategy = new FloodFillSearch(w, h);

        colorStrategy = new NegativeColorStrategy(color, 0x40);

        searchStrategy.setPixelStrategy(colorStrategy);
    }

    public TrackingByNegativeColorFilter(int w, int h, Color color, int tolerance) {
        this(w, h, color);

        colorStrategy.setTolerance(tolerance);

    }

    public void addValidation(ComponentValidationStrategy validation) {
        searchStrategy.addValidation(validation);
    }

}
