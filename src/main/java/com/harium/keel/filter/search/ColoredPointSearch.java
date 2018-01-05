package com.harium.keel.filter.search;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.filter.color.RGBColorStrategy;

public class ColoredPointSearch extends PointSearch {

    public ColoredPointSearch(int w, int h, Color color) {
        super(w, h);
        pixelStrategy = new RGBColorStrategy(color.getRGB());
    }

}
