package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;

public class Spectrum extends IntervalColorMap {

    public Spectrum() {
        super();
        intervals.put(0f, new Color(0xff, 0, 0));
        intervals.put(0.2f, new Color(0xff, 0, 0xff));
        intervals.put(.4f, new Color(0, 0, 0xff));
        intervals.put(.6f, new Color(0, 0xff, 0));
        intervals.put(.8f, new Color(0xff, 0xff, 0));
        intervals.put(1f, new Color(0xff, 0, 0));
    }

}
