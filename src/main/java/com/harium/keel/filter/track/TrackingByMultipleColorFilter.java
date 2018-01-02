package com.harium.keel.filter.track;

import com.harium.keel.custom.CustomFilter;
import com.harium.keel.filter.color.MultipleColorStrategy;
import com.harium.keel.filter.search.flood.FloodFillSearch;
import com.harium.etyl.commons.graphics.Color;

public class TrackingByMultipleColorFilter extends CustomFilter {

    private static int DEFAULT_TOLERANCE = 0x40;
    private MultipleColorStrategy colorStrategy;

    public TrackingByMultipleColorFilter(int w, int h) {
        super(new FloodFillSearch(w, h));

        colorStrategy = new MultipleColorStrategy();
        setPixelStrategy(new MultipleColorStrategy());
    }

    public TrackingByMultipleColorFilter(int w, int h, Color color, int tolerance) {
        this(w, h);

        colorStrategy.addColor(color, tolerance);
    }

    public TrackingByMultipleColorFilter(int w, int h, Color color) {
        this(w, h, color, DEFAULT_TOLERANCE);
    }

    public void addColor(Color color, int tolerance) {
        colorStrategy.addColor(color, tolerance);
    }

    public void addColor(Color color, int redTolerance, int greenTolerance, int blueTolerance) {
        colorStrategy.addColor(color, redTolerance, greenTolerance, blueTolerance);
    }

    public void addColor(Color color, int maxTolerance, int minTolerance) {
        colorStrategy.addColor(color, maxTolerance, minTolerance);
    }

}
