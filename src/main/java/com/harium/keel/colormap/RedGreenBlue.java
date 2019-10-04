package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;

public class RedGreenBlue extends IntervalColorMap {

    public RedGreenBlue() {
        super();
        intervals.put(0f, new Color(0xff, 0, 0));
        intervals.put(0.5f, new Color(0, 0xff, 0));
        intervals.put(1f, new Color(0, 0, 0xff));
    }

}
