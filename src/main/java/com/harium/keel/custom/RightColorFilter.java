package com.harium.keel.custom;


import com.harium.keel.filter.ColorPointFilter;
import com.harium.keel.filter.color.ColorStrategy;
import com.harium.keel.filter.search.RightToLeftSearch;
import com.harium.etyl.commons.graphics.Color;

public class RightColorFilter extends ColorPointFilter {

    public RightColorFilter(int w, int h, Color color) {
        super(w, h);

        searchStrategy = new RightToLeftSearch();

        colorStrategy = new ColorStrategy(color);

        searchStrategy.setPixelStrategy(colorStrategy);

    }

}
