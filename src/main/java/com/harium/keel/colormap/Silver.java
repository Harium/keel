package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;

public class Silver extends IntervalColorMap {

    public Silver() {
        super();
        intervals.put(0f, new Color(0x93, 0x90, 0x90));
        intervals.put(0.3f, new Color(0x42, 0x42, 0x42));
        intervals.put(0.45f, new Color(0xb5, 0xb3, 0xb1));
        intervals.put(0.6f, new Color(0x66, 0x66, 0x66));
        intervals.put(0.75f, new Color(0xde, 0xde, 0xde));
        intervals.put(1f, new Color(0x80, 0x80, 0x80));
    }

}
