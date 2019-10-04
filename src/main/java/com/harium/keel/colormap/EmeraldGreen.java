package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;

public class EmeraldGreen extends IntervalColorMap {

    public EmeraldGreen() {
        super();
        intervals.put(0f, new Color(0x20, 0x3C, 0));
        intervals.put(0.3f, new Color(0x1b, 0x97, 0x1b));
        intervals.put(0.45f, new Color(0x1e, 0x4f, 0x45));
        intervals.put(0.6f, new Color(0x6a, 0xec, 0x01));
        intervals.put(0.75f, new Color(0x1b, 0x97, 0x1b));
        intervals.put(1f, new Color(0x20, 0x3C, 0));
    }

}
