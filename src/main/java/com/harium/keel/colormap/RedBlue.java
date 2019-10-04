package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;

public class RedBlue extends IntervalColorMap {

    public RedBlue() {
        super();
        intervals.put(0f, new Color(0xff, 0, 0));
        intervals.put(1f, new Color(0, 0, 0xff));
    }

}
