package com.harium.keel.marker;

import com.harium.etyl.commons.math.Vector2i;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RectangularMarkerTest {

    private RectangularMarker marker;

    @Before
    public void setUp() {
        marker = new RectangularMarker();
    }

    @Test
    public void testAlignedSquare() {
        int size = 100;
        Vector2i a = new Vector2i(0, 0);
        Vector2i b = new Vector2i(size, 0);
        Vector2i c = new Vector2i(size, size);
        Vector2i d = new Vector2i(0, size);
        marker = new RectangularMarker(a, b, c, d);

        assertEquals(0, 0, marker.findPosition(0f, 0f));
        assertEquals(size, size, marker.findPosition(1f, 1f));
        assertEquals(size / 2, size / 2, marker.findPosition(0.5f, 0.5f));
    }

    @Test
    public void testAlignedRectangle() {
        int width = 200;
        int height = 100;
        Vector2i a = new Vector2i(0, 0);
        Vector2i b = new Vector2i(width, 0);
        Vector2i c = new Vector2i(width, height);
        Vector2i d = new Vector2i(0, height);
        marker = new RectangularMarker(a, b, c, d);

        assertEquals(0, 0, marker.findPosition(0f, 0f));
        assertEquals(width, height, marker.findPosition(1f, 1f));
        assertEquals(width / 2, height / 2, marker.findPosition(0.5f, 0.5f));
    }

    private void assertEquals(int px, int py, Vector2i position) {
        Assert.assertEquals(px, position.x);
        Assert.assertEquals(py, position.y);
    }

}
