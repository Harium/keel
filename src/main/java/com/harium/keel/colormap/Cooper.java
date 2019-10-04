package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;

public class Cooper extends IntervalColorMap {

    public Cooper() {
        super();
        intervals.put(0f, new Color(0x97, 0x46, 0x1a));
        intervals.put(0.3f, new Color(0xf8, 0xd8, 0xc5));
        intervals.put(0.8f, new Color(0x6c, 0x2e, 0x16));
        intervals.put(1f, new Color(0xef, 0xdb, 0xcd));
    }

}
