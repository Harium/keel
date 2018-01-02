package com.harium.keel.filter;

import com.harium.keel.filter.color.ColorStrategy;
import com.harium.keel.filter.search.flood.FloodFillSearch;
import com.harium.etyl.commons.graphics.Color;

public class HardColorFilter extends FloodFillSearch {

    protected int tolerance = 0x40;

    public HardColorFilter(int w, int h) {
        this(w, h, Color.BLACK);
    }

    public HardColorFilter(int w, int h, Color color) {
        super(w, h);
        pixelStrategy = new ColorStrategy(color, tolerance);
    }

    public HardColorFilter(int w, int h, Color color, int tolerance) {
        this(w, h, color);
        this.tolerance = tolerance;
        ((ColorStrategy) pixelStrategy).setTolerance(tolerance);
    }
				
}
