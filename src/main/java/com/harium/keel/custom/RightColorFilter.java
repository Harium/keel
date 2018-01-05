package com.harium.keel.custom;


import com.harium.keel.filter.ColorFilter;
import com.harium.keel.filter.ColorPointFilter;
import com.harium.keel.filter.search.strategy.RightToLeftSearch;
import com.harium.etyl.commons.graphics.Color;

public class RightColorFilter extends ColorPointFilter {

    public RightColorFilter(int w, int h, Color color) {
        super(w, h);

        filter = new ColorFilter(w, h, color);
        filter.setSearchStrategy(new RightToLeftSearch(filter));
    }

}
