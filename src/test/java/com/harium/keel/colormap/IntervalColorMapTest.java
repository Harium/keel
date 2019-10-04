package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntervalColorMapTest {

    private IntervalColorMap colorMap;

    @Test
    public void testSingleInterval() {
        colorMap = new TestColorMap(new Interval(0, Color.BLACK), new Interval(1, Color.WHITE));
        assertColor(63, 63, 63, colorMap.getColor(0.25f));
        assertColor(127, 127, 127, colorMap.getColor(0.5f));
        assertColor(191, 191, 191, colorMap.getColor(0.75f));
    }

    @Test
    public void testMultipleIntervals() {
        colorMap = new TestColorMap(new Interval(0, new Color(0xff, 0, 0)), new Interval(0.5f, new Color(0, 0xff, 0)), new Interval(1, new Color(0, 0, 0xff)));
        assertColor(223, 31, 0, colorMap.getColor(0.25f));
        assertColor(0, 31, 223, colorMap.getColor(0.75f));
    }

    private void assertColor(int r, int g, int b, Color color) {
        assertEquals(r, color.getRed());
        assertEquals(g, color.getGreen());
        assertEquals(b, color.getBlue());
    }

    class Interval {
        float index;
        Color color;

        public Interval(float index, Color color) {
            this.index = index;
            this.color = color;
        }
    }

    class TestColorMap extends IntervalColorMap {
        public TestColorMap(Interval... intervals) {
            for (Interval interval : intervals) {
                this.intervals.put(interval.index, interval.color);
            }
        }
    }

}
