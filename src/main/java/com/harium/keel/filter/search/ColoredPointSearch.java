package com.harium.keel.filter.search;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;
import com.harium.keel.filter.color.ColorStrategy;

public class ColoredPointSearch extends PointSearch {

    public ColoredPointSearch(int w, int h, Color color) {
        super(w, h);
        pixelStrategy = new ColorStrategy(color.getRGB());
    }

}
