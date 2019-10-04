package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;

public class CobaltBlue extends IntervalColorMap {

    public CobaltBlue() {
        super();
        intervals.put(0f, new Color(0, 0x38, 0xff));
        intervals.put(0.3f, new Color(0, 0x29, 0xa7));
        intervals.put(0.45f, new Color(0, 0xb6, 0xff));
        intervals.put(0.6f, new Color(0, 0x29, 0xa7));
        intervals.put(0.75f, new Color(0, 0xb6, 0xff));
        intervals.put(1f, new Color(0, 0x38, 0xff));
    }

}
