package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;

public class Pastels extends IntervalColorMap {

    public Pastels() {
        super();
        intervals.put(0f, new Color(0xff, 0x66, 0x66));
        intervals.put(0.3f, new Color(0x80, 0x80, 0xff));
        intervals.put(.45f, new Color(0x66, 0xff, 0xff));
        intervals.put(.6f, new Color(0x80, 0xff, 0x7f));
        intervals.put(.75f, new Color(0xff, 0xff, 0x66));
        intervals.put(1f, new Color(0xff, 0x7f, 0xff));
    }

}
