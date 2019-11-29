package com.harium.keel.core.source;

import org.junit.Before;
import org.junit.Test;

import static com.harium.keel.core.helper.ColorHelper.*;
import static org.junit.Assert.assertEquals;

public class YUV420SourceTest {

    private YUV420Source source;

    @Before
    public void setUp() {
        source = new YUV420Source(3, 3, new byte[]{100, -1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 127});
    }

    @Test
    public void testGetRGB() {
        assertEquals(0, getRed(source.getRGB(0, 0)));
        assertEquals(240, getGreen(source.getRGB(0, 0)));
        assertEquals(0, getBlue(source.getRGB(0, 0)));

        assertEquals(88, getRed(source.getRGB(1, 0)));
        assertEquals(255, getGreen(source.getRGB(1, 0)));
        assertEquals(40, getBlue(source.getRGB(1, 0)));

        assertEquals(0, getRed(source.getRGB(1, 1)));
        assertEquals(142, getGreen(source.getRGB(1, 1)));
        assertEquals(0, getBlue(source.getRGB(1, 1)));
    }

}
