package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;

public class BlueYellowBlue extends IntervalColorMap {

    public BlueYellowBlue() {
        super();
        intervals.put(0f, new Color(0x0b, 0x02, 0xaa));
        intervals.put(0.1f, new Color(0x0b, 0x02, 0xaa));
        intervals.put(0.5f, new Color(0xfd, 0xfb, 0x02));
        intervals.put(0.9f, new Color(0x0b, 0x02, 0xaa));
        intervals.put(1f, new Color(0x0b, 0x02, 0xaa));
    }

}
