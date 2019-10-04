package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;

public class VioletOrange extends IntervalColorMap {

    public VioletOrange() {
        super();
        intervals.put(0f, new Color(0x29, 0x0a, 0x59));
        intervals.put(1f, new Color(0xff, 0x7c, 0));
    }

}
