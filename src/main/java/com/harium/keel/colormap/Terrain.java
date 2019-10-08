package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;

public class Terrain extends IntervalColorMap {

    public Terrain() {
        super();
        intervals.put(0f, new Color(0xfd, 0xfc, 0xfc));
        intervals.put(0.25f, new Color(0x82, 0x5f, 0x56));
        intervals.put(.5f, new Color(0xfe, 0xfe, 0x98));
        intervals.put(.75f, new Color(0x05, 0xcd, 0x67));
        intervals.put(.1f, new Color(0x32, 0x36, 0x9c));
    }

}
