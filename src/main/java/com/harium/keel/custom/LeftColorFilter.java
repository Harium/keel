package com.harium.keel.custom;

import com.harium.keel.filter.ColorPointFilter;
import com.harium.keel.filter.color.ColorStrategy;
import com.harium.keel.filter.search.LeftToRightSearch;
import com.harium.etyl.commons.graphics.Color;

public class LeftColorFilter extends ColorPointFilter {

    public LeftColorFilter(int w, int h, Color color) {
        super(w, h);

        searchStrategy = new LeftToRightSearch();

        colorStrategy = new ColorStrategy(color);

        searchStrategy.setPixelStrategy(colorStrategy);
    }

}
