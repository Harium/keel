package com.harium.keel.custom;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.filter.ColorFilter;
import com.harium.keel.filter.ColorPointFilter;

public class LeftColorFilter extends ColorPointFilter {

    public LeftColorFilter(int w, int h, Color color) {
        super(w, h);
        filter = new ColorFilter(w, h, color);
    }

}
